package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class ColumnsOut {

	private String colName;
	
	private String colCode;

	@JsonProperty("col_name")
	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	@JsonProperty("col_code")
	public String getColCode() {
		return colCode;
	}

	public void setColCode(String colCode) {
		this.colCode = colCode;
	}
	
}
