package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.service.SaveWechatAssetListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.SaveWechatAssetListIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.*;

/**
 * Created by Yunfeng on 2016-6-1.
 */
@Service
public class SaveWechatAssetListServiceImpl implements SaveWechatAssetListService{

    @Autowired
    private AudienceListDao audienceListDao;
    @Autowired
    private AudienceListPartyMapDao audienceListPartyMapDao;
    @Autowired
    private DataPartyDao dataPartyDao;
    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;
    @Autowired
    private WechatGroupDao wechatGroupDao;
    @Autowired
    private WechatMemberDao wechatMemberDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Object saveWechatAssetList(SaveWechatAssetListIn saveWechatAssetListIn, SecurityContext securityContext) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);

        //Todo:0.现根据传进来的name判断这个人群名称是否已经存在，存在返回，不存在继续下一步
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("audience_name",saveWechatAssetListIn.getPeopleGroupName());
        Long id = audienceListDao.selectIdByAudienceName(paramMap);
        if(id != null){
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
            baseOutput.setMsg("人群名称已经重复");
            return Response.ok().entity(baseOutput).build();
        }

        //Todo:1.统计人群总数，将人群名称和人群总数保存到audience_list表中
        Long totalAudienceNumber = wechatAssetGroupDao.sumGroupMemberCount(saveWechatAssetListIn.getGroupIds());
        paramMap = new HashMap<String,Object>();
        paramMap.put("audience_name",saveWechatAssetListIn.getPeopleGroupName());
        paramMap.put("audience_rows",totalAudienceNumber);
        paramMap.put("create_time",new Date(System.currentTimeMillis()));
        audienceListDao.insertWechatGroups(paramMap);
        id = audienceListDao.selectIdByAudienceName(paramMap);

        //Todo:2.根据groupId选出import_groupId。
        List<Long> importGroudIds = wechatAssetGroupDao.selectImportGroupIdsByIds(saveWechatAssetListIn.getGroupIds());

        //Todo:3.根据import_groupId，选出member的Id
        List<Long> idLists = wechatMemberDao.selectIdListByGroupId(importGroudIds);

        //Todo:4.根据ucode选择出data_party_id
        List<Long> dataPartyIds = dataPartyDao.selectDataPartyIdsByMappinKeyIds(idLists);

        //Todo:5.将dataparty_id和audience_id存入audience_data_mapping表中
        List<Map<String,Object>> paramInsertLists = new ArrayList<Map<String,Object>>();
        for(Long dataPartyId : dataPartyIds){
            paramMap = new HashMap<String,Object>();
            paramMap.put("audience_list_id",id);
            paramMap.put("party_id",dataPartyId);
            paramMap.put("create_time",new Date(System.currentTimeMillis()));
            paramInsertLists.add(paramMap);
        }
        int effectRows = audienceListPartyMapDao.batchInsert(paramInsertLists);

        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        baseOutput.setTotal(effectRows);
        return Response.ok().entity(baseOutput).build();
    }
}
