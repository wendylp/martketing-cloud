package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.util.ListSplit;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.CampaignAudienceFixDao;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignAudienceFix;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignAudienceFixTask extends BaseMQService implements TaskService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CampaignAudienceFixDao campaignAudienceFixDao;

	@Autowired
	private AudienceListPartyMapDao audienceListPartyMapDao;

	@Autowired
	private CampaignActionSaveAudienceTask campaignActionSaveAudienceTask;

	@Autowired
	private DataPartyDao dataPartyDao;
	
	@Autowired
	private TaskScheduleDao taskScheduleDao;

	private ExecutorService executor = null;

	private static final int THREAD_POOL_FIX_SIZE = 10;

	private static final int BATCH_SIZE = 50;

	@Override
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		String queueKey = campaignHeadId + "-" + itemId;
		
		CampaignAudienceFix campaignAudienceFix = new CampaignAudienceFix();
		campaignAudienceFix.setCampaignHeadId(campaignHeadId);
		campaignAudienceFix.setItemId(itemId);
		campaignAudienceFix.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		// 查询mysql中该节点对应的segmentId
		List<CampaignAudienceFix> campaignAudienceFixList = campaignAudienceFixDao
				.selectList(campaignAudienceFix);
		
		List<Segment> segmentListUnique = new ArrayList<Segment>(); // 去重后的segment
		if (CollectionUtils.isNotEmpty(campaignAudienceFixList)) {
			CampaignAudienceFix cat = campaignAudienceFixList.get(0);
			// TODO congshulin mongo转成redis
			long startTime = System.currentTimeMillis();
			executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);
			List<Future<List<Segment>>> resultList = new ArrayList<Future<List<Segment>>>();
			try {
				Integer id = cat.getAudienceFixId();
				List<String> smembers = audienceListPartyMapDao.selectPartyIdLIistByAudienceId(String.valueOf(id));
				logger.info(" fixid {} get value {}.",  id, smembers.size());
				if (CollectionUtils.isNotEmpty(smembers)) {
					List<List<String>> setList = ListSplit.getListSplit(smembers, BATCH_SIZE);
					for (List<String> IdList : setList) {
						Future<List<Segment>> segmentFutureList = executor.submit(new Callable<List<Segment>>() {
							@Override
							public List<Segment> call() throws Exception {
								List<Segment> selectSegmentByIdList = dataPartyDao.selectSegmentByIdList(IdList);
								return selectSegmentByIdList;
							}
						});
						resultList.add(segmentFutureList);
					}
				}
				executor.shutdown();
				// 设置最大阻塞时间，所有线程任务执行完成再继续往下执行
				executor.awaitTermination(24, TimeUnit.HOURS);
				long endTime = System.currentTimeMillis();
				logger.info("=====================从dataParty同步segment的name,用时" + (endTime - startTime) + "毫秒");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

			// 遍历任务的结果
			if (CollectionUtils.isNotEmpty(resultList)) {
				for (Future<List<Segment>> fs : resultList) {
					try {
						List<Segment> list = fs.get(); // 打印各个线程（任务）执行的结果
						if (CollectionUtils.isNotEmpty(list)) {
							for (Segment segment : list) {
								boolean audienceExist = checkNodeAudienceExist(campaignHeadId, itemId,
										segment.getDataId());
								if (!audienceExist) {// 只存node_audience表中不存在的数据
									// 把segment保存到mongo中的node_audience表
									insertNodeAudience(campaignHeadId, itemId, segment);
									segmentListUnique.add(segment);
								}
							}
						}
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					} catch (ExecutionException e) {
						executor.shutdownNow();
						logger.error(e.getMessage());
					}
				}
			}
		}
		logger.info("----获取队列 {}长度： {}----", queueKey, segmentListUnique.size());
		if (CollectionUtils.isNotEmpty(segmentListUnique)) {
			// 查询节点后面的分支
			List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
			for (CampaignSwitch cs : campaignEndsList) {
			    sendDynamicQueue(segmentListUnique, cs.getCampaignHeadId() + "-" + cs.getNextItemId());
			   
				// 逻辑删除传递走的数据
				logicDeleteNodeAudience(campaignHeadId, itemId, segmentListUnique);
				logger.info(queueKey + "-out:" + JSON.toJSONString(segmentListUnique));
			}
		}

	}

	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub

	}

}
