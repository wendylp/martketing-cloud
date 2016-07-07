package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.service.base.OriginalDataScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-7-7.
 */
@Service
public class WechatMemberScheduleImpl implements TaskService{

    private static final Integer MD_TYPE = 8;
    private final Integer BATCH_SIZE = 500;

    @Autowired
    private WechatMemberDao wechatMemberDao;
    @Autowired
    private DataPartyDao dataPartyDao;

    //Todo: 1选出没有被同步过的数据，根据selected为0的字段
    //Todo: 2将数据同步到dataParty表中。
    //Todo: 3将通不过的数据的selected字段置为1。


    @Override
    public void task(Integer taskId) {
        Integer totalCount = wechatMemberDao.selectedNotSyncCount();
        if(totalCount != null){
            for(int pageNum = 1; pageNum < (totalCount + BATCH_SIZE -1) / BATCH_SIZE; pageNum ++ ){
                syncWechatMemberByBatchSize();
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private void syncWechatMemberByBatchSize() {
        List<Map<String,Object>> notSyncWechatMemberList = wechatMemberDao.selectNotSyncWechatMemberList();
        if(doSync(notSyncWechatMemberList)){
            List<Long> idList = new ArrayList<Long>();
            for(Map<String,Object> map : notSyncWechatMemberList){
                idList.add((long)map.get("mapping_key_id"));
            }
            updateSyncWechatMemeberListStatus(idList);
        }
    }

    private boolean doSync(List<Map<String, Object>> notSyncWechatMemberList) {
        for(Map<String,Object> map : notSyncWechatMemberList){
            map.put("provice",map.remove("province"));
            map.put("name",map.remove("wx_name"));
            map.put("mapping_key_id",map.remove("id"));
            map.put("gender",map.remove("sex"));
            map.put("md_type", MD_TYPE);
        }

        Integer effectRows = dataPartyDao.batchInsertWechatDatas(notSyncWechatMemberList);
        if(effectRows > 0){
            return true;
        }
        return false;
    }

    private void updateSyncWechatMemeberListStatus(List<Long> notSyncWechatMemberList) {
        wechatMemberDao.updateSyncDataMark(notSyncWechatMemberList);
    }

}
