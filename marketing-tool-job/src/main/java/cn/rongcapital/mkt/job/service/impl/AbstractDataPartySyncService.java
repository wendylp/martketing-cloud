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

    protected int BATCH_SIZE = 500;

    protected static Integer MD_TYPE = Integer.valueOf(0);

    @Autowired
    private DataPartyDao dataPartyDao;

    @Override
    public void doSync() {
        int totalCount = this.queryTotalCount();
        if (totalCount < 1) {
            return;
        }

        int totalPages = (totalCount + BATCH_SIZE - 1) / BATCH_SIZE;
        for (int i = 0; i < totalPages; i++) {
            DataPartySyncVO<T> dataPartySyncVO = this.querySyncData(
                    Integer.valueOf(i * BATCH_SIZE), Integer.valueOf(BATCH_SIZE));
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
}
