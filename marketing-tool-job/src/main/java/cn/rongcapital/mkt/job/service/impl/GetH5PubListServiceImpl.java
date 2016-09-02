package cn.rongcapital.mkt.job.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.biz.WechatRegisterBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.dao.WebchatAuthInfoDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.vo.in.H5MktPubListResponse;
import cn.rongcapital.mkt.job.vo.in.H5Pub;
import cn.rongcapital.mkt.po.WebchatAuthInfo;
import cn.rongcapital.mkt.po.WechatRegister;

/*************************************************
 * @功能及特点的描述简述: 同步微信公众号接口
 * 
 * @see （与该类关联的类): TaskService
 * @对应项目名称: MC系统
 * @author: 白云峰
 * @version: v1.2 @date(创建、开发日期): 2016-6-17 最后修改日期: 2016-09-02
 * @复审人: 丛树林
 *************************************************/
@Service
public class GetH5PubListServiceImpl implements TaskService {

	// Todo:同步大连那边获取服务号，订阅号接口。
	// Todo:1.调用Http请求获取接口返回值 (checked)
	// Todo:2.对接口返回值进行JSON解析，保存到entity类中 (checked)

	@Autowired
	private TenementDao tenementDao;

	@Autowired
	private WechatRegisterDao wechatRegisterDao;

	@Autowired
	private WechatRegisterBiz wechatRegisterBiz;

	@Autowired
	private WebchatAuthInfoDao webchatAuthInfoDao;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void task(Integer taskId) {
		// callH5PlusMethod();
		callWechatSdkMethod();

	}

	private void callWechatSdkMethod() {

		List<WebchatAuthInfo> selectListByIdList = webchatAuthInfoDao.selectList(new WebchatAuthInfo());

		if (!CollectionUtils.isEmpty(selectListByIdList)) {

			for (WebchatAuthInfo info : selectListByIdList) {
				WechatRegister wechatRegister = wechatRegisterBiz.getAuthInfo(info.getAuthorizerAppid(),
						info.getAuthorizerRefreshToken());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("wx_acct", wechatRegister.getWxAcct());
				Long id = wechatRegisterDao.selectPersonalId(map);

				if (id == null) {
					wechatRegisterDao.insert(wechatRegister);
					logger.info("insert into wechat_register id:" + wechatRegister.getId());
				} else {
					wechatRegister.setId(Integer.valueOf(id.toString()));
					wechatRegisterDao.updateById(wechatRegister);
					logger.info("update wechat_register id:" + wechatRegister.getId());
				}
			}
		}
	}

	private void callH5PlusMethod() {
		Map<String, String> h5ParamMap = new HashMap<String, String>();
		h5ParamMap.put("pid", tenementDao.selectPid().get("pid"));
		h5ParamMap.put(ApiConstant.DL_API_PARAM_METHOD, ApiConstant.DL_PUB_LIST_API);
		HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
		if (httpResponse != null) {
			try {
				String entityString = EntityUtils.toString(httpResponse.getEntity());
				if (entityString == null)
					return;
				JSONObject obj = JSON.parseObject(entityString).getJSONObject("hfive_mkt_pub_list_response");
				if (obj != null) {
					H5MktPubListResponse h5MktPubListResponse = JSON.parseObject(obj.toString(),
							H5MktPubListResponse.class);
					// Todo:3.判断pub_id是否已经注册，如果没有注册则属于非法数据，反之属于合法数据，则更新register表中的数据
					checkPublistStatus(h5MktPubListResponse);
					updatePublistInfo(h5MktPubListResponse);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void checkPublistStatus(H5MktPubListResponse h5MktPubListResponse) {
		ArrayList<H5Pub> dirtyH5Pubs = new ArrayList<H5Pub>();
		for (int index = 0; index < h5MktPubListResponse.getPubs().getPub().size(); index++) {
			H5Pub h5Pub = h5MktPubListResponse.getPubs().getPub().get(index);
			Integer status = wechatRegisterDao.selectStatus(h5Pub.getPubId());
			if (status == null || status == 1) {
				dirtyH5Pubs.add(h5Pub);
				logger.info("dirty pubId: " + h5Pub.getPubId());
			}
			logger.info("effective pubId : " + h5Pub.getPubId());
		}
		h5MktPubListResponse.getPubs().getPub().removeAll(dirtyH5Pubs);
	}

	private void updatePublistInfo(H5MktPubListResponse h5MktPubListResponse) {
		List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
		for (H5Pub h5Pub : h5MktPubListResponse.getPubs().getPub()) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("wx_acct", h5Pub.getPubId());
			paramMap.put("name", h5Pub.getPubName());
			paramMap.put("header_image", h5Pub.getHeadUrl());
			if (h5Pub.getPubType() < 2) {
				paramMap.put("type", 2);
			} else {
				paramMap.put("type", 0);
			}
			wechatRegisterDao.updatePubInfo(paramMap);
		}
	}
}
