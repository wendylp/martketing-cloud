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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    

    
}
