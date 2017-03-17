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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.util.ListSplit;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.CampaignAudienceTarget;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignAudienceTargetTask extends CampaignAutoCancelTaskService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CampaignAudienceTargetDao campaignAudienceTargetDao;
	@Autowired
	private DataPartyDao dataPartyDao;
	

	private static final String REDIS_IDS_KEY_PREFIX = "segmentcoverid:";
	private static final String REDIS_SNAP_IDS_KEY_PREFIX = "segmentsnapcoverid:";


	private ExecutorService executor = null;

	private static final int THREAD_POOL_FIX_SIZE = 10;

	private static final int BATCH_SIZE = 50;

	@Override
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		String queueKey = campaignHeadId + "-" + itemId;
		CampaignAudienceTarget campaignAudienceTarget = new CampaignAudienceTarget();
		campaignAudienceTarget.setCampaignHeadId(campaignHeadId);
		campaignAudienceTarget.setItemId(itemId);
		campaignAudienceTarget.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		// 查询mysql中该节点对应的segmentId
		List<CampaignAudienceTarget> campaignAudienceTargetList = campaignAudienceTargetDao
				.selectList(campaignAudienceTarget);
		List<Segment> segmentListUnique = new ArrayList<Segment>(); // 去重后的segment
		if (CollectionUtils.isNotEmpty(campaignAudienceTargetList)) {
			CampaignAudienceTarget cat = campaignAudienceTargetList.get(0);
			Integer  snapID = cat.getSnapSegmentationId();			
			String mongoKey = snapID == null ? REDIS_IDS_KEY_PREFIX + cat.getSegmentationId()
										:REDIS_SNAP_IDS_KEY_PREFIX + cat.getSegmentationId();
			
			// TODO congshulin mongo转成redis
			long startTime = System.currentTimeMillis();
			executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);
			List<Future<List<Segment>>> resultList = new ArrayList<Future<List<Segment>>>();
			try {
				Set<String> smembers = JedisClient.smembers(mongoKey, 2);
				logger.info("redis key {} get value {}.", mongoKey, smembers.size());
				if (CollectionUtils.isNotEmpty(smembers)) {
					List<List<String>> setList = ListSplit.getSetSplit(smembers, BATCH_SIZE);
					for (List<String> segmentIdList : setList) {
						Future<List<Segment>> segmentFutureList = executor.submit(new Callable<List<Segment>>() {
							@Override
							public List<Segment> call() throws Exception {
								List<Segment> selectSegmentByIdList = dataPartyDao.selectSegmentByIdList(segmentIdList);
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
								segment.setSegmentationHeadId(cat.getSegmentationId());
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

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		super.cancelInnerTask(taskSchedule);
	}

	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub

	}

}
