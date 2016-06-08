package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.service.CustomTagDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagDeleteIn;

@Service
public class CustomTagDeleteServiceImpl implements CustomTagDeleteService {

    @Autowired
    private CustomTagDao customTagDao;

    @Override
    public BaseOutput deleteCustomTag(CustomTagDeleteIn body) {
    	Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("id",body.getTagId());
        int rowEffected = customTagDao.logicDeleteTagById(paramMap);
    	
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        if(rowEffected != 0){
        	result.setMsg("success");
        }else{
        	result.setMsg("不存在当前输入id的自定义标签");
        }
        result.setTotal(rowEffected);
        return result;
    }
}
