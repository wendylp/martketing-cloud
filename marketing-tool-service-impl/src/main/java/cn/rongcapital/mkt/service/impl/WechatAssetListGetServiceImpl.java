package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.biz.WechatPublicAuthBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.po.WechatAssetGroup;
import cn.rongcapital.mkt.service.ReauthWechatAccountService;
import cn.rongcapital.mkt.service.WechatAssetListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by Yunfeng on 2016-6-1.
 */
@Service
public class WechatAssetListGetServiceImpl implements WechatAssetListGetService {

	@Autowired
	private WechatAssetDao wechatAssetDao;
	@Autowired
	private WechatAssetGroupDao wechatAssetGroupDao;
	@Autowired
	private ReauthWechatAccountService reauthWechatAccountService;

	@Autowired
	private WechatPublicAuthBiz wechatPublicAuthBiz;

	@Override
	public Object getWechatAssetList(Integer assetId) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(), ApiErrorCode.DB_ERROR.getMsg(),
				ApiConstant.INT_ZERO, null);
		if (assetId == null) {
			baseOutput.setMsg("请求参数传递错误");
			return Response.ok().entity(baseOutput).build();
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("asset_id", assetId);
		paramMap.put("status", ApiConstant.TABLE_DATA_STATUS_VALID);

		Map<String, Object> assetDetaiMap = wechatAssetDao.selectWechatAssetDetai(paramMap);

		if (assetDetaiMap == null) {
			baseOutput.setMsg("请求参数传递错误");
			return Response.ok().entity(baseOutput).build();
		}
		// setNicknameFlag(assetDetaiMap);
		String authAppid = (String) assetDetaiMap.get("authorizer_appid");
		Boolean flag = wechatPublicAuthBiz.isPubIdGranted(authAppid);
		if(flag == null || !flag){
			resultMap.put("flag",false);
			baseOutput.getData().add(resultMap);
			baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
			baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
			baseOutput.setTotal(baseOutput.getData().size());
			return Response.ok().entity(baseOutput).build();
		}else {
			resultMap.put("flag",flag);
		}

		ArrayList<Map<String, Object>> groups = new ArrayList<Map<String, Object>>();
		
		Date consignationTime = (Date)assetDetaiMap.get("consignation_time");

		String condate = DateUtil.getStringFromDate(consignationTime, "yyyy-MM-dd HH:mm:ss");

		resultMap.put("consignation_time",condate);

		String wxAcct = (String) assetDetaiMap.get("wx_acct");
		resultMap.put("wx_acct",wxAcct);
		resultMap.put("asset_name",assetDetaiMap.get("asset_name"));
		resultMap.put("status",assetDetaiMap.get("status"));
		resultMap.put("nickname",assetDetaiMap.get("nickname"));
		
		String groupIds = (String) assetDetaiMap.get("group_ids");
		if (StringUtils.isNotBlank(groupIds)) {
			Map<String, Object> assetGroupInfo = new HashMap<String, Object>();
			String[] ids = groupIds.split(",");
			if (groupIds.contains(",")) {
				for (String groupId : ids) {
					WechatAssetGroup assetGroup = getAssetGroup(wxAcct, groupId);
					assetGroupInfo = getAssetGroupInfo(assetGroup);
					groups.add(assetGroupInfo);
				}
			} else {
				WechatAssetGroup assetGroup = getAssetGroup(wxAcct, groupIds);
				assetGroupInfo = getAssetGroupInfo(assetGroup);
				groups.add(assetGroupInfo);
			}

			// if(groupIds.contains(",")){
			// String[] ids = groupIds.split(",");
			// for(String id : ids){
			// Map<String,Object> group =
			// wechatAssetGroupDao.selectGroupById(id);
			// addGroupDataToList(groups, group);
			// }
			// }else{
			// Map<String,Object> group =
			// wechatAssetGroupDao.selectGroupById(groupIds);
			// addGroupDataToList(groups, group);
			// }
			resultMap.put("group_data", groups);
		}

		assetDetaiMap.clear();

		baseOutput.getData().add(resultMap);
		//resultMap.clear();
		// if(reauthRelation != null){
		// assetDetaiMap.putAll(reauthRelation);
		// }
		baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
		baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
		baseOutput.setTotal(baseOutput.getData().size());
		return Response.ok().entity(baseOutput).build();
	}

	private WechatAssetGroup getAssetGroup(String wxAcct, String groupId) {
		WechatAssetGroup wechatAssetGroup = new WechatAssetGroup();
		wechatAssetGroup.setImportGroupId(new Long(groupId));
		wechatAssetGroup.setWxAcct(wxAcct);
		List<WechatAssetGroup> selectList = wechatAssetGroupDao.selectList(wechatAssetGroup);
		WechatAssetGroup wechatAGroup = selectList.get(0);
		return wechatAGroup;
	}

	private Map<String, Object> getAssetGroupInfo(WechatAssetGroup wechatAGroup) {
		Map<String, Object> group = new HashMap<String, Object>();
		group.put("group_name", wechatAGroup.getName());
		group.put("member_count", wechatAGroup.getMembers());
		return group;
	}

	private void addGroupDataToList(ArrayList<Map<String, Object>> groups, Map<String, Object> group) {
		if (group != null) {
			group.put("group_name", group.remove("name"));
			group.put("member_count", group.remove("members"));
			groups.add(group);
		}
	}

	private void setNicknameFlag(Map<String, Object> assetDetaiMap) {
		if (assetDetaiMap.remove("nickname") != null) {
			assetDetaiMap.put("nickname_flag", true);
		} else {
			assetDetaiMap.put("nickname_flag", false);
		}
	}

}
