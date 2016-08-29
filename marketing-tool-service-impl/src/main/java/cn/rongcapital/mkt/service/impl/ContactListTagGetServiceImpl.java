package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.service.ContactListTagGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.ContactListTagOut;
import cn.rongcapital.mkt.vo.out.Contact_Tags;

@Service
public class ContactListTagGetServiceImpl implements ContactListTagGetService {

	@Autowired
	ContactTemplateDao contactTemplateDao;
	@Autowired
	CustomTagMapDao customTagMapDao;
	@Autowired
	CustomTagDao customTagDao;

	@Override
	public BaseOutput getContactListTag(Integer contactId) {
		ContactTemplate contactTemplate = new ContactTemplate();
		contactTemplate.setContactId(Long.parseLong(contactId.toString()));
		List<ContactTemplate> contactTemplates = contactTemplateDao.selectList(contactTemplate);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		if (CollectionUtils.isNotEmpty(contactTemplates)) {
			result.setTotal(contactTemplates.size());
			ContactListTagOut contactListTagOut = new ContactListTagOut();
			List<Contact_Tags> contact_Tags = new ArrayList<>();
			contactListTagOut.setContact_tags(contact_Tags);
			Long cId = contactTemplates.get(0).getContactId();
			if (null != cId) {
				contactListTagOut.setContact_id(cId.toString());
				CustomTagMap customTagMap = new CustomTagMap();
				customTagMap.setMapId(Integer.parseInt(cId.toString()));
				List<CustomTagMap> customTagMaps = customTagMapDao.selectList(customTagMap);
				if (CollectionUtils.isNotEmpty(customTagMaps)) {
					for (CustomTagMap custom : customTagMaps) {
						// List<CustomTag> customs = new ArrayList<>();
						CustomTag customTag = new CustomTag();
						customTag.setId(custom.getTagId());
						List<CustomTag> customTags = customTagDao.selectList(customTag);
						if (CollectionUtils.isNotEmpty(customTags)) {
							List<Contact_Tags> contacttags = new ArrayList<>();
							for (CustomTag c : customTags) {
								Contact_Tags contactTags = new Contact_Tags();
								contactTags.setTag_id(c.getId().toString());
								contactTags.setTag_name(c.getName());
								contacttags.add(contactTags);
							}
							contactListTagOut.getContact_tags().addAll(contacttags);
						}
					}
				}
				result.getData().add(contactListTagOut);
			}
		}
		return result;
	}

}