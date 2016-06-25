package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.service.WechatPeopleDetailDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-24.
 */
@Service
public class WechatPeopleDetailDownloadServiceImpl implements WechatPeopleDetailDownloadService {

    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;
    @Autowired
    private WechatMemberDao wechatMemberDao;

    @Override
    public Object downloadWechatPeopleDetail(String group_ids) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        File file = null;
        //1.将group_ids进行拆分，获取组id
        if(group_ids != null){
            String[] ids = group_ids.split(",");
            ArrayList<Integer> groupIds = new ArrayList<Integer>();
            for (String id : ids){
                groupIds.add(Integer.parseInt(id));
            }
            //通过组id查询import的group_id
            List<Long> importGroupIds = wechatAssetGroupDao.selectImportGroupIdsByIds(groupIds);
            //3.根据import_id选取出人。
            List<Map<String,Object>> peopleDetails = wechatMemberDao.selectPeopleDetails(importGroupIds);
            file = FileUtil.generateDownloadFile(peopleDetails,"wxDetails");
        }
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\""+file.getName() +"\"");
        return response.build();
    }
}
