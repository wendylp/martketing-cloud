package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.WechatMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yunfeng on 2016-8-9.
 */

@Service
public class WechatMemberScheduleToPopulationServiceImpl implements TaskService{
    private static final Integer MD_TYPE = 8;
    private static final String WECHAT_PUBFANS_SOURCE = "公众号";
    private static final String WECHAT_PERSONS_SOURCE = "个人号";
    private static final Integer NOT_SYNC_TO_DATA_PARTY = 0;
    private static final Integer SYNCED_TO_DATA_PARTY = 1;
    private final Integer BATCH_SIZE = 500;

    @Autowired
    private WechatMemberDao wechatMemberDao;
    @Autowired
    private DataPopulationDao dataPopulationDao;

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

    //在这里需要做两步，data_party里有数据的弄出key_id反插入member，否则
    private boolean doSync(List<WechatMember> notSyncWechatMemberList) {
        //将同步过程改为使用PO操作数据库而不是使用Map操作数据库
        for(WechatMember wechatMember : notSyncWechatMemberList){
            DataPopulation dataPopulation = new DataPopulation();
            if(wechatMember.getPubId() != null && !wechatMember.getPubId().isEmpty()){
                if (updateWechatMemberKeyidByBitmap(wechatMember, dataPopulation)) continue;
                dataPopulation.setWxmpId(wechatMember.getPubId());
                dataPopulation.setWxCode(wechatMember.getWxCode());
                dataPopulation.setBitmap(wechatMember.getBitmap());
                dataPopulation.setProvice(wechatMember.getProvince());
                dataPopulation.setName(wechatMember.getWxName());
                dataPopulation.setGender(wechatMember.getSex().byteValue());
                dataPopulation.setNickname(wechatMember.getNickname());
                dataPopulation.setCity(wechatMember.getCity());
                dataPopulation.setHeadImgUrl(wechatMember.getHeadImageUrl());
                dataPopulation.setCitizenship(wechatMember.getCountry());
                dataPopulation.setSource(WECHAT_PUBFANS_SOURCE);
//                dataPopulation.setSubscribeTime(DateUtil.getDateFromString(wechatMember.getSubscribeTime()));
                dataPopulation.setRemark(wechatMember.getRemark());
                dataPopulationDao.insert(dataPopulation);
                wechatMember.setKeyid(dataPopulation.getId());
                updateKeyIdInWechatMember(wechatMember);
            }
        }

        return true;
    }

    private boolean updateWechatMemberKeyidByBitmap(WechatMember wechatMember, DataPopulation dataPopulation) {
        dataPopulation.setWxmpId(wechatMember.getPubId());
        dataPopulation.setWxCode(wechatMember.getWxCode());
        if(isAlreadySyncedToDataPopulation(dataPopulation)){
            Integer keyId = retrieveKeyId(dataPopulation);
            if(keyId != null && keyId > 0){
                wechatMember.setKeyid(keyId);
                updateKeyIdInWechatMember(wechatMember);
                return true;
            }
        }
        return false;
    }

    private void updateKeyIdInWechatMember(WechatMember wechatMember) {
        wechatMemberDao.updateById(wechatMember);
    }

    private Integer retrieveKeyId(DataPopulation dataPopulation) {
        List<DataPopulation> dataPopulationList = dataPopulationDao.selectList(dataPopulation);
        if(!CollectionUtils.isEmpty(dataPopulationList)){
            DataPopulation idDataPopulation = dataPopulationList.get(0);
            return idDataPopulation.getId();
        }
        return null;
    }

    private boolean isAlreadySyncedToDataPopulation(DataPopulation dataPopulation) {
        Integer count = dataPopulationDao.selectListCount(dataPopulation);
        if(count != null && count > 0) return true;
        return false;
    }

    private void updateSyncWechatMemeberListStatus(List<Long> notSyncWechatMemberList) {
        wechatMemberDao.updateSyncDataMark(notSyncWechatMemberList);
    }
}
