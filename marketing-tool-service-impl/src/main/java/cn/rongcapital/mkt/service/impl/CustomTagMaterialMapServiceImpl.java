package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CustomTagMaterialMapDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.CustomTagMaterialMap;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomTagActionService;
import cn.rongcapital.mkt.service.CustomTagMaterialMapService;
import cn.rongcapital.mkt.vo.in.CustomTagIn;

@Service
public class CustomTagMaterialMapServiceImpl implements CustomTagMaterialMapService {

	@Autowired
	private CustomTagMaterialMapDao customTagMaterialMapDao;

	@Autowired
	private CustomTagActionService customTagActionService;

	@Autowired
	private MongoCustomTagDao customTagDao;

	@Autowired
	private MongoCustomTagCategoryDao customTagCategoryDao;

	@Override
	public void buildTagMaterialRealation(List<CustomTagIn> customTagInList, String materialCode, String materialType) {
		List<String> customTagIdList = new ArrayList<>();
		Date currentDate = new Date();
		for (CustomTagIn customTagIn : customTagInList) {
			String customTagId = customTagIn.getCustomTagId(); // 自定义标签ID
			String customTagName = customTagIn.getCustomTagName(); // 自定义标签名称
			if (StringUtils.isEmpty(customTagId)) {
				// TODO 标签插入
				List<String> customTags = new ArrayList<>();
				customTags.add(customTagName);
				customTagId = customTagActionService
						.insertCustomTagListIntoDefaultCategory((ArrayList<String>) customTags).get(0).getCustomTagId();
			}
			// 封装
			CustomTagMaterialMap customTagMaterialMap = new CustomTagMaterialMap(customTagId, customTagName,
					materialCode, materialType, ApiConstant.INT_ZERO, currentDate);
			if(CollectionUtils.isEmpty(customTagMaterialMapDao.selectList(new CustomTagMaterialMap(customTagId, materialCode, materialType, ApiConstant.INT_ZERO)))){
				// 插入数据
				customTagMaterialMapDao.insert(customTagMaterialMap);
			}
			customTagIdList.add(customTagId);
		}
		//多余数据清理
		customTagMaterialMapDao.clearSpareData(materialCode, materialType, customTagIdList);
	}

	@Override
	public List<CustomTagIn> getCustomTagByMaterialCode(String materialCode, String materialType) {
		List<CustomTagIn> resultList = new ArrayList<>();
		List<String> customTagIdList = customTagMaterialMapDao.getCustomTagIdByMaterialParam(materialCode,
				materialType);
		for (String customTagId : customTagIdList) {
			CustomTag customTag = customTagDao.getCustomTagByTagId(customTagId);
			CustomTagCategory customTagCategory = customTagCategoryDao.findByChildrenCustomTagList(customTagId);
			if(customTagCategory == null){		//为Null表示错误数据
				continue;
			}
			resultList.add(new CustomTagIn(customTagId, customTag.getCustomTagName(), customTag.getParentId(),
					customTagCategory.getCustomTagCategoryName(), customTag.getIsDeleted()));
		}
		return resultList;
	}

	@Override
	public List<CustomTagMaterialMap> getAllData() {
		CustomTagMaterialMap customTagMatertalMap = new CustomTagMaterialMap();
		customTagMatertalMap.setPageSize(null);
		return customTagMaterialMapDao.selectList(customTagMatertalMap );
	}

}
