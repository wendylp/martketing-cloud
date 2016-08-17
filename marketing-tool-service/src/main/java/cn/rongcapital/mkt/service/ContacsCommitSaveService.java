package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactStatusUpdateIn;
import cn.rongcapital.mkt.vo.in.ContactsCommitSaveIn;

/*
 * created by chengjincheng 2016-08-16
 */
public interface ContacsCommitSaveService {
	BaseOutput contactsCommitSave(ContactsCommitSaveIn body);
}
