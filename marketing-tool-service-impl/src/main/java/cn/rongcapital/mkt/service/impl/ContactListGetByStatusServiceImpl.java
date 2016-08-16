package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactListDao;
import cn.rongcapital.mkt.po.ContactList;
import cn.rongcapital.mkt.service.ContactListGetByStatusService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by zhaoguoying on 2016-08-12.
 */
@Service
public class ContactListGetByStatusServiceImpl implements ContactListGetByStatusService {

	@Autowired
	ContactListDao contactListdao;

	@Override
	public BaseOutput getContactList(Integer contactStatus, String contactId) {
		ContactList contactList = new ContactList();
		contactList.setStatus(contactStatus);
		if (contactId != null)
			contactList.setId(Integer.parseInt(contactId));

		List<ContactList> contactLists = contactListdao.selectList(contactList);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		if (CollectionUtils.isNotEmpty(contactLists)) {
			result.setTotal(contactLists.size());
			Map<String, Object> contactListMap = new HashMap<String, Object>();
			for (ContactList w : contactLists) {
				contactListMap.put("contact_id", w.getId());
				contactListMap.put("contact_name", w.getName());
				result.getData().add(contactListMap);
			}
		}
		return result;
	}

}
