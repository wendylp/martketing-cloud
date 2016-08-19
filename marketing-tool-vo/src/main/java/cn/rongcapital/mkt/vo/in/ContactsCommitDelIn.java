package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

public class ContactsCommitDelIn {
	@NotNull
	private Integer contact_id;
	@NotNull
	private Integer commit_id;

	public Integer getContact_id() {
		return contact_id;
	}

	public void setContact_id(Integer contact_id) {
		this.contact_id = contact_id;
	}

	public Integer getCommit_id() {
		return commit_id;
	}

	public void setCommit_id(Integer commit_id) {
		this.commit_id = commit_id;
	}
	
	 
}
