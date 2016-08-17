package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactList;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContacsCommitSaveService;
import cn.rongcapital.mkt.service.ContactListUsedService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactStatusUpdateIn;
import cn.rongcapital.mkt.vo.in.ContactsCommitSaveIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;
@Service
public class ContactsCommitSaveServiceImpl implements ContacsCommitSaveService{

	@Autowired
	ContactTemplateDao contactDao;
	
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput contactsCommitSave(ContactsCommitSaveIn body)
	{	
		ContactList contact = new ContactList();
		contact.setName(body.getName());
		contact.setGender(body.getGender());
		contact.setMobile(body.getMobile());
		contact.setEmail(body.getMobile());
		
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO,
				null);
		
		return result;
	}
}
