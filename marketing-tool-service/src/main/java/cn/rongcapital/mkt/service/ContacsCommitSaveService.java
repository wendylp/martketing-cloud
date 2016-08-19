package cn.rongcapital.mkt.service;

import java.util.Date;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactsCommitDelIn;
import cn.rongcapital.mkt.vo.in.ContactsCommitSaveIn;

/*
 * created by chengjincheng 2016-08-16
 */
public interface ContacsCommitSaveService {
	BaseOutput contactsCommitSave(ContactsCommitSaveIn body);
	
	BaseOutput contactsCommitGet(Integer contact_id,Date commit_time);
	
	BaseOutput contactsCommitDel(ContactsCommitDelIn body);
	
	BaseOutput contactsCommitDownload(Integer contact_id,Date commit_time);
}
