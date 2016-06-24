package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.dao.OriginalDataPaymentDao;
import cn.rongcapital.mkt.po.DataPayment;
import cn.rongcapital.mkt.po.OriginalDataPayment;
import cn.rongcapital.mkt.service.OriginalDataPaymentScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bianyulong on 16/6/23.
 */
@Service
public class OriginalDataPaymentScheduleServiceImpl implements OriginalDataPaymentScheduleService {

    public static final int BATCH_NUM = 1000;

    @Autowired
    private OriginalDataPaymentDao originalDataPaymentDao;

    @Autowired
    private DataPaymentDao dataPaymentDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {

        // 1. 取出需要处理的数据
        OriginalDataPayment paramOriginalDataPayment = new OriginalDataPayment();
        paramOriginalDataPayment.setStatus(false);

        // 查询没有被处理过的数据
        List<OriginalDataPayment> originalDataPayments =
                originalDataPaymentDao.selectList(paramOriginalDataPayment);

        if (originalDataPayments.isEmpty()) {
            // 根本没有要处理的数据
            return;
        }

        // 2. 分多次处理所有的数据,每次处理BATCH_NUM条数据
        // 一共有多少条要处理的数据
        int totalCount = originalDataPayments.size();
        int loopCount = (totalCount + BATCH_NUM - 1) / BATCH_NUM;

        for (int i = 0; i < loopCount; i++) {
            // 每次循环中的临时数据表
            List<OriginalDataPayment> tmpOriginalDataPayments = new ArrayList<>(BATCH_NUM);
            if (i == (loopCount - 1)) {
                tmpOriginalDataPayments =
                        originalDataPayments.subList(i * BATCH_NUM, originalDataPayments.size());
            } else {
                tmpOriginalDataPayments = originalDataPayments.subList(i * BATCH_NUM, (i + 1) * BATCH_NUM - 1);
            }

            handleOriginalDataLogin(tmpOriginalDataPayments);
        }

    }

    // 处理OriginalDataPayment的数据
    private void handleOriginalDataLogin(List<OriginalDataPayment> tmpOriginalDataPayments) {
        if (tmpOriginalDataPayments.isEmpty()) {
            return;
        }

        int batchCount = tmpOriginalDataPayments.size();

        List<DataPayment> dataLogins = new ArrayList<>(batchCount);
        // 将OriginalDataPayment的数据同步到DataPayment
        for (int i = 0; i < batchCount; i++) {
            DataPayment paramDataPayment = new DataPayment();
            BeanUtils.copyProperties(tmpOriginalDataPayments.get(i), paramDataPayment);
            dataLogins.add(paramDataPayment);
        }

        dataPaymentDao.cleanAndUpdateByOriginal(dataLogins);
    }

}
