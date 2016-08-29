package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactListGetByStatusService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by zhaoguoying on 2016-08-12.
 */
@Service
public class ContactListGetByStatusServiceImpl implements ContactListGetByStatusService {

	@Autowired
	ContactTemplateDao contactTemplateDao;

	@Override
	public BaseOutput getContactList(Integer contactStatus, String contactId, String contactName, int index, int size) {
		//ContactList contactList = new ContactList();
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		ContactTemplate contactTemplate = new ContactTemplate();
		contactTemplate.setStatus(contactStatus.byteValue());
		if(contactStatus == 3){
			contactTemplate.setStatus(null);
		}
		if (contactId != null && contactId.length()>0){
			contactTemplate.setContactId(Long.valueOf(contactId));
		}
		if (contactName != null && contactName.length() >0){
			contactTemplate.setContactName(contactName);
		}

		Integer totalCount = contactTemplateDao.selectRealContactTemplateListCount(contactTemplate);
		result.setTotal(totalCount);
		//set index and size
		contactTemplate.setPageSize(size);
		contactTemplate.setStartIndex((index-1)*size);

		List<ContactTemplate> contactTemplateList = contactTemplateDao.selectListGroupByCId(contactTemplate);

		//Map<String, Object> cloMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(contactTemplateList)) {
			List<Object> resultData = result.getData();
			for (ContactTemplate contactTem : contactTemplateList) {
				
				Map<String, Object> contactListMap = new HashMap<String, Object>();
				contactListMap.put("contact_id", contactTem.getContactId());
				contactListMap.put("contact_name", contactTem.getContactName());
				contactListMap.put("qrcode_url", contactTem.getQrcodeUrl());
				contactListMap.put("qrcode_pic", contactTem.getQrcodePic());
				contactListMap.put("user_count", contactTemplateList.size());
				contactListMap.put("contact_status", contactStatus);
				//cloMap.put(contactTemplate.getFieldCode(), contactTemplate.getFieldName());
				resultData.add(contactListMap);
			}
		}
		//result.getColNames().add(cloMap);
		return result;
	}

}
