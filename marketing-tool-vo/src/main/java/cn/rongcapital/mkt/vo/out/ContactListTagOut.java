package cn.rongcapital.mkt.vo.out;

import java.util.List;

public class ContactListTagOut {
	private String contact_id;
	private List<Contact_Tags> contact_tags;

	public String getContact_id() {
		return contact_id;
	}

	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}

	public List<Contact_Tags> getContact_tags() {
		return contact_tags;
	}

	public void setContact_tags(List<Contact_Tags> contact_tags) {
		this.contact_tags = contact_tags;
	}
}
