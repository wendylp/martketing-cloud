package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.dao.OriginalDataShoppingDao;
import cn.rongcapital.mkt.po.DataShopping;
import cn.rongcapital.mkt.po.OriginalDataShopping;
import cn.rongcapital.mkt.service.OriginalDataShoppingScheduleService;
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
public class OriginalDataShoppingScheduleServiceImpl implements OriginalDataShoppingScheduleService {

    public static final int BATCH_NUM = 1000;

    @Autowired
    private OriginalDataShoppingDao originalDataShoppingDao;

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {

        // 1. 取出需要处理的数据
        OriginalDataShopping paramOriginalDataShopping = new OriginalDataShopping();
        paramOriginalDataShopping.setStatus(false);

        // 查询没有被处理过的数据
        List<OriginalDataShopping> originalDataShoppings =
                originalDataShoppingDao.selectList(paramOriginalDataShopping);

        if (originalDataShoppings.isEmpty()) {
            // 根本没有要处理的数据
            return;
        }

        // 2. 分多次处理所有的数据,每次处理BATCH_NUM条数据
        // 一共有多少条要处理的数据
        int totalCount = originalDataShoppings.size();
        int loopCount = (totalCount + BATCH_NUM - 1) / BATCH_NUM;

        for (int i = 0; i < loopCount; i++) {
            // 每次循环中的临时数据表
            List<OriginalDataShopping> tmpOriginalDataShoppings = new ArrayList<>(BATCH_NUM);
            if (i == (loopCount - 1)) {
                tmpOriginalDataShoppings =
                        originalDataShoppings.subList(i * BATCH_NUM, originalDataShoppings.size());
            } else {
                tmpOriginalDataShoppings = originalDataShoppings.subList(i * BATCH_NUM, (i + 1) * BATCH_NUM - 1);
            }

            handleOriginalDataLogin(tmpOriginalDataShoppings);
        }

    }

    // 处理OriginalDataPayment的数据
    private void handleOriginalDataLogin(List<OriginalDataShopping> tmpOriginalDataShoppings) {
        if (tmpOriginalDataShoppings.isEmpty()) {
            return;
        }

        int batchCount = tmpOriginalDataShoppings.size();

        List<DataShopping> dataShoppings = new ArrayList<>(batchCount);
        // 将OriginalDataPayment的数据同步到DataPayment
        for (int i = 0; i < batchCount; i++) {
            DataShopping paramDataShopping = new DataShopping();
            OriginalDataShopping originalDataShopping = tmpOriginalDataShoppings.get(i);
            BeanUtils.copyProperties(tmpOriginalDataShoppings.get(i), paramDataShopping);

            originalDataShopping.setStatus(Boolean.TRUE);
            originalDataShoppingDao.updateById(originalDataShopping);
            dataShoppings.add(paramDataShopping);
        }

        dataShoppingDao.cleanAndUpdateByOriginal(dataShoppings);
    }
}
