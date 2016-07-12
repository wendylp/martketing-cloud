package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.WechatAsset;
import cn.rongcapital.mkt.po.WechatRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-26.
 * Todo：1同步小组数据
 * Todo: 2同步资产数据
 */
@Service
public class WechatToWechatAssetSyncServiceImpl implements TaskService{

    @Autowired
    private WechatGroupDao wechatGroupDao;
    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;
    @Autowired
    private WechatMemberDao wechatMemberDao;
    @Autowired
    private WechatRegisterDao wechatRegisterDao;
    @Autowired
    private WechatAssetDao wechatAssetDao;

    @Override
    public void task(Integer taskId) {
        //Todo:1.遍历wechat_asset_group表，获取importId的List
        List<Long> alreadyImportedIdList = wechatAssetGroupDao.selectImportGroupIds();
        //Todo:2.根据1获取的listId，在wechat_group表中获取不在importId中的IdList
        List<Map<String,Object>> newGroupList = null;
        if(alreadyImportedIdList != null && alreadyImportedIdList.size() > 0){
            newGroupList = wechatGroupDao.selectNewGroupList(alreadyImportedIdList);
        }else{
            newGroupList = wechatGroupDao.selectFirstImportGroupList();
        }
        //Todo:3.根据2获取idList，查询wechat_group中的相关数据同步到wechat_asset_group表中
        if(newGroupList != null && newGroupList.size() > 0){
            wechatAssetGroupDao.insertNewGroupList(newGroupList);
        }

        //Todo:4.即时跟新wechat_asset_group表中的member数
        alreadyImportedIdList = wechatAssetGroupDao.selectImportGroupIds();
        for(Long id : alreadyImportedIdList){
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("wx_group_id", id);
            Integer count = wechatMemberDao.selectGroupMemeberCount(paramMap);
            paramMap.put("import_group_id",paramMap.remove("wx_group_id"));
            paramMap.put("members",count);
            wechatAssetGroupDao.updateGroupCountById(paramMap);
        }
        //Todo:group基本同步完毕

        //Todo:获取wechat_asset表中的wx_acct。
        List<String> alreadyImportedWxAcctList = wechatAssetDao.selectWxAssetList();
        //Todo:根据acct获取wx_register表中新注册的公众号或者个人号
        List<Map<String,Object>> newRegisterWxAssetList = null;
        if(alreadyImportedWxAcctList != null && alreadyImportedWxAcctList.size() > 0){
            newRegisterWxAssetList = wechatRegisterDao.selectNewWxAssetList(alreadyImportedWxAcctList);
        }else{
            newRegisterWxAssetList = wechatRegisterDao.selectNewWxAssetListWhenFirstImported();
        }
        //Todo:将选择出来的相应数据插入wechat_asset表中
        if(newRegisterWxAssetList != null && newRegisterWxAssetList.size() > 0){
            wechatAssetDao.insertNewRegisterAsset(newRegisterWxAssetList);
        }
        //Todo:更新wechat_asset表中的total数据和groupIds数据
        alreadyImportedWxAcctList = wechatAssetDao.selectWxAssetList();
        for(String wxAcct : alreadyImportedWxAcctList){
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("wx_acct",wxAcct);
            List<Long> groupIdList = wechatAssetGroupDao.selectGroupIdsByWxAcct(paramMap);
            String groupIds = generateGroupIds(groupIdList);
            if(groupIds != null){
                Integer totalCount = wechatAssetGroupDao.selectMemberCountByWxAcct(paramMap);
                paramMap.put("group_ids",groupIds);
                paramMap.put("total_count", totalCount);
                wechatAssetDao.updateGroupIdsAndTotalCount(paramMap);
            }
        }

        //Todo:更新微信资产的状态信息，未来做成PO与上面那个for循环进行合并
        for(String wxAcct : alreadyImportedWxAcctList){
            WechatRegister wechatRegister = new WechatRegister();
            wechatRegister.setWxAcct(wxAcct);
            wechatRegister.setStatus(new Integer(0).byteValue());  //0代表有效
            List<WechatRegister> wechatRegisterList = wechatRegisterDao.selectList(wechatRegister);
            if(!CollectionUtils.isEmpty(wechatRegisterList)){
                wechatRegister = wechatRegisterList.get(0);
                WechatAsset wechatAsset = new WechatAsset();
                wechatAsset.setAssetName(wechatRegister.getName());
                wechatAsset.setAssetType(wechatRegister.getType());
                wechatAsset.setImgfileUrl(wechatRegister.getHeaderImage());
                wechatAsset.setWxAcct(wechatRegister.getWxAcct());
                wechatAsset.setNickname(wechatRegister.getNickname());
                wechatAssetDao.updateByWxacct(wechatAsset);
            }
        }
    }

    private String generateGroupIds(List<Long> groupIdList) {
        StringBuffer sf = new StringBuffer();
        if(groupIdList != null && groupIdList.size() > 0){
            for(Long id : groupIdList){
                sf.append( id + ",");
            }
            sf.deleteCharAt(sf.length()-1);
            return sf.toString();
        }
        return null;
    }
}
