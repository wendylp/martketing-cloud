package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomTagActionService;
import cn.rongcapital.mkt.vo.BaseOutput;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * Created by byf on 1/15/17.
 */
@Service
public class CustomTagActionServiceImpl implements CustomTagActionService{

    @Autowired
    private MongoCustomTagDao mongoCustomTagDao;

    @Autowired
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Override
    public BaseOutput insertCustomTag() {
        CustomTag customTag = new CustomTag();
        customTag.setCustomTagId("XXXXXX");
        customTag.setCustomTagName("测试标签");
        mongoCustomTagDao.insertCustomTag(customTag);
        return new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
    }

	@Override
	public List<CustomTag> findCustomTagsByCategoryId(String categoryId) {
		
		CustomTagCategory customTagCategory = mongoCustomTagCategoryDao.findByCategoryId(categoryId);
		List<CustomTag> customTagList = new ArrayList<CustomTag>();
		CustomTag customTag;
		if(customTagCategory != null){
			List<String> customTagIdList = customTagCategory.getChildrenCustomTagList();
			for(String customTagId : customTagIdList){
				customTag = mongoCustomTagDao.findByCustomTagId(customTagId);
				if(customTag != null){
					customTagList.add(customTag);
				}
			}
		}
		
		return customTagList;
	}

	@Override
	public List<CustomTag> insertCustomTagListIntoDefaultCategory(ArrayList<String> customTags) {
		List<CustomTag> customTagList = new ArrayList();

		for(String customTagName : customTags){
			if(!isCustomTagExists(customTagName)){
				CustomTag insertCustomTag = new CustomTag();
				insertCustomTag.setParentId(ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID);     //父节点Id
				insertCustomTag.setCustomTagId(RandomStringUtils.random(10,true,true) + System.currentTimeMillis());    //子节点Id
				insertCustomTag.setCustomTagName(customTagName);  //子节点名称
				insertCustomTag.setCustomTagSource(null);
				insertCustomTag.setCreateTime(new Date());
				insertCustomTag.setUpdateTime(new Date());
				insertCustomTag.setCustomTagType(ApiConstant.CUSTOM_TAG_CATEGORY_TYPE_DEFINE);
				insertCustomTag.setIsDeleted(Integer.valueOf(ApiConstant.TABLE_DATA_STATUS_VALID));
				insertCustomTag.setRecommendFlag(null);
				insertCustomTag.setCoverNumber(new Integer(0));
				insertCustomTag.setCoverFrequency(new Integer(0));
				mongoCustomTagDao.insertCustomTag(insertCustomTag);
				
				//Todo:更新CustomTagCategory的ChildrenList。
				CustomTagCategory paramCustomTagCategory = new CustomTagCategory();
				paramCustomTagCategory.setCustomTagCategoryId(ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID);
				CustomTagCategory customTagCategory = mongoCustomTagCategoryDao.findOne(paramCustomTagCategory);
			    customTagCategory.getChildrenCustomTagList().add(insertCustomTag.getCustomTagId());
			    mongoCustomTagCategoryDao.updateCustomTagCategoryChildrenListById(ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID,customTagCategory);
			}
		}

		for(String customTagName : customTags){
			CustomTag paramCustomTag = new CustomTag();
			paramCustomTag.setCustomTagName(customTagName);
			paramCustomTag.setCustomTagType(ApiConstant.CUSTOM_TAG_CATEGORY_TYPE_DEFINE);
			paramCustomTag.setParentId(ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID);
			customTagList.add(mongoCustomTagDao.findOne(paramCustomTag));
		}
		return customTagList;
	}

	private boolean isCustomTagExists(String customTagName) {
		boolean flag = false;
		CustomTag paramCustomTag = new CustomTag();
		paramCustomTag.setCustomTagName(customTagName);
		paramCustomTag.setCustomTagType(ApiConstant.CUSTOM_TAG_CATEGORY_TYPE_DEFINE);
		paramCustomTag.setParentId(ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID);
		if(mongoCustomTagDao.findOne(paramCustomTag) != null){
			flag = true;
		}
		return flag;
	}



	/**
	 * 查询最热标签(覆盖人次最多的标签)
	 */
	public List<CustomTag> findCustomTagTopList(Integer topType){

        Criteria criteria = new Criteria("is_deleted").is(0);
		Query query = new Query(criteria);

		//排序
		query.with(new Sort(Direction.DESC, "cover_frequency"));

		if(topType == 1){
			query.limit(25);
		}else if(topType == 2){
			query.limit(50);
		}else if(topType == 3){
			query.limit(100);
		}

		List<CustomTag> customTagList = mongoCustomTagDao.find(query);

		return customTagList;
	}

}
