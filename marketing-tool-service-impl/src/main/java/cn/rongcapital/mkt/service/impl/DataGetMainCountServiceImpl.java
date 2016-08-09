package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.po.WechatMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.ImportTemplTypeEnum;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.service.DataGetMainCountService;
import cn.rongcapital.mkt.vo.out.DataGetMainCountOut;

@Service
public class DataGetMainCountServiceImpl implements DataGetMainCountService {

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private ImportTemplateDao importTemplateDao;

    @Autowired
    private WechatMemberDao wechatMemberDao;

    private final Integer WECHAT_MD_TYPE = 8;
    private final String WECHAT_NAME = "微信";

    @Override
    public Object getMainCount(String method, String userToken, String ver) {

        DataGetMainCountOut result = new DataGetMainCountOut(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        Map<String, Object> countRowsMap = dataPartyDao.selectMainCount(new HashMap<>());
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<ImportTemplate> importTemplateList = importTemplateDao.selectTemplTypePairs();
        Collections.sort(importTemplateList);
        for (ImportTemplate importTemplate : importTemplateList) {
            Map<String, Object> map = new LinkedHashMap<>();
            String tagName = importTemplate.getTemplName();
            map.put("md_type", importTemplate.getTemplType());
            map.put("tag_name", tagName);
            map.put("count_rows", countRowsMap.get(ImportTemplTypeEnum.getCountNameByName(tagName)));
            
            resultList.add(map);
        }

        //添加微信数据源，从而避免使用大的SQL
        WechatMember wechatMember = new WechatMember();
        wechatMember.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        Integer wechatMemberCount = wechatMemberDao.selectListCount(wechatMember);
        Map<String,Object> wechatRelationResults = new HashMap<String,Object>();
        wechatRelationResults.put("md_type",WECHAT_MD_TYPE);
        wechatRelationResults.put("tag_name",WECHAT_NAME);
        wechatRelationResults.put("count_rows",wechatMemberCount);
        resultList.add(wechatRelationResults);

        Integer dataSourceCount = 0;
        for(Map<String,Object> map : resultList){
            if((Integer)map.get("md_type")!= 0 && map.get("count_rows") != null && Long.valueOf(map.get("count_rows") + "")>0) dataSourceCount++;
        }

        result.setDataSourceCount(dataSourceCount);
        result.getData().addAll(resultList);
        result.setTotal(result.getData().size());
        return Response.ok().entity(result).build();
    }

}
