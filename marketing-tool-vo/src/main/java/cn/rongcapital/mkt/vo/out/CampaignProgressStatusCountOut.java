package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import cn.rongcapital.mkt.vo.BaseOutput;

public class CampaignProgressStatusCountOut extends BaseOutput {

	private List<CampaignProgressStatusCountDataOut> dataCustom = new ArrayList<CampaignProgressStatusCountDataOut>();
	
	public CampaignProgressStatusCountOut(int code, String msg, int total) {
		super(code, msg, total, null);
	}

	public List<CampaignProgressStatusCountDataOut> getDataCustom() {
		return dataCustom;
	}

	public void setDataCustom(List<CampaignProgressStatusCountDataOut> dataCustom) {
		this.dataCustom = dataCustom;
	}
	
}
