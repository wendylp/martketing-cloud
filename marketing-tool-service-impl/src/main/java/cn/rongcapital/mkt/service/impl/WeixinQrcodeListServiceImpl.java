package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.po.Tag;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.po.WechatQrcodeFocus;
import cn.rongcapital.mkt.service.WeixinQrcodeListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class WeixinQrcodeListServiceImpl implements WeixinQrcodeListService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;

	@Autowired
	private WechatChannelDao wechatChannelDao;

	@Autowired
	private WechatQrcodeFocusDao wechatQrcodeFocusDao;
	
	@Autowired
	private TagDao tagDao;

	/**
	 * 根据公众号名称、失效时间、状态、二维码名称查询二维码列表
	 * 接口：mkt.weixin.qrcode.list 
	 * @author shuiyangyang
	 * @Data 2016.08.19
	 */
	@Override
	public BaseOutput getWeixinQrcodeList(String wxmpName, Integer expirationTime, Byte qrcodeStatus, int index, int size) {
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		WechatQrcode wechatQrcode = new WechatQrcode();

		if(wxmpName != null && !wxmpName.isEmpty() && !wxmpName.equals("0")) {
			wechatQrcode.setWxAcct(wxmpName);
		}
		
		if(expirationTime != null && getExpirationTime(expirationTime) != null) {
			wechatQrcode.setExpirationTime(getExpirationTime(expirationTime));
		}
		
		/*
		 * 如果qrcodeStatus==0查询除删除以外的数据
		 */
		if(qrcodeStatus != null) {
//			if(qrcodeStatus == 2) {
//				qrcodeStatus++;
//			}
			wechatQrcode.setStatus(Byte.valueOf(qrcodeStatus));
		} else {
			wechatQrcode.setStatus((byte)0);
		}
		
		wechatQrcode.setPageSize(size);
		wechatQrcode.setStartIndex((index-1)*size);
		
		List<WechatQrcode> wechatQrcodeLists = wechatQrcodeDao.selectListExpirationTime(wechatQrcode);// 如果修改表结构需要修改对应的mapper文件
		//查询总条数用
		wechatQrcode.setStartIndex(null);
		wechatQrcode.setPageSize(null);
		List<WechatQrcode> countList = wechatQrcodeDao.selectListExpirationTime(wechatQrcode);
		
		result.setTotal(wechatQrcodeLists.size());
		if (wechatQrcodeLists != null && !wechatQrcodeLists.isEmpty()) {
			result = addData(result, wechatQrcodeLists);
		}
		result.setTotalCount(countList.size());
		return result;
	}
	
	/**
	 * 根据输入的二维码名称模糊查询表wechat_qrcode
	 * 接口：mkt.weixin.qrcode.list.qrname
	 * @author shuiyangyang
	 * @Data 2016.08.19
	 */
	@Override
	public BaseOutput getWeixinQrcodeListQrname(String qrcodeName, int index, int size) {
		
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		
		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setQrcodeName(qrcodeName);
		wechatQrcode.setPageSize(size);
		wechatQrcode.setStartIndex((index-1)*size);


		
		List<WechatQrcode> wechatQrcodeLists = wechatQrcodeDao.fuzzySearchQrcodeName(wechatQrcode);
		result.setTotal(wechatQrcodeLists.size());
		wechatQrcode = new WechatQrcode();
		wechatQrcode.setQrcodeName(qrcodeName);
		wechatQrcode.setStartIndex(null);
		wechatQrcode.setPageSize(null);
		List<WechatQrcode> wqList = wechatQrcodeDao.fuzzySearchQrcodeName(wechatQrcode);
		if (wechatQrcodeLists != null && !wechatQrcodeLists.isEmpty()) {
			result = addData(result, wechatQrcodeLists);
		} else {
			logger.debug("根据微信号名：{}查不到信息",qrcodeName);
		}
		result.setTotalCount(wqList.size());
		
		return result;
	}
	
	/**
	 * 把查询出来的结果按格式放到 result
	 * @param result
	 * @param wechatQrcodeLists
	 * @return
	 * @author shuiyangyang
	 * @Data 2016.08.19
	 */
	private BaseOutput addData(BaseOutput result, List<WechatQrcode> wechatQrcodeLists) {
		
			//result.setTotal(wechatQrcodeLists.size());
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (WechatQrcode wechatQrcodeList : wechatQrcodeLists) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("id", wechatQrcodeList.getId());
				
				map.put("qrcode_pic", ApiConstant.return_img_path_small + wechatQrcodeList.getQrcodeUrl());// 返回二维码图片文件url 
																		
				map.put("qrcode_name", wechatQrcodeList.getQrcodeName());

				// 获取 渠道名
				WechatChannel wechatChannel = new WechatChannel();
				wechatChannel.setId(wechatQrcodeList.getChCode());
				List<WechatChannel> wechatChannelLists = wechatChannelDao.selectList(wechatChannel);
				String chName = "";
				if(wechatChannelLists != null && !wechatChannelLists.isEmpty()) {
					chName = wechatChannelLists.get(0).getChName();
				}
				
				map.put("ch_name", chName);
				if(wechatQrcodeList.getExpirationTime() != null) {
					map.put("expiration_time", format.format(wechatQrcodeList.getExpirationTime()));
				} else {
					map.put("expiration_time", wechatQrcodeList.getExpirationTime());
				}
				
				map.put("qrcode_status", statusToString(wechatQrcodeList.getStatus()));

				map.put("qrcode_tag", getRelatedTags(wechatQrcodeList.getRelatedTags()));

				// 获取 关注者数
				WechatQrcodeFocus wechatQrcodeFocus = new WechatQrcodeFocus();
				wechatQrcodeFocus.setQrcodeId(wechatQrcodeList.getId().toString());
				int focusCount = wechatQrcodeFocusDao.selectListCount(wechatQrcodeFocus);
				
				map.put("focus_count", focusCount);

				result.getData().add(map);
			}
		
		
		return result;
	}
	
	private Date getExpirationTime(Integer expirationTimeInteger) {
		Date expirationTime = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(expirationTime);
		switch(expirationTimeInteger.intValue()) {
			case 0 : return null;
			case 1 : calendar.add(Calendar.DATE, 3); break;
			case 2 : calendar.add(Calendar.DATE, 7); break;
			case 3 : calendar.add(Calendar.MONTH, 1); break;
			case 4 : calendar.add(Calendar.MONTH, 3); break;
			case 5 : calendar.add(Calendar.MONTH, 6); break;
			case 6 : calendar.add(Calendar.YEAR, 1); break;
			case 7 : calendar.add(Calendar.YEAR, 3); break;
			case 8 : calendar.add(Calendar.YEAR, 10); break;
		}
		return calendar.getTime();
	}
	
	// 根据状态获取对应的文字
	private String statusToString(Byte status) {
		if(status != null){
            switch (status){
                case 1:
                    return "使用中";
                case 2:
                    return "已删除";
                case 3:
                    return "已失效";
            }
        }
        return "删除";
	}
	
	// 根据标签id获取标签name
	private String getRelatedTags(String tagStr) {
		String tagNameString = "";
		if(tagStr != null && tagStr.length()>=0) {
			String[] tagStrLists = tagStr.split(";");
			for(String tagStrList : tagStrLists) {
				Tag tag = new Tag();
				try {
					tag.setId(Integer.valueOf(tagStrList));
				} catch (NumberFormatException e) {
					logger.debug("标签tagStrList=“{}”转换到Integer失败", tagStrList);
					continue;
					//e.printStackTrace();
				}
				List<Tag> tagLists = tagDao.selectList(tag);
				if(tagLists != null && tagLists.size()>0) {
					tagNameString = tagNameString + tagLists.get(0).getName() + ";";
				} else {
					logger.debug("标签id=“{}”在tag表中不存在", tagStrList);
				}
			}

			if(tagNameString.length()>0) {
				tagNameString = tagNameString.substring(0, tagNameString.lastIndexOf(";"));
			}
		}
		
		return tagNameString;
	}

}
