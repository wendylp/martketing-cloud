package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface ContactListGetByStatusService {
		BaseOutput getContactList(Integer contactStatus,String contactId, String contactName, int index, int size);
}
