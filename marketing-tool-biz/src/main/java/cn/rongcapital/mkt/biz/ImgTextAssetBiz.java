package cn.rongcapital.mkt.biz;

import java.util.List;

import cn.rongcapital.mkt.po.ImgTextAsset;

public interface ImgTextAssetBiz {

	public List<ImgTextAsset> getMaterialList(String authAppId,String authorizer_refresh_token,String type);
	
	public Long getMaterialCount(String authAppId,String authorizer_refresh_token);
}
