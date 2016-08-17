package cn.rongcapital.mkt.vo.out;

import java.util.List;

public class ContactListInfoOut {
	private String contact_id;
	private String contact_name;
	private String qrcode_url;
	private String qrcode_pic;
	private String contact_status;
	private List<Field_List> field_list;

	public String getContact_id() {
		return contact_id;
	}

	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getQrcode_url() {
		return qrcode_url;
	}

	public void setQrcode_url(String qrcode_url) {
		this.qrcode_url = qrcode_url;
	}

	public String getQrcode_pic() {
		return qrcode_pic;
	}

	public void setQrcode_pic(String qrcode_pic) {
		this.qrcode_pic = qrcode_pic;
	}

	public String getContact_status() {
		return contact_status;
	}

	public void setContact_status(String contact_status) {
		this.contact_status = contact_status;
	}

	public List<Field_List> getField_list() {
		return field_list;
	}

	public void setField_list(List<Field_List> field_list) {
		this.field_list = field_list;
	}
}
