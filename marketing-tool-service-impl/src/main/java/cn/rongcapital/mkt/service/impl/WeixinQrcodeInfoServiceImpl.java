package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.WeixinQrcodeInfoService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * 
 * 查询二维码详细信息
 * 
 * @author shuiyangyang
 * @Data 2016.08.24
 */
@Service
public class WeixinQrcodeInfoServiceImpl implements WeixinQrcodeInfoService {

	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;

	@Autowired
	private WechatChannelDao wechatChannelDao;

	@Autowired
	CustomTagDao customTagDao;

	@Override
	public BaseOutput getWeiXinQrocdeInfo(String qrcodeId) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ONE, null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setId(Integer.valueOf(qrcodeId));

		List<WechatQrcode> wechatQrcodeLists = wechatQrcodeDao.selectList(wechatQrcode);
		// 如果没有查询结果 设置错误
		if (wechatQrcodeLists == null || wechatQrcodeLists.isEmpty()) {
			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("wxmp_name", wechatQrcodeLists.get(0).getWxName());

			// 根据渠道号获取渠道名
			WechatChannel wechatChannel = new WechatChannel();
			wechatChannel.setId(wechatQrcodeLists.get(0).getChCode());
			List<WechatChannel> wechatChannelLists = wechatChannelDao.selectList(wechatChannel);
			if (!(wechatChannelLists == null) && !wechatChannelLists.isEmpty()) {
				map.put("ch_name", wechatChannelLists.get(0).getChName());
				// 渠道id
				map.put("ch_id", wechatChannelLists.get(0).getId());
			} else {
				map.put("ch_name", "");// 查询不到的时候默认传空字符串
				map.put("ch_id", "");
			}

			map.put("qrcode_name", wechatQrcodeLists.get(0).getQrcodeName());
			// 微信公众号
			map.put("wx_acct", wechatQrcodeLists.get(0).getWxAcct());

			// create_time为空检查
			if (wechatQrcodeLists.get(0).getCreateTime() != null) {
				map.put("create_time", sdf.format(wechatQrcodeLists.get(0).getCreateTime()));
			} else {
				map.put("create_time", "");
			}
			// 日期格式化处理
			Date expirationTime = wechatQrcodeLists.get(0).getExpirationTime();
			expirationTime = null == expirationTime ? new Date() : expirationTime;
			map.put("expiration_time", sdf.format(expirationTime));

			if (wechatQrcodeLists.get(0).getAudienceName() != null) {
				map.put("fixed_audience", wechatQrcodeLists.get(0).getAudienceName());
			} else {
				map.put("fixed_audience", "");
			}
			// 关联标签
			String relatedTag = wechatQrcodeLists.get(0).getRelatedTags();
			if (relatedTag != null && !"".equals(relatedTag)) {
				// 标签查询
				List<CustomTag> customTagList = customTagDao.selectCustomTagsByIds(relatedTag.split(";"));
				List<Map<String, Object>> returnDataList = new ArrayList<>();
				for (CustomTag customTag : customTagList) {
					Map<String, Object> dataMap = new HashMap<>();

					dataMap.put("id", customTag.getId());
					dataMap.put("name", customTag.getName());
					returnDataList.add(dataMap);
				}
				map.put("association_tags", returnDataList);
			} else {
				map.put("association_tags", new ArrayList<String>());
			}

			if (wechatQrcodeLists.get(0).getComments() != null) {
				map.put("comment", wechatQrcodeLists.get(0).getComments());
			} else {
				map.put("comment", "");
			}

			result.getData().add(map);
		}

		result.setDate(DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd"));

		return result;
	}

}
