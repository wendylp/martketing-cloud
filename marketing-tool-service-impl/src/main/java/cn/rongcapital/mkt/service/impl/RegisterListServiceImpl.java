/*************************************************
 * @功能简述: AudienceListService实现类
 * @see: MkyApi
 * @author: 程金成
 * @version: 1.0
 * @date: 2016/8/11
*************************************************/


package cn.rongcapital.mkt.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.AudienceColumnsDao;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.po.WechatRegister;
import cn.rongcapital.mkt.service.RegisterListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;
import org.apache.commons.collections4.CollectionUtils;

@Service
public class RegisterListServiceImpl implements RegisterListService {
 	
	@Autowired
	private WechatRegisterDao wechatRegisterDao;
	
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput getRegisterList() {
		
		WechatRegister param = new WechatRegister();
		param.setType(2);
		param.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		List<WechatRegister> reList = wechatRegisterDao.selectList(param);
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				   ApiErrorCode.SUCCESS.getMsg(),
				   ApiConstant.INT_ZERO,null);
		
		if (CollectionUtils.isNotEmpty(reList)) {
			result.setTotal(reList.size());
			for (WechatRegister s : reList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("wxmp_id", s.getId());
				map.put("head_image", s.getHeaderImage());
				map.put("wx_acct", s.getWxAcct());
				map.put("name", s.getName());
				result.getData().add(map);
			}
		}
		
		result.setDate(DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd"));
		
		return result;
	}
	
	@Override
	public BaseOutput selectRegisterList() {
		BaseOutput baseOutput = newSuccessBaseOutput();
		WechatRegister param = new WechatRegister();
		param.setType(2);
		param.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		List<WechatRegister> dataList = wechatRegisterDao.selectList(param);
		this.setBaseOut(baseOutput, dataList);
		baseOutput.setDate(DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd"));
		return baseOutput;
	}

	private BaseOutput newSuccessBaseOutput() {
		return new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
				null);
	}
	
	private <O> void setBaseOut(BaseOutput out, List<O> dataList) {
		if (CollectionUtils.isEmpty(dataList)) {
			return;
		}
		out.setTotal(dataList.size());
		out.getData().addAll(dataList);
	}
}
