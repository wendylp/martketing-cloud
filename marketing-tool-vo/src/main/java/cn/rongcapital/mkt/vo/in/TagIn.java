package cn.rongcapital.mkt.vo.in;

public class TagIn {

	public static final String TAG_TYPE_CUSTOM = "1";
	public static final String TAG_TYPE_SYS = "0";

	private String tag_name;
	
	private String tag_id;

	private String tag_type;

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

	public String getTag_type() {
		return tag_type;
	}

	public void setTag_type(String tag_type) {
		this.tag_type = tag_type;
	}	
}
