/*************************************************
 * @功能简述: 保存固定人群服务实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/4/12
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dataauth.interceptor.DataAuthPut;
import cn.rongcapital.mkt.dataauth.interceptor.ParamType;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.service.AudienceCreateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.AudienceCreateIn;

@Service
public class AudienceCreateServiceImpl implements AudienceCreateService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AudienceListDao audienceListDao;

    @Autowired
    private AudienceListPartyMapDao audienceListPartyMapDao;

    @Override
    @ReadWrite(type = ReadWriteType.WRITE)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @DataAuthPut(resourceType = "audience_list", orgId = "#in.orgId", outputResourceId = "code == T(cn.rongcapital.mkt.common.constant.ApiErrorCode).SUCCESS.getCode() && data!=null && data.size()>0?data[0][id]:null", type = ParamType.SpEl)
    public BaseOutput createAudience(AudienceCreateIn in) {
        logger.info("target audience {} is creating.", in.getName());
        Date now = new Date();

        // 人群名称重复验证
        AudienceList audienceCondition = new AudienceList();
        audienceCondition.setAudienceName(in.getName());
        audienceCondition.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        int count = audienceListDao.selectListCount(audienceCondition);
        if (count > 0) {
            return new BaseOutput(ApiErrorCode.VALIDATE_ERROR.getCode(), "目标人群名称重复", ApiConstant.INT_ZERO, null);
        }
        // 保存人群
        AudienceList audienceSave = new AudienceList();
        audienceSave.setAudienceName(in.getName());
        audienceSave.setAudienceRows(in.getDetails().size());
        audienceSave.setSource(in.getSource());
        audienceSave.setCreateTime(now);
        audienceListDao.insert(audienceSave);

        // 保存人群明细
        Map<String, Object> paramMap = null;
        List<Map<String, Object>> paramInsertLists = new ArrayList<>();
        for (Integer mid : in.getDetails()) {
            if (mid != null) {
                paramMap = new HashMap<>();
                paramMap.put("audience_list_id", audienceSave.getId());
                paramMap.put("party_id", mid);
                paramMap.put("create_time", now);
                paramInsertLists.add(paramMap);
            }
        }
        audienceListPartyMapDao.batchInsert(paramInsertLists);
        logger.info("target audience {} is created.", in.getName());

        BaseOutput output =
                new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
                        null);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", audienceSave.getId());
        output.getData().add(map);
        output.setTotal(output.getData().size());
        return output;
    }

}
