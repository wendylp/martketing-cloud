package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.WechatMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-7-7.
 */
@Service
public class WechatMemberScheduleImpl implements TaskService{

    private static final Integer MD_TYPE = 8;
    private static final String WECHAT_PUBFANS_SOURCE = "公众号";
    private static final String WECHAT_PERSONS_SOURCE = "个人号";
    private static final Integer NOT_SYNC_TO_DATA_PARTY = 0;
    private static final Integer SYNCED_TO_DATA_PARTY = 1;
    private final Integer BATCH_SIZE = 500;

    @Autowired
    private WechatMemberDao wechatMemberDao;
    @Autowired
    private DataPartyDao dataPartyDao;

    //1选出没有被同步过的数据，根据selected为0的字段
    //2将数据同步到dataParty表中。
    //3将通不过的数据的selected字段置为1。


    @Override
    public void task(Integer taskId) {
        Integer totalCount = wechatMemberDao.selectedNotSyncCount();
        if(totalCount != null){
            for(int pageNum = 1; pageNum <= (totalCount + BATCH_SIZE -1) / BATCH_SIZE; pageNum ++ ){
                syncWechatMemberByBatchSize();
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private void syncWechatMemberByBatchSize() {
        //1通过PO来选择未同步的WechatMember数据,如果没有未同步的数据则返回，如果有对这些数据进行同步
        WechatMember wechatMember = new WechatMember();
        wechatMember.setSelected(NOT_SYNC_TO_DATA_PARTY.byteValue());
        wechatMember.setPageSize(BATCH_SIZE);
        List<WechatMember> notSyncWechatMemberList = wechatMemberDao.selectList(wechatMember);
        if(notSyncWechatMemberList == null || CollectionUtils.isEmpty(notSyncWechatMemberList)) return;
        if(doSync(notSyncWechatMemberList)){
            List<Long> idList = new ArrayList<Long>();
            for(WechatMember notUpdateStatusWechatMember: notSyncWechatMemberList){
                idList.add(notUpdateStatusWechatMember.getId());
            }
            updateSyncWechatMemeberListStatus(idList);
        }
    }

    private boolean doSync(List<WechatMember> notSyncWechatMemberList) {
        //将同步过程改为使用PO操作数据库而不是使用Map操作数据库
        List<DataParty> readyToSyncDataPartyList = new LinkedList<DataParty>();
        for(WechatMember wechatMember : notSyncWechatMemberList){
            DataParty dataParty = new DataParty();
            dataParty.setProvice(wechatMember.getProvince());
            dataParty.setName(wechatMember.getWxName());
            dataParty.setMappingKeyid(wechatMember.getId() + "");
            dataParty.setGender(wechatMember.getSex().byteValue());
            if(wechatMember.getPubId() != null && !wechatMember.getPubId().isEmpty()){
                dataParty.setSource(WECHAT_PUBFANS_SOURCE);
            }else{
                dataParty.setSource(WECHAT_PERSONS_SOURCE);
            }
            dataParty.setMdType(MD_TYPE);
            readyToSyncDataPartyList.add(dataParty);
        }

        Integer effectRows = 0;
        for(DataParty dataParty : readyToSyncDataPartyList){
            dataPartyDao.insert(dataParty);
            effectRows ++;
        }
        if(effectRows == readyToSyncDataPartyList.size()){
            return true;
        }
        return false;
    }

    private void updateSyncWechatMemeberListStatus(List<Long> notSyncWechatMemberList) {
        wechatMemberDao.updateSyncDataMark(notSyncWechatMemberList);
    }

}
