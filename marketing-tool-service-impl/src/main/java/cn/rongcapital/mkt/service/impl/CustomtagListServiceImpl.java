package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomtagListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CustomtagListServiceImpl implements CustomtagListService {

	@Autowired
	private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

	@Autowired
	private MongoCustomTagDao mongoCustomTagDao;

	/**
	 * 功能描述：标签列表(分页， 分类名称展示)
	 * 
	 * 接口：mkt.customtag.list
	 * 
	 * @param customTagCategoryId
	 * @param index
	 * @param size
	 * @return
	 */
	@Override
	public BaseOutput customtagListGet(String customTagCategoryId, Integer index, Integer size) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		CustomTagCategory customTagCategory = mongoCustomTagCategoryDao.findByCategoryId(customTagCategoryId);

		if (customTagCategory != null) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("custom_tag_category_id", customTagCategoryId);
			data.put("custom_tag_category_name", customTagCategory.getCustomTagCategoryName());
			Boolean add_status = true;
			// 如果是未分类不能添加自定义标签，可以移动自定义标签
			if (ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID.equals(customTagCategoryId)) {
				add_status = false;
			}
			data.put("add_status", add_status);
			data.put("move_status", !add_status);

			List<Object> childernTag = new ArrayList<Object>();

			List<String> customTagIdLists = customTagCategory.getChildrenCustomTagList();
			if (CollectionUtils.isNotEmpty(customTagIdLists)) {
				List<CustomTag> customTagLists = mongoCustomTagDao.findByCustomTagIdList(customTagIdLists, index, size);
				if (CollectionUtils.isNotEmpty(customTagLists)) {
					for (CustomTag customTagList : customTagLists) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("custom_tag_id", customTagList.getCustomTagId());
						// 如果为空则设置为0
						map.put("custom_tag_name", customTagList.getCustomTagName());
						Integer coverNumber = customTagList.getCoverNumber();
						map.put("cover_number", coverNumber == null ? 0 : coverNumber);
						// 如果为null则设置为0
						Integer coverFrequency = customTagList.getCoverFrequency();
						map.put("cover_frequency", coverFrequency == null ? 0 : coverFrequency);
						childernTag.add(map);
					}
				}
				result.setTotal(childernTag.size());
				result.setTotalCount((int) mongoCustomTagDao.countValidCustomTag(customTagIdLists));
			}
			data.put("childern_tag", childernTag);
			result.getData().add(data);
		}
		return result;
	}

	/**
	 * 功能描述：细分中查询自定义标签列表
	 * 
	 * 接口：mkt.segment.customtag.get
	 * 
	 * @param customTagCategoryId
	 *            自定义标签分类id
	 * @return BaseOutput
	 */
	public BaseOutput getCustomtagList(String customTagCategoryId) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		List<Object> dataList = new ArrayList<Object>();

		CustomTagCategory customTagCategory = mongoCustomTagCategoryDao.findByCategoryId(customTagCategoryId);
		if (customTagCategory != null) {
			List<String> customTagIdLists = customTagCategory.getChildrenCustomTagList();
			if (CollectionUtils.isNotEmpty(customTagIdLists)) {
				List<CustomTag> customTagLists = mongoCustomTagDao.findByCustomTagIdList(customTagIdLists);
				if (CollectionUtils.isNotEmpty(customTagLists)) {
					for (CustomTag customTagList : customTagLists) {
						
                        Integer coverNumber = customTagList.getCoverNumber();
                        if (coverNumber != null && coverNumber.intValue() != 0) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("custom_tag_id", customTagList.getCustomTagId());
                            map.put("custom_tag_name", customTagList.getCustomTagName());
                            map.put("cover_number", coverNumber);
                            map.put("cover_frequency",
                                    customTagList.getCoverFrequency() == null ? 0 : customTagList.getCoverFrequency());
                            dataList.add(map);
                        }
						
					}
				}
				result.setTotal(dataList.size());
				result.setTotalCount(dataList.size());
			}
			result.getData().addAll(dataList);

		}
		return result;
	}

}
