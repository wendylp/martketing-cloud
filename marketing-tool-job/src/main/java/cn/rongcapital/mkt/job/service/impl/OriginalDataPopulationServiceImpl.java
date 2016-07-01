package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.job.service.base.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.OriginalDataPopulationDao;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.OriginalDataPopulation;
import cn.rongcapital.mkt.service.OriginalDataPopulationService;

@Service
public class OriginalDataPopulationServiceImpl implements OriginalDataPopulationService,TaskService {

    @Autowired
    private OriginalDataPopulationDao originalDataPopulationDao;

    @Autowired
    private DataPopulationDao dataPopulationDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {

        // 1. 取出需要处理的数据
        OriginalDataPopulation paramOriginalDataPopulation = new OriginalDataPopulation();
        paramOriginalDataPopulation.setStatus(StatusEnum.ACTIVE.getStatusCode());

        int totalCount = originalDataPopulationDao.selectListCount(paramOriginalDataPopulation);
        int totalPages = (totalCount + BATCH_NUM - 1) / BATCH_NUM;
        paramOriginalDataPopulation.setPageSize(BATCH_NUM);
        for (int i = 0; i < totalPages; i++) {
            paramOriginalDataPopulation.setStartIndex(Integer.valueOf(i * BATCH_NUM));
            List<OriginalDataPopulation> originalDataPopulations =
                    originalDataPopulationDao.selectList(paramOriginalDataPopulation);
            if (originalDataPopulations.isEmpty()) {
                continue;
            }

            handleOriginalDataPopulation(originalDataPopulations);

        }
    }

    // 处理OriginalDataPopulation的数据
    private void handleOriginalDataPopulation(
                    List<OriginalDataPopulation> tmpOriginalDataPopulations) {
        if (tmpOriginalDataPopulations.isEmpty()) {
            return;
        }

        int batchCount = tmpOriginalDataPopulations.size();

        List<DataPopulation> dataPopulations = new ArrayList<>(batchCount);
        // 将OriginalDataPopulation的数据同步到DataPopulation
        for (int i = 0; i < batchCount; i++) {
            DataPopulation paramDataPopulation = new DataPopulation();
            OriginalDataPopulation tmpOriginalDataPopulation = tmpOriginalDataPopulations.get(i);
            BeanUtils.copyProperties(tmpOriginalDataPopulation, paramDataPopulation);

            // 因为在一个事务里 , 直接修改OriginalDataPopulation的状态
            tmpOriginalDataPopulation.setStatus(StatusEnum.DELETED.getStatusCode());
            originalDataPopulationDao.updateById(tmpOriginalDataPopulation);
            dataPopulations.add(paramDataPopulation);
        }

        Map<String, List<DataPopulation>> paramMap = new HashMap<>();
        paramMap.put("list", dataPopulations);

        dataPopulationDao.cleanAndUpdateByOriginal(paramMap);
    }

    @Override
    public void task(Integer taskId) {
        cleanData();
    }
}
