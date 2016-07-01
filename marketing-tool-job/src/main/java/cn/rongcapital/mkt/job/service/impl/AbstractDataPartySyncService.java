package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.job.service.DataPartySyncService;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by ethan on 16/6/30.
 */
public abstract class AbstractDataPartySyncService<T> implements DataPartySyncService<T> {

    @Autowired
    private DataPartyDao dataPartyDao;

    @Override
    public void doSync() {
        DataPartySyncVO<T> dataPartySyncVO = this.querySyncData();
        if (dataPartySyncVO == null) {
            return;
        }
        List<DataParty> dataPartyList = dataPartySyncVO.getDataPartyList();
        if (CollectionUtils.isEmpty(dataPartyList)) {
            return;
        }
        dataPartyDao.batchInsert(dataPartyList);
        this.doSyncAfter(dataPartySyncVO);
    }
}
