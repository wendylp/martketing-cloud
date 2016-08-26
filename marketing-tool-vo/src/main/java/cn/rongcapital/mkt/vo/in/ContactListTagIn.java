package cn.rongcapital.mkt.vo.in;


import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ContactListTagIn extends BaseInput {
	@NotNull
	private Integer contact_id;
	@NotEmpty
	private String[] tag_names;
	
 
	public String[] getTag_names() {
		return tag_names;
	}
	public void setTag_names(String[] tag_names) {
		this.tag_names = tag_names;
	}
	public Integer getContact_id() {
		return contact_id;
	}
	public void setContact_id(Integer contact_id) {
		this.contact_id = contact_id;
	}
	
}
