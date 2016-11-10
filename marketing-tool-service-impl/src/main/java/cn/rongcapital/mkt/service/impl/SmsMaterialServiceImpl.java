package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.service.SmsMaterialService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsMaterialIn;
@Service
public class SmsMaterialServiceImpl implements SmsMaterialService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private final String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
	
	@Autowired
	private SmsMaterialDao smsMaterialDao;
	
	@Autowired
	private SmsTaskHeadDao smsTaskHeadDao;
	
	@Autowired
	private SmsTempletDao smsTempletDao;
	
	@Override
	public BaseOutput insertOrUpdateSmsMaterial(SmsMaterialIn smsMaterialIn) {
		BaseOutput output = this.newSuccessBaseOutput();
		SmsMaterial smsMaterial = this.getSmsMaterial(smsMaterialIn);		
		if(smsMaterial!=null){
			if(smsMaterial.getId()!=null&&smsMaterial.getId()!=0){
				if(smsMaterialValidate(smsMaterial.getId())){
					smsMaterialDao.updateById(smsMaterial);
				}else{
					output.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
					output.setMsg(ApiErrorCode.VALIDATE_ERROR.getMsg());
				}				
			}else{
				smsMaterialDao.insert(smsMaterial);
			}
			List<Object> smsMaterials = new ArrayList<Object>();
			smsMaterials.add(smsMaterial);
			output.setData(smsMaterials);
			output.setDate(DateUtil.getStringFromDate(new Date(),FORMAT_STRING));			
		}else{
			output.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
			output.setMsg(ApiErrorCode.PARAMETER_ERROR.getMsg());
		}		
		return output;
	}

	@Override
	public BaseOutput deleteSmsMaterial(Integer id){
		BaseOutput output = this.newSuccessBaseOutput();
		if(id!=null&&id!=0){
				if(smsMaterialValidate(id)){
					SmsMaterial smsMaterial = new SmsMaterial();
					smsMaterial.setId(id);
					smsMaterial.setStatus(NumUtil.int2OneByte(StatusEnum.DELETED.getStatusCode()));
					smsMaterialDao.updateById(smsMaterial);
				}else{
					output.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
					output.setMsg(ApiErrorCode.VALIDATE_ERROR.getMsg());
				}
		}else{
			output.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
			output.setMsg(ApiErrorCode.PARAMETER_ERROR.getMsg());			
		}		
		return output;		
	}
	
	public boolean smsMaterialValidate(Integer id){
		SmsTaskHead smsTaskHeadTemp = new SmsTaskHead();
		smsTaskHeadTemp.setSmsTaskMaterialId(Long.parseLong(String.valueOf(id)));
		smsTaskHeadTemp.setSmsTaskStatus(4);
		smsTaskHeadTemp.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
		List<SmsTaskHead> smsTaskHeads = smsTaskHeadDao.selectListByMaterial(smsTaskHeadTemp);
		if(CollectionUtils.isNotEmpty(smsTaskHeads)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 根据输入得到素材对象
	 * @param smsMaterialIn
	 * @return
	 */
	private SmsMaterial getSmsMaterial(SmsMaterialIn smsMaterialIn){
		SmsMaterial smsMaterial = new SmsMaterial();
		if(smsMaterialIn!=null){			
			if(smsMaterialIn.getId()!=null&&smsMaterialIn.getId()!=0){
				smsMaterial.setId(smsMaterialIn.getId());
				if(StringUtils.isNotEmpty(smsMaterialIn.getUpdateUser())){
					smsMaterial.setUpdateUser(smsMaterialIn.getUpdateUser());
				}
				smsMaterial.setUpdateTime(new Date());
			}else{
				if(StringUtils.isNotEmpty(smsMaterialIn.getCreator())){
					smsMaterial.setCreator(smsMaterialIn.getCreator());
				}
				smsMaterial.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
				smsMaterial.setCreateTime(new Date());
			}
			if(StringUtils.isNotEmpty(smsMaterialIn.getName())){
				smsMaterial.setName(smsMaterialIn.getName());
			}
			if(smsMaterialIn.getSmsTempletId()!=null&&smsMaterialIn.getSmsTempletId()!=0){
				smsMaterial.setSmsTempletId(smsMaterialIn.getSmsTempletId());
				SmsTemplet smsTempletTemp = new SmsTemplet();
				smsTempletTemp.setId(smsMaterialIn.getSmsTempletId());
				List<SmsTemplet> smsTemplets = smsTempletDao.selectList(smsTempletTemp);
				if(CollectionUtils.isNotEmpty(smsTemplets)){
					SmsTemplet smsTempletBack = smsTemplets.get(0);
					if(smsTempletBack!=null){
						smsMaterial.setChannelType(smsTempletBack.getChannelType());
					}
				}
			}
			if(StringUtils.isNotEmpty(smsMaterialIn.getSmsTempletContent())){
				smsMaterial.setSmsTempletContent(smsMaterialIn.getSmsTempletContent());
			}
			if(smsMaterialIn.getSmsSignId()!=null&&smsMaterialIn.getSmsSignId()!=0){
				smsMaterial.setSmsSignId(smsMaterialIn.getSmsSignId());
			}
			if(StringUtils.isNotEmpty(smsMaterialIn.getSmsSignName())){
				smsMaterial.setSmsSignName(smsMaterialIn.getSmsSignName());
			}
			if(smsMaterialIn.getSmsType()!=null){
				smsMaterial.setSmsType(NumUtil.int2OneByte(smsMaterialIn.getSmsType()));
			}
			
			return smsMaterial;
		}
		return null;		
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
	
	public void setSmsMaterialDao(SmsMaterialDao smsMaterialDao) {
		this.smsMaterialDao = smsMaterialDao;
	}

	
	
}
