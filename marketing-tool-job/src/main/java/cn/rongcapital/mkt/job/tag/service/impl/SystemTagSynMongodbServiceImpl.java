package cn.rongcapital.mkt.job.tag.service.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.SysTagViewDao;
import cn.rongcapital.mkt.dao.SystemTagResultDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.SysTagView;
import cn.rongcapital.mkt.po.SystemTagResult;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Tag;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;

/*************************************************
 * @功能简述: 系统标签同步接口实现（查询视图方式）
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/10/13
 * @复审人:
 *************************************************/
@Service
public class SystemTagSynMongodbServiceImpl implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SystemTagResultDao systemTagResultDao;

	@Autowired
	private SysTagViewDao sysTagViewDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	// @Autowired
	// private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	private final ExecutorService executor = Executors.newFixedThreadPool(50);

	@Override
	public void task(Integer taskId) {
		try {
			logger.info("同步系统标签任务开始执行------------------>");
			initMongoTagList();
			SysTagView sysTagView = new SysTagView();
			sysTagView.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			List<SysTagView> selectList = sysTagViewDao.selectList(sysTagView);

			for (SysTagView sys : selectList) {
				this.getSysTagResult(sys);
			}

			logger.info("同步系统标签任务执行结束------------------>");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("同步系统标签任务出现异常--------------->" + e.getMessage(), e);
		}
	}

	public void getSysTagResult(SysTagView sys) {
		try {
			String viewName = sys.getViewName(); // 视图名称
			String tagName = sys.getTagName(); // 标签名称

			if (StringUtils.isEmpty(viewName) || StringUtils.isEmpty(tagName))
				return;
			List<SystemTagResult> resultList = systemTagResultDao.selectListByMap(viewName);
			logger.info("开始同步" + sys.getViewDesc() + "-------->" + tagName);

			// 查询推荐标签
			Criteria criteriaAll = Criteria.where("tag_name_eng").is(tagName);

			TagRecommend tagRecommend = mongoTemplate.findOne(new Query(criteriaAll),
					cn.rongcapital.mkt.po.mongodb.TagRecommend.class);

			for (SystemTagResult systemTagResult : resultList) {
				/*
				 * Runnable a = new Runnable() {
				 * 
				 * @Override public void run() {
				 * startSynchTag(systemTagResult.getKeyId(),systemTagResult.
				 * getTagValue(),tagRecommend); } };
				 * threadPoolTaskExecutor.execute(a);
				 */
				this.executor.submit(new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						startSynchTag(systemTagResult.getKeyId(), systemTagResult.getTagValue(), tagRecommend);
						return null;
					}

				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取视图结果数据出现异常----------------->" + e.getMessage(), e);
		}
	}

	/**
	 * 同步标签
	 * 
	 * @param keyId
	 *            人员ID
	 * @param tagValue
	 *            标签名称
	 * @param tagRecommend
	 */
	private void startSynchTag(Integer keyId, String tagValue, TagRecommend tagRecommend) {
		try {
			Criteria criteria = Criteria.where("mid").is(Integer.valueOf(keyId));
			// 查询Mongo是否存在此数据
			DataParty dataParty = mongoTemplate.findOne(new Query(criteria), DataParty.class);
			if (dataParty == null) {
				return;
			}
			// 封装Tag属性
			Tag tag = new Tag(tagRecommend.getTagId(), tagRecommend.getTagName(), tagRecommend.getTagNameEng(),
					tagValue, 1);

			Query query = Query.query(Criteria.where("mid").is(keyId));
			WriteResult updateFirst = mongoTemplate.updateFirst(query, new Update().push("tag_list", tag),
					DataParty.class);
			// 为NULL标签该人不存在
			if (updateFirst == null) {
				logger.info("MID不存在---------------->" + keyId);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("同步Mongo中tag_list字段出现异常-------------->" + e.getMessage(), e);
		}
	}

	/**
	 * 初始化Mongo数据，将tag_list字段删除
	 */
	private void initMongoTagList() {
		try {
			Query query = Query.query(Criteria.where("mid").gt(-1));
			Update update = new Update().unset("tag_list");
			mongoTemplate.updateMulti(query, update, cn.rongcapital.mkt.po.mongodb.DataParty.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("初始化Mongo tag_list字段出现异常---------->" + e.getMessage(), e);
		}
	}

}
