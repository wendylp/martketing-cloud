package cn.rongcapital.mkt.vo;

import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class AddResult extends BaseOutput{

	@SuppressWarnings("unused")
	private AddResult(){};	
	public AddResult(int code, String message){
		super(code,message);
	}
  
	private Integer id;

	@JsonProperty("id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}

