package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseOutput;

public class SegmentSummaryListOut extends BaseOutput {

	
	List<SegmentSummaryListDataOut> dataCustom = new ArrayList<SegmentSummaryListDataOut>();
	
	public SegmentSummaryListOut(int code, String msg, int total){
		super(code, msg, total, null);
	}

	@JsonProperty("data")
	public List<SegmentSummaryListDataOut> getDataCustom() {
		return dataCustom;
	}

	public void setDataCustom(List<SegmentSummaryListDataOut> dataCustom) {
		this.dataCustom = dataCustom;
	}
	
	
}
