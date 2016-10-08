package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.dao.OriginalDataShoppingDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataShopping;
import cn.rongcapital.mkt.po.OriginalDataShopping;
import cn.rongcapital.mkt.service.OriginalDataShoppingScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bianyulong on 16/6/23.
 */
@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class OriginalDataShoppingScheduleServiceImpl implements OriginalDataShoppingScheduleService, TaskService {

    @Autowired
    private OriginalDataShoppingDao originalDataShoppingDao;

    @Autowired
    private DataShoppingDao dataShoppingDao;
    
	@Autowired
	Environment env;	
	
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {

        int BATCH_NUM = Integer.valueOf(env.getProperty("orginal.to.data.batch.num"));
        
        // 1. 取出需要处理的数据
        OriginalDataShopping paramOriginalDataShopping = new OriginalDataShopping();
        paramOriginalDataShopping.setStatus(StatusEnum.ACTIVE.getStatusCode());

        int totalCount = originalDataShoppingDao.selectListCount(paramOriginalDataShopping);
        int totalPages = (totalCount + BATCH_NUM - 1) / BATCH_NUM;
        paramOriginalDataShopping.setPageSize(BATCH_NUM);
        for (int i = 0; i < totalPages; i++) {
            paramOriginalDataShopping.setStartIndex(Integer.valueOf(i * BATCH_NUM));
            List<OriginalDataShopping> originalDataShoppings =
                    originalDataShoppingDao.selectList(paramOriginalDataShopping);

            if (originalDataShoppings.isEmpty()) {
                continue;
            }

            handleOriginalDataLogin(originalDataShoppings);
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

            originalDataShopping.setStatus(StatusEnum.PROCESSED.getStatusCode());
            originalDataShoppingDao.updateById(originalDataShopping);
            dataShoppings.add(paramDataShopping);
        }

        dataShoppingDao.cleanAndUpdateByOriginal(dataShoppings);
    }

    @Override
    public void task(Integer taskId) {
        cleanData();
    }
}
