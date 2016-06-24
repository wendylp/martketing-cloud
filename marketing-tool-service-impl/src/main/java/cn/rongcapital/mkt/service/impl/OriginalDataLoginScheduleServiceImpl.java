package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.dao.OriginalDataLoginDao;
import cn.rongcapital.mkt.po.DataLogin;
import cn.rongcapital.mkt.po.OriginalDataLogin;
import cn.rongcapital.mkt.service.OriginalDataLoginScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bianyulong on 16/6/22.
 */
@Service
public class OriginalDataLoginScheduleServiceImpl implements OriginalDataLoginScheduleService {

    public static final int BATCH_NUM = 1000;

    @Autowired
    private OriginalDataLoginDao originalDataLoginDao;

    @Autowired
    private DataLoginDao dataLoginDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {

        // 1. 取出需要处理的数据
        OriginalDataLogin paramOriginalDataLogin = new OriginalDataLogin();
        paramOriginalDataLogin.setStatus(false);

        // 查询没有被处理过的数据
        List<OriginalDataLogin> originalDataLogins =
                originalDataLoginDao.selectList(paramOriginalDataLogin);

        if (originalDataLogins.isEmpty()) {
            // 根本没有要处理的数据
            return;
        }

        // 2. 分多次处理所有的数据,每次处理BATCH_NUM条数据
        // 一共有多少条要处理的数据
        int totalCount = originalDataLogins.size();
        int loopCount = (totalCount + BATCH_NUM - 1) / BATCH_NUM;

        for (int i = 0; i < loopCount; i++) {
            // 每次循环中的临时数据表
            List<OriginalDataLogin> tmpOriginalDataLogins = new ArrayList<>(BATCH_NUM);
            if (i == (loopCount - 1)) {
                tmpOriginalDataLogins =
                        originalDataLogins.subList(i * BATCH_NUM, originalDataLogins.size());
            } else {
                tmpOriginalDataLogins = originalDataLogins.subList(i * BATCH_NUM, (i + 1) * BATCH_NUM - 1);
            }

            handleOriginalDataLogin(tmpOriginalDataLogins);
        }

    }

    // 处理OriginalDataLogin的数据
    private void handleOriginalDataLogin(List<OriginalDataLogin> tmpOriginalDataLogins) {
        if (tmpOriginalDataLogins.isEmpty()) {
            return;
        }

        int batchCount = tmpOriginalDataLogins.size();

        List<DataLogin> dataLogins = new ArrayList<>(batchCount);
        // 将OriginalDataLogin的数据同步到DataLogin
        for (int i = 0; i < batchCount; i++) {
            DataLogin paramDataLogin = new DataLogin();
            BeanUtils.copyProperties(tmpOriginalDataLogins.get(i), paramDataLogin);
            dataLogins.add(paramDataLogin);
        }

        dataLoginDao.cleanAndUpdateByOriginal(dataLogins);
    }

}
