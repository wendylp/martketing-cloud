package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactListInfoGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.ContactListInfoOut;
import cn.rongcapital.mkt.vo.out.Field_List;

@Service
public class ContactListInfoGetServiceImpl implements ContactListInfoGetService {

	@Autowired
	ContactTemplateDao contactTemplateDao;

	@Override
	public BaseOutput getContactListInfo(String contactId) {

		ContactTemplate contactTemplate = new ContactTemplate();
		contactTemplate.setContactId(Long.parseLong(contactId));
		List<ContactTemplate> contactTemplates = contactTemplateDao.selectList(contactTemplate);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		if (CollectionUtils.isNotEmpty(contactTemplates)) {
			result.setTotal(contactTemplates.size());
			if (contactTemplates.size() > 0) {
				ContactListInfoOut contactListInfoOut = new ContactListInfoOut();
				contactListInfoOut.setContact_id(contactTemplates.get(0).getContactId().toString());
				contactListInfoOut.setContact_name(contactTemplates.get(0).getContactName());
				contactListInfoOut.setQrcode_url(contactTemplates.get(0).getQrcodeUrl());
				// contactListInfoOut.setQrcode_pic(contactTemplates.get(0).getqr);
				contactListInfoOut.setContact_status(contactTemplates.get(0).getStatus().toString());
				List<Field_List> lists = new ArrayList<Field_List>();
				for (ContactTemplate c : contactTemplates) {
					Field_List list = new Field_List();
					list.setField_name(c.getFieldName());
					list.setField_code(c.getFieldCode());
					list.setSelected(c.getSelected());
					// list.setField_type(field_type);
					list.setIndex(c.getFieldIndex().toString());
					list.setRequired(c.getRequired());
					list.setIschecked(c.getIschecked());
					lists.add(list);
				}
				contactListInfoOut.setField_list(lists);
				result.getData().add(contactListInfoOut);
			}
		}
		return result;
	}
}