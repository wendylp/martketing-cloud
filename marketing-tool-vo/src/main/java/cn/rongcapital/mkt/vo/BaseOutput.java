/*************************************************
 * @功能简述: 输入结果VO基类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.vo;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class BaseOutput {
	
	private int code;
	
	private String msg;
	
	private int total;
	
	private List<Object> data = new ArrayList<Object>();

	public BaseOutput(){}
	
	public BaseOutput(int code, String msg, int total, List<Object> data) {
		this.code = code;
		this.msg = msg;
		this.total = total;
		this.data = null == data?this.data:data;
	}
	
	@JsonProperty("code")
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@JsonProperty("msg")
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@JsonProperty("total")
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@JsonProperty("data")
	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}
	
	
}
