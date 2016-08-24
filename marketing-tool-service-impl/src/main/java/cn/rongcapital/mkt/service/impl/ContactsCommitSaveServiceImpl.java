package cn.rongcapital.mkt.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.ContactListDao;
import cn.rongcapital.mkt.po.ContactList;
import cn.rongcapital.mkt.service.ContacsCommitSaveService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactsCommitDelIn;
import cn.rongcapital.mkt.vo.in.ContactsCommitSaveIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class ContactsCommitSaveServiceImpl implements ContacsCommitSaveService {

	@Autowired
	ContactListDao contactDao;

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput contactsCommitSave(ContactsCommitSaveIn body) {
		ContactList contact = new ContactList();
		contact.setContactTemplId(body.getContact_templ_id());
		contact.setName(body.getName());
		contact.setGender(body.getGender());
		contact.setAge(body.getAge());
		contact.setMobile(body.getMobile());
		contact.setEmail(body.getEmail());
		contact.setAddress(body.getAddress());

		contactDao.insert(contact);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		return result;
	}

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput contactsCommitGet(Integer contact_id, Integer commit_time) {
		ContactList contact = new ContactList();
		contact.setContactTemplId(contact_id);

		Date startTime = null;
		Date endTime = null;
		Calendar canlendar = Calendar.getInstance();
		switch (commit_time) {
		case 1:
			startTime = DateUtil.getDateFromString(DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
			endTime = new Date();
			break;
		case 2:
			canlendar.add(Calendar.WEEK_OF_YEAR, -1);
			startTime = canlendar.getTime();
			endTime = new Date();
			break;
		case 3:
			canlendar.add(Calendar.MONTH, -1);
			startTime = canlendar.getTime();
			endTime = new Date();
			break;
		case 4:
			canlendar.add(Calendar.MONTH, -3);
			startTime = canlendar.getTime();
			endTime = new Date();
			break;
		}
		contact.setStartTime(startTime);
		contact.setEndTime(endTime);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		List<ContactList> list = contactDao.selectListByContactIdAndCommitTime(contact);
		if (!CollectionUtils.isEmpty(list)) {
			result.setTotal(list.size());
			for (ContactList item : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id",item.getId());
				map.put("name", item.getName());
				map.put("mobile", item.getMobile());
				map.put("email", item.getEmail());
				map.put("gender", item.getGender());
				map.put("provice", item.getProvice());
				map.put("city", item.getCity());
				map.put("job", item.getJob());
				map.put("source", item.getSource());
				result.getData().add(map);
			}
		}

		return result;
	}

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput contactsCommitDel(ContactsCommitDelIn body) {
		ContactList contact = new ContactList();
		contact.setCommitId(body.getCommit_id());
		contact.setContactTemplId(body.getContact_id());
		contact.setStatus(2);

		contactDao.updateByContactId(contact);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", body.getCommit_id());
		map.put("updatetime", DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		result.setTotal(1);
		result.getData().add(map);

		return result;
	}

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput contactsCommitDownload(Integer contact_id, Integer commit_time) {
		ContactList contact = new ContactList();
		contact.setContactTemplId(contact_id);

		Date startTime = null;
		Date endTime = null;
		Calendar canlendar = Calendar.getInstance();
		switch (commit_time) {
		case 1:
			startTime = DateUtil.getDateFromString(DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
			endTime = new Date();
			break;
		case 2:
			canlendar.add(Calendar.WEEK_OF_YEAR, -1);
			startTime = canlendar.getTime();
			endTime = new Date();
			break;
		case 3:
			canlendar.add(Calendar.MONTH, -1);
			startTime = canlendar.getTime();
			endTime = new Date();
			break;
		case 4:
			canlendar.add(Calendar.MONTH, -3);
			startTime = canlendar.getTime();
			endTime = new Date();
			break;
		}
		contact.setStartTime(startTime);
		contact.setEndTime(endTime);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		List<ContactList> list = contactDao.selectListByContactIdAndCommitTime(contact);
		if (!CollectionUtils.isEmpty(list)) {
			result.setTotal(list.size());
			for (ContactList item : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("download_url", item.getDownloadUrl());
				result.getData().add(map);
			}
		}

		return result;
	}
}
