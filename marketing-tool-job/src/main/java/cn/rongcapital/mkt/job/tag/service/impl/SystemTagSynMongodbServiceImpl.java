package cn.rongcapital.mkt.job.tag.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.SysTagViewDao;
import cn.rongcapital.mkt.dao.SystemTagResultDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.SysTagView;
import cn.rongcapital.mkt.po.SystemTagResult;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;

@Service
public class SystemTagSynMongodbServiceImpl implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SystemTagResultDao systemTagResultDao;

	@Autowired
	private SysTagViewDao sysTagViewDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Override
	public void task(Integer taskId) {
		SysTagView sysTagView = new SysTagView();
		sysTagView.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		List<SysTagView> selectList = sysTagViewDao.selectList(sysTagView);
		for (SysTagView sys : selectList) {
			this.getSysTagResult(sys);
		}

		//thread1();
	}
	
	public void getSysTagResult(SysTagView sys) {
		HashMap<String, String> map = new HashMap<String,String>();
		map.put("tagView", sys.getViewName());
		List<SystemTagResult> selectList = systemTagResultDao.selectList(map);
		
		
		
		
		
	}

//	public void thread1() {
//		try {
//			int count = 0;
//			List<Map<String, Object>> list = systemTagResultDao.selectList(map);
//			// 查询推荐标签
//			Criteria criteriaAll = Criteria.where("tag_name_eng").is("city");
//
//			TagRecommend tagRecommend = mongoTemplate.findOne(new Query(criteriaAll),
//					cn.rongcapital.mkt.po.mongodb.TagRecommend.class);
//			for (Map<String, Object> map : list) {
//				if (count++ < 200) {
//					Runnable a = new Runnable() {
//						@Override
//						public void run() {
//							test(map, tagRecommend);
//						}
//					};
//					threadPoolTaskExecutor.execute(a);
//				} else {
//					count = 0;
//				}
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
