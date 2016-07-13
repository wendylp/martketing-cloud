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
    private static final int LOOP_TIME = 1000;

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

    // 获取
    private List<BasEventOut> getBasData() {
        // 1.获取dataParty中的数据数量,这也是最终导出的数据数量
        DataParty paramDataPary = new DataParty();
        // 导出未被删除的数据
        paramDataPary.setStatus((byte) 0);
        int totalCount = dataPartyDao.selectListCount(paramDataPary);
        List<BasEventOut> basEventOuts = new ArrayList<>(totalCount);

        return basEventOuts;
    }


}
