package cn.rongcapital.mkt.biz;

import javax.xml.bind.JAXBException;

import com.qq.weixin.mp.aes.AesException;

public interface ProcessReceiveMessageOfWeiXinBiz {

	public void getMsgLog(String textXml,String msg_signature,String timestamp,String nonce,String signature,String openid,String authAppId) throws JAXBException, AesException;
	
}
