package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.UserInfoDao;
import cn.rongcapital.mkt.dao.dataauth.OrganizationDao;
import cn.rongcapital.mkt.dataauth.po.Organization;
import cn.rongcapital.mkt.po.UserInfo;
import cn.rongcapital.mkt.service.GetUserInfoService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class GetUserInfoServiceImpl implements GetUserInfoService{

    @Autowired
    private UserInfoDao userInfoDao;
    
    @Autowired
    private OrganizationDao organizationDao;

    @Override
    public BaseOutput getUserInfo(String userId) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        //返回结果集
        Map<String, Object> resultMap = new HashMap<>();
        UserInfo userInfo = userInfoDao.getUserInfo(userId);
        if(null != userInfo){
        	Integer orgId = userInfo.getOrgId();
        	if(orgId == null){
        		baseOutput.setCode(ApiErrorCode.ORG_ID_IS_NULL_ERROR.getCode());
        		baseOutput.setMsg(ApiErrorCode.ORG_ID_IS_NULL_ERROR.getMsg());
    			return baseOutput;
        	}
        	
        	Organization organization = organizationDao.getNodeById(Long.valueOf(orgId));
        	if(organization == null){
        		baseOutput.setCode(ApiErrorCode.ORG_IS_NOT_FOUND.getCode());
        		baseOutput.setMsg(ApiErrorCode.ORG_IS_NOT_FOUND.getMsg());
        		return baseOutput;
        	}
            resultMap.put("org_id", organization.getOrgId());
            resultMap.put("org_name", organization.getName());
        }else{
        	baseOutput.setCode(ApiErrorCode.THE_PRESON_NOT_FOUND.getCode());
    		baseOutput.setMsg(ApiErrorCode.THE_PRESON_NOT_FOUND.getMsg());
			return baseOutput;
        }
        baseOutput.getData().add(resultMap);
        return baseOutput;
    }
}
