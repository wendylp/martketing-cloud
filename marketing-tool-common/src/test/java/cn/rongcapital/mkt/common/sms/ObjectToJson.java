package cn.rongcapital.mkt.common.sms;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

public class ObjectToJson {
	@Test
	public void testJSON() {
		Map<String, Sms> map = new HashMap<String, Sms>();
		for (int i = 0; i < 5; i++) {
			map.put("156" + i, new Sms("200", "succes"));
		}
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("errcode", "200");
		rs.put("errmsg", "success");
		rs.put("data", map);

		JSONObject json = JSONObject.fromObject(rs);
		System.out.println(json.toString());
	}

	public class Sms {
		private String errcode;
		private String errmsg;

		public Sms(String errcode, String errmsg) {
			this.errcode = errcode;
			this.errmsg = errmsg;
		}

		public String getErrcode() {
			return errcode;
		}

		public void setErrcode(String errcode) {
			this.errcode = errcode;
		}

		public String getErrmsg() {
			return errmsg;
		}

		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}

	}
}
