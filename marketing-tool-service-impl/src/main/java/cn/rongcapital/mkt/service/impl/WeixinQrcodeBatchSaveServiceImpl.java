/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.WeixinQrcodeBatchSaveService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.AssociationTag;
import cn.rongcapital.mkt.vo.in.WechatQrcodeBatchSaveIn;
import cn.rongcapital.mkt.vo.out.HomePageCalendarPopOut;

/**
 * @author shuiyangyang
 *
 */
@Service
public class WeixinQrcodeBatchSaveServiceImpl implements WeixinQrcodeBatchSaveService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	WechatQrcodeDao wechatQrcodeDao;

	/**
	 * 根据batch_id，在表wechat_qrcode 中查找到对应二维码记录，更新：失效时间、标签、备注信息、状态(status=0)
	 * 接口：mkt.weixin.qrcode.batch.save
	 * 
	 * @author shuiyangyang
	 * @Date 2016.08.26
	 */
	@Override
	public BaseOutput weixinQrcodeBatchSave(String batchId, String expirationTime, String qrcodeTagIds,
			Integer qrcodeStatus) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setBatchId(batchId);

		if (expirationTime != null && !expirationTime.isEmpty()) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar expirationTimeCalendar = Calendar.getInstance();
				expirationTimeCalendar.setTime(simpleDateFormat.parse(expirationTime));
				wechatQrcode.setExpirationTime(expirationTimeCalendar.getTime());
			} catch (ParseException e) {
				logger.error("转换expirationTime到日期出错,expirationTime = {} ", expirationTime);
			}
		}

		wechatQrcode.setRelatedTags(qrcodeTagIds);
		wechatQrcode.setStatus((byte) qrcodeStatus.intValue());

		int count = wechatQrcodeDao.updataByBatchId(wechatQrcode);
		
		
		logger.debug("更新数据id:{}",wechatQrcode.getId());

		return result;
	}

	@Override
	public BaseOutput weixinQrcodeBatchSave(WechatQrcodeBatchSaveIn body) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setBatchId(body.getBatchId());
		
		String expirationTime = body.getExpirationTime();
		
		if (expirationTime != null && !expirationTime.isEmpty()) {
			try {
				Calendar expirationTimeCalendar = Calendar.getInstance();
				expirationTimeCalendar.setTime(simpleDateFormat.parse(expirationTime));
				wechatQrcode.setExpirationTime(expirationTimeCalendar.getTime());
			} catch (ParseException e) {
				logger.error("转换expirationTime到日期出错,expirationTime = {} ", expirationTime);
			}
		}
		
		wechatQrcode.setComments(body.getComment());
		
		List<AssociationTag> associationTags = body.getAssociation_tags();
		List<String> tagList = new ArrayList<String>();
		
		for(AssociationTag at : associationTags){
			tagList.add(String.valueOf(at.getId()));
		}
		StringBuilder related = new StringBuilder();;
		for(String tag :tagList){
			related.append(tag);
			related.append(";");
		}
		if(related != null &&related.length() > 0){
			String relatedTags = related.toString().substring(0,related.length()-1);
			wechatQrcode.setRelatedTags(relatedTags);
		}
		
		wechatQrcodeDao.updataByBatchId(wechatQrcode);
		
		return result;
	}

}
