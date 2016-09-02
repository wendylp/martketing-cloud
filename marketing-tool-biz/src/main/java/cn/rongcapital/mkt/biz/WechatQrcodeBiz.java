package cn.rongcapital.mkt.biz;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatQrcodeIn;

public interface WechatQrcodeBiz {

	BaseOutput getQrcode(int sceneId,String actionName);
	
	BaseOutput getQrcodes(int startSceneId,int endSceneId,String actionName);
	
	BaseOutput createQrcode(WechatQrcodeIn wechatQrcodeIn);
	
	BaseOutput getWechatQrcodeTicket();

}
