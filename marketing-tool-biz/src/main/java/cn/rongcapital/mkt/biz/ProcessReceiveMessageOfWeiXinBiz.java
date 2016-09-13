package cn.rongcapital.mkt.biz;

public interface ProcessReceiveMessageOfWeiXinBiz {

	public void getMsgLog(String textXml,String msg_signature,String timestamp,String nonce,String signature,String openid);
	
}
