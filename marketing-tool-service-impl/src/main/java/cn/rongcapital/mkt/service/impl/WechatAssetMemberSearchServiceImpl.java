package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.service.WechatAssetMemberSearchService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-28.
 */
@Service
public class WechatAssetMemberSearchServiceImpl implements WechatAssetMemberSearchService{

    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;

    @Autowired
    private WechatMemberDao wechatMemberDao;

    @Override
    public BaseOutput searchWechatAssetMember(String groupIds,String searchField) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        if(groupIds == null) return baseOutput;

        ArrayList<Long> idList = new ArrayList<Long>();
        getIdList(groupIds, idList);

        List<Long> importIdList = wechatAssetGroupDao.selectImportGroupIdsByIds(idList);

        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("nickname", "%" + searchField + "%");
        paramMap.put("importIdList",importIdList);
        List<Map<String,Object>> searchResult = wechatMemberDao.selectSearchInfo(paramMap);
        if(searchResult != null && searchResult.size() > 0){
            for(Map<String,Object> map : searchResult){
                if(map.get("sex") != null){
                    if((Integer)map.get("sex") == 1){
                        map.put("sex","男");
                    }else if((Integer)map.get("sex") == 2){
                        map.put("sex","女");
                    }
                }
                map.put("sex","用户保密");
                if(map.get("head_image_url") == null){
                    map.put("head_image_url","http://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
                }
                baseOutput.getData().add(map);
            }
        }

        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        baseOutput.setTotal(baseOutput.getData().size());
        return baseOutput;
    }

    private void getIdList(String groupIds, ArrayList<Long> idList) {
        if(groupIds.contains(",")){
            for(String id : groupIds.split(",")){
                idList.add(Long.parseLong(id));
            }
        }else{
            idList.add(Long.parseLong(groupIds));
        }
    }

}
