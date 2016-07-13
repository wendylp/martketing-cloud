package cn.rongcapital.mkt.job.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.base.BasEventExportService;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.vo.out.BasEventOut;

@Service
public class BasEventExportServiceImpl implements BasEventExportService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 每次获取的数据量,不能一次把数据库的数据都导出来
    private static final int LOOP_COUNT = 1000;

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private DataPopulationDao dataPopulationDao;

    @Autowired
    private DataCustomerTagsDao dataCustomerTagsDao;

    @Autowired
    private DataArchPointDao dataArchPointDao;

    @Autowired
    private DataMemberDao dataMemberDao;

    @Autowired
    private DataLoginDao dataLoginDao;

    @Autowired
    private DataPaymentDao dataPaymentDao;

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Override
    public File exportData() {
        return null;
    }

    // 获取所有整理为BAS Event格式的数据
    private List<BasEventOut> getBasData() {

        // 1.获取dataParty中的数据数量,这也是最终导出的数据数量
        DataParty paramDataPary = new DataParty();
        // 导出未被删除的数据
        paramDataPary.setStatus((byte) 0);
        int totalCount = dataPartyDao.selectListCount(paramDataPary);
        List<DataParty> dataParties = dataPartyDao.selectList(paramDataPary);
        List<BasEventOut> basEventOuts = new ArrayList<>(totalCount);

        logger.info("BAS Event预计要导出的数据量为 : {}条", totalCount);

        // 循环次数为"一个房间能住N个人,M个人需要多少房间"的问题.不解释
        int loopTimes = (totalCount + LOOP_COUNT - 1) / LOOP_COUNT;
        for (int i = 0; i < loopTimes; i++) {
            int startIndex = i * LOOP_COUNT;
            int endIndex = (i + 1) * LOOP_COUNT;
            // 如果是最后一次循环,endIndex就是最大值了.
            if (i == loopTimes - 1) {
                endIndex = totalCount;
            }

            basEventOuts.addAll(fillBasEventVo(dataParties.subList(startIndex, endIndex)));
        }

        return basEventOuts;
    }

    private List<BasEventOut> fillBasEventVo(List<DataParty> dataParties) {
        int dataPartyCount = dataParties.size();
        List<BasEventOut> basEventOuts = new ArrayList<>(dataPartyCount);
        // 如果没有数据,则返回空对象,坚决不返回null
        if (dataPartyCount != 0) {

            for (int i = 0; i < dataPartyCount; i++) {

            }

        }

        return basEventOuts;
    }


}
