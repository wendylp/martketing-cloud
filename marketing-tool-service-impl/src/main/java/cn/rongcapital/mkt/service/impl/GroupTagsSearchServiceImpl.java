package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.dao.TagGroupMapDao;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.po.Tag;
import cn.rongcapital.mkt.po.TagGroupMap;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.service.GroupTagsSearchService;
import cn.rongcapital.mkt.vo.out.SerarchTagGroupTagsDataOut;
import cn.rongcapital.mkt.vo.out.SerarchTagGroupTagsOut;
import cn.rongcapital.mkt.vo.out.TagOut;

@Service
public class GroupTagsSearchServiceImpl implements GroupTagsSearchService {

	@Autowired
	private TagDao tagDao;
	@Autowired
	private TaggroupDao taggroupDao;
	@Autowired
	private TagGroupMapDao tagGroupMapDao;

	@Autowired
	private CustomTagDao customTagDao;

	@Autowired
	private CustomTagMapDao customTagMapDao;

	public SerarchTagGroupTagsOut groupTagsSearch(String method, String userToken, String tagGroupName) {
		SerarchTagGroupTagsOut out = new SerarchTagGroupTagsOut(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO);
		Taggroup taggroup = new Taggroup();
		taggroup.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		taggroup.setLevel(ApiConstant.INT_ONE);
		if (StringUtils.isNotBlank(tagGroupName)) {
			taggroup.setName(tagGroupName);
		}
		List<Taggroup> taggroupList = taggroupDao.selectByNameFuzzy(taggroup);
		if (null != taggroupList && taggroupList.size() > 0) {
			for (Taggroup tg : taggroupList) {
				TagGroupMap tagGroupMap = new TagGroupMap();
				tagGroupMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				tagGroupMap.setGroupId(tg.getId());
				List<TagGroupMap> tagGroupMapList = tagGroupMapDao.selectList(tagGroupMap);
				if (null != tagGroupMapList && tagGroupMapList.size() > 0) {
					SerarchTagGroupTagsDataOut serarchTagGroupTagsDataOut = new SerarchTagGroupTagsDataOut();
					serarchTagGroupTagsDataOut.setTagGroupId(tg.getId());
					String name = tg.getName();
					serarchTagGroupTagsDataOut.setTagGroupName(name.substring(name.lastIndexOf('-') + 1));
					for (TagGroupMap tgmp : tagGroupMapList) {
						Tag tag = new Tag();
						tag.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
						tag.setId(tgmp.getTagId());
						List<Tag> tagList = tagDao.selectList(tag);
						if (null != tagList && tagList.size() > 0) {
							TagOut tagOut = new TagOut();
							tagOut.setTagId(tgmp.getTagId());
							tagOut.setTagName(tagList.get(0).getName());
							serarchTagGroupTagsDataOut.getTagList().add(tagOut);
						}
					}
					out.getDataCustom().add(serarchTagGroupTagsDataOut);
				}
			}
		}

		// CustomTag customTag = new CustomTag();
		// customTag.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		// if (StringUtils.isNotBlank(tagGroupName)) {
		// customTag.setName(tagGroupName);
		// }
		// List<CustomTag> customTagList =
		// customTagDao.selectByNameFuzzy(customTag);
		// if (CollectionUtils.isNotEmpty(customTagList)) {
		// for (CustomTag ct : customTagList) {
		// CustomTagMap customTagMap = new CustomTagMap();
		// customTagMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		//
		// customTagMap.setTagId(ct.getId());
		// List<CustomTagMap> customTagMapList =
		// customTagMapDao.selectList(customTagMap);
		// if (CollectionUtils.isNotEmpty(customTagList)) {
		// SerarchTagGroupTagsDataOut serarchTagGroupTagsDataOut = new
		// SerarchTagGroupTagsDataOut();
		// serarchTagGroupTagsDataOut.setTagGroupId(ct.getId());
		// serarchTagGroupTagsDataOut.setTagGroupName(ct.getName());
		// for (CustomTagMap ctmp : customTagMapList) {
		// Tag tag = new Tag();
		// tag.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		// tag.setId(tgmp.getTagId());
		// List<Tag> tagList = tagDao.selectList(tag);
		// if (null != tagList && tagList.size() > 0) {
		// TagOut tagOut = new TagOut();
		// tagOut.setTagId(tgmp.getTagId());
		// tagOut.setTagName(tagList.get(0).getName());
		// serarchTagGroupTagsDataOut.getTagList().add(tagOut);
		// }
		// }
		//
		//
		//
		// }
		//
		//
		// }
		// }

		out.setTotal(out.getDataCustom().size());
		return out;
	}
}
