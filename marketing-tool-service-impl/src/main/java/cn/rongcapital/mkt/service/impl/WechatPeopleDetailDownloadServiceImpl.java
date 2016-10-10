package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.po.WechatAssetGroup;
import cn.rongcapital.mkt.service.WechatPeopleDetailDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.DownloadFileName;
import cn.rongcapital.mkt.vo.out.WechatPeopleDetailDownloadOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        ArrayList<Long> groupIds = new ArrayList<Long>();
        File file = null;
        //1.将group_ids进行拆分，获取组id
        if(group_ids != null){
            if(group_ids.contains(",")){
                String[] ids = group_ids.split(",");
                for (String id : ids){
                    groupIds.add(Long.parseLong(id));
                }
            }else{
                groupIds.add(Long.parseLong(group_ids));
            }
            //通过asset_group_id查询这些group的总人数
            Long totalGroupCount = wechatAssetGroupDao.sumGroupMemberCount(groupIds);
            //通过组id查询import的group_id
            List<WechatAssetGroup> wechatAssetGroups = wechatAssetGroupDao.selectImportGroupsByIds(groupIds);
            //3.根据import_id选取出人。
            List<Map<String,Object>> peopleDetails = wechatMemberDao.selectPeopleDetails(wechatAssetGroups);
            file = FileUtil.generateDownloadFile(peopleDetails,"wxDetails");
            baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
            baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
            WechatPeopleDetailDownloadOut wechatPeopleDetailDownloadOut = new WechatPeopleDetailDownloadOut();
            wechatPeopleDetailDownloadOut.setDownloadFileName(file.getName());
            wechatPeopleDetailDownloadOut.setTotalCount(totalGroupCount);
            baseOutput.getData().add(wechatPeopleDetailDownloadOut);
        }
        return baseOutput;
    }
}
