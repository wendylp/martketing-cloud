/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.WeixinQrcodeDelService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * 删除二维码接口 （逻辑删除，状态改为2）
 * 
 * @author shuiyangyang
 * @Date 2016.08.25
 */
@Service
public class WeixinQrcodeDelServiceImpl implements WeixinQrcodeDelService{

	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;
	
	@Override
	public BaseOutput weixinQrocdeDel(String qrcodeId) {
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ONE,null);
		
		WechatQrcode wechatQrcode = new WechatQrcode();
		
		wechatQrcode.setId(Integer.valueOf(qrcodeId));
		wechatQrcode.setStatus((byte)2);
		
		int count = wechatQrcodeDao.updateById(wechatQrcode);
		
		if(count<=0) {
			result.setTotal(0);
			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
		} else {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", qrcodeId);
			map.put("status", 2);
			result.getData().add(map);
		}
		
		return result;
	}
	
	/**
	 * 删除二维码接口 （微信记录物理删除）
	 * 接口：mkt.weixin.qrcode.records.del
	 * 
	 * @param qrcodeId
	 * @return
	 * @author shuiyangyang
	 * @Date 2016.08.25
	 */
	@Override
	public BaseOutput weixinQrcodeRecordsDel(String qrcodeId) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ONE,null);
		
		WechatQrcode wechatQrcode = new WechatQrcode();
		
		wechatQrcode.setId(Integer.valueOf(qrcodeId));
		
		int count = wechatQrcodeDao.deleteById(wechatQrcode);
		if(count<=0) {
			result.setTotal(0);
			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
		} else {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", qrcodeId);
			result.getData().add(map);
		}
		return result;
	}

}
