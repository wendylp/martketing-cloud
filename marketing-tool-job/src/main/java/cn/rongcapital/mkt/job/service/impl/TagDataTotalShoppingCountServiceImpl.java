package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mongodb.WriteResult;

import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.BaseTagData;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.ShoppingWechat;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Tag;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;

@Service
public class TagDataTotalShoppingCountServiceImpl extends BaseTagData implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DataShoppingDao dataShoppingDao;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;


	public void requestRuleEngine() {

	}

	@Override
	public void task(Integer taskId) {
		thread1();
		thread2();
		thread3();
		thread4();
	}
	
	public void thread1(){
		try {
			int count = 0;
			List<Map<String, Object>> list = dataShoppingDao.testFindView();
			// 查询推荐标签
			Criteria criteriaAll = Criteria.where("tag_name_eng").is("city");

			TagRecommend tagRecommend = mongoTemplate.findOne(new Query(criteriaAll),
					cn.rongcapital.mkt.po.mongodb.TagRecommend.class);
			for (Map<String, Object> map : list) {
				if (count++ < 200) {
					Runnable a = new Runnable() {
						@Override
						public void run() {
							test(map,tagRecommend);
						}
					};
					threadPoolTaskExecutor.execute(a);
				} else {
					count = 0;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void thread2(){
		int count = 0;
		List<Map<String, Object>> list = dataShoppingDao.selectVTagRecent30();
		// 查询推荐标签
		Criteria criteriaAll = Criteria.where("tag_name_eng").is("buyMonth");

		TagRecommend tagRecommend = mongoTemplate.findOne(new Query(criteriaAll),
				cn.rongcapital.mkt.po.mongodb.TagRecommend.class);
		
		for (Map<String, Object> map : list) {
			if (count++ < 200) {
				Runnable a = new Runnable() {
					@Override
					public void run() {
						test2(map,tagRecommend);
					}
				};
				threadPoolTaskExecutor.execute(a);
			} else {
				count = 0;
			}

		}
		
	}

	public void thread3(){
		int count = 0;
		List<Map<String, Object>> list = dataShoppingDao.selectVTagTastePrefer();
		// 查询推荐标签
		Criteria criteriaAll = Criteria.where("tag_name_eng").is("cakeTastes");

		TagRecommend tagRecommend = mongoTemplate.findOne(new Query(criteriaAll),
				cn.rongcapital.mkt.po.mongodb.TagRecommend.class);
		
		for (Map<String, Object> map : list) {
			if (count++ < 200) {
				Runnable a = new Runnable() {
					@Override
					public void run() {
						test3(map,tagRecommend);
					}
				};
				Thread thread = new Thread(a);
				thread.start();
			} else {
				count = 0;
			}

		}
	}
	
	public void thread4(){
		int count = 0;
		List<Map<String, Object>> list = dataShoppingDao.selectVTagWeightPrefer();
		// 查询推荐标签
		Criteria criteriaAll = Criteria.where("tag_name_eng").is("cakeWeight");

		TagRecommend tagRecommend = mongoTemplate.findOne(new Query(criteriaAll),
				cn.rongcapital.mkt.po.mongodb.TagRecommend.class);
		
		for (Map<String, Object> map : list) {
			if (count++ < 400) {
				Runnable a = new Runnable() {
					@Override
					public void run() {
						test4(map,tagRecommend);
					}
				};
				Thread thread = new Thread(a);
				thread.start();
			} else {
				count = 0;
			}

		}
	}

	@Override
	public void tagData(ShoppingWechat shoppingWechat) {
		
	}

	private void test(Map<String, Object> map,TagRecommend tagRecommend) {
		try {
			Integer keyId = (Integer) map.get("keyid");
			System.out.println("-----------------------》本次更新：" + keyId);
			Criteria criteria = Criteria.where("mid").is(Integer.valueOf(keyId));

			DataParty dataParty = mongoTemplate.findOne(new Query(criteria), DataParty.class);
			if (dataParty == null)
				return;
			List<Tag> tagList = dataParty.getTagList();
			if (CollectionUtils.isEmpty(tagList)) {
				tagList = new ArrayList<>();
			}

			Tag tag = new Tag(tagRecommend.getTagId(), tagRecommend.getTagName(), tagRecommend.getTagNameEng(),
					(String) map.get("tagv"), 1);
			
			Query query = Query.query(Criteria.where("mid").is(keyId));
			WriteResult updateFirst = mongoTemplate.updateFirst(query, new Update().push("tag_list", tag), DataParty.class);

//			tagList.add(tag);
			// 更新插入
//			Update update = new Update().set("tag_list", tagList);
			// logger.info("同步标签数据方法开始执行：----------->标签属性为，Mid:" + bizCode);
//
//			DataParty findAndModify = mongoTemplate.findAndModify(new Query(criteria), update, DataParty.class);
			if (updateFirst == null){
				System.out.println("MID不存在----------------》"+keyId);
				return;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test2(Map<String, Object> map,TagRecommend tagRecommend){
		Integer keyId = (Integer) map.get("keyid");
		System.out.println("-----------------------》本次更新：" + keyId);
		Criteria criteria = Criteria.where("mid").is(Integer.valueOf(keyId));

		DataParty dataParty = mongoTemplate.findOne(new Query(criteria), DataParty.class);
		if (dataParty == null)
			return;
		List<Tag> tagList = dataParty.getTagList();
		if (CollectionUtils.isEmpty(tagList)) {
			tagList = new ArrayList<>();
		}
		Tag tag = new Tag(tagRecommend.getTagId(), tagRecommend.getTagName(), tagRecommend.getTagNameEng(),
				"否", 1);
//		tagList.add(tag);
		// 更新插入
//		Update update = new Update().set("tag_list", tagList);
		// logger.info("同步标签数据方法开始执行：----------->标签属性为，Mid:" + bizCode);

		Query query = Query.query(Criteria.where("mid").is(keyId));
		WriteResult updateFirst = mongoTemplate.updateFirst(query, new Update().push("tag_list", tag), DataParty.class);
		
//		DataParty findAndModify = mongoTemplate.findAndModify(new Query(criteria), update, DataParty.class);
		if (updateFirst == null){
			System.out.println("MID不存在----------------》"+keyId);
			return;
		}
	}
	
	public void test3(Map<String, Object> map,TagRecommend tagRecommend){
		Integer keyId = (Integer) map.get("keyid");
		System.out.println("-----------------------》本次更新：" + keyId);
		Criteria criteria = Criteria.where("mid").is(Integer.valueOf(keyId));

		DataParty dataParty = mongoTemplate.findOne(new Query(criteria), DataParty.class);
		if (dataParty == null)
			return;
		List<Tag> tagList = dataParty.getTagList();
		if (CollectionUtils.isEmpty(tagList)) {
			tagList = new ArrayList<>();
		}
		Tag tag = new Tag(tagRecommend.getTagId(), tagRecommend.getTagName(), tagRecommend.getTagNameEng(),
				(String) map.get("tagv"), 1);
//		tagList.add(tag);
		// 更新插入
//		Update update = new Update().set("tag_list", tagList);
		// logger.info("同步标签数据方法开始执行：----------->标签属性为，Mid:" + bizCode);

		Query query = Query.query(Criteria.where("mid").is(keyId));
		WriteResult updateFirst = mongoTemplate.updateFirst(query, new Update().push("tag_list", tag), DataParty.class);
		
//		DataParty findAndModify = mongoTemplate.findAndModify(new Query(criteria), update, DataParty.class);
		if (updateFirst == null){
			System.out.println("MID不存在----------------》"+keyId);
			return;
		}
	}
	
	public void test4(Map<String, Object> map,TagRecommend tagRecommend){
		Integer keyId = (Integer) map.get("keyid");
		System.out.println("-----------------------》本次更新：" + keyId);
		Criteria criteria = Criteria.where("mid").is(Integer.valueOf(keyId));

		DataParty dataParty = mongoTemplate.findOne(new Query(criteria), DataParty.class);
		if (dataParty == null)
			return;
		List<Tag> tagList = dataParty.getTagList();
		if (CollectionUtils.isEmpty(tagList)) {
			tagList = new ArrayList<>();
		}
		String weight = (String) map.get("tagv");
		weight = weight.replaceAll("磅", "");
		Float f = Float.valueOf(weight);
		if(f < 1.2 ) {
			weight = "<1.2";
		} else if(f <= 1.5) {
			weight = "1.2-1.5";
		} else if(f <2.2) {
	        ;
		} else if(f <=2.5) {
			weight = "2.2-2.5";
		} else if(f <3.2) {
	        ;
	    }else if(f <=3.5) {
	    	weight = "3.2-3.5";
	    }else if(f <5.2) {
	        ;
	    }else if(f <=5.5) {
	    	weight = "5.2-5.5";
	    }else if(f > 5.5) {
	    	weight = ">5.5";
	    }
		Tag tag = new Tag(tagRecommend.getTagId(), tagRecommend.getTagName(), tagRecommend.getTagNameEng(),
				weight, 1);
//		tagList.add(tag);
		// 更新插入
//		Update update = new Update().set("tag_list", tagList);
		// logger.info("同步标签数据方法开始执行：----------->标签属性为，Mid:" + bizCode);

		Query query = Query.query(Criteria.where("mid").is(keyId));
		WriteResult updateFirst = mongoTemplate.updateFirst(query, new Update().push("tag_list", tag), DataParty.class);
		
//		DataParty findAndModify = mongoTemplate.findAndModify(new Query(criteria), update, DataParty.class);
		if (updateFirst == null){
			System.out.println("MID不存在----------------》"+keyId);
			return;
		}
	}
	

}
