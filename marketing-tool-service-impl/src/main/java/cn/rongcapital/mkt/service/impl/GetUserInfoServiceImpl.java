package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.UserInfoDao;
import cn.rongcapital.mkt.po.UserInfo;
import cn.rongcapital.mkt.service.GetUserInfoService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.UserInfoIn;

@Service
public class GetUserInfoServiceImpl implements GetUserInfoService{

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public BaseOutput getUserInfo(String userId) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        //返回结果集
        Map<String, Object> resultMap = new HashMap<>();
        UserInfo userInfo = userInfoDao.getUserInfo(userId);
        if(null != userInfo){
            resultMap.put("org_id", userInfo.getOrgId());
            resultMap.put("org_name", userInfo.getOrgName());
        }else{
        	baseOutput.setCode(ApiErrorCode.THE_PRESON_NOT_FOUND.getCode());
    		baseOutput.setMsg(ApiErrorCode.THE_PRESON_NOT_FOUND.getMsg());
			return baseOutput;
        }
        baseOutput.getData().add(resultMap);
        return baseOutput;
    }
    

    @Override
public BaseOutput getUserMappInfo(UserInfoIn userInfo) {
   UserInfo userInfoT = userInfoDao.getMappingUserInfo(userInfo.getUserId(), userInfo.getUserCode());
   if(userInfoT==null)
   {
       userInfoT=new UserInfo();
       BeanUtils.copyProperties(userInfo, userInfoT);
       userInfoDao.insert(userInfoT);
       
   }
   return getUserInfo(userInfoT.getUserId());
}
}
