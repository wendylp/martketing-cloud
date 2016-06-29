package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        paramOriginalDataPopulation.setStatus(false);
        // 查询没有被处理过的数据 (未删除的)
        List<OriginalDataPopulation> originalDataPopulations =
                        originalDataPopulationDao.selectList(paramOriginalDataPopulation);

        if (originalDataPopulations.isEmpty()) {
            // 根本没有要处理的数据
            return;
        }

        // 2. 分多次处理所有的数据,每次处理BATCH_NUM条数据

        // 一共有多少条要处理的数据
        int totalCount = originalDataPopulations.size();

        // 需要多少次循环去处理,相当于一个房间住M个人,N个人需要多少房间的问题.不解释
        int loopCount = (totalCount + BATCH_NUM - 1) / BATCH_NUM;

        for (int i = 0; i < loopCount; i++) {
            // 每次循环中的临时数据表
            List<OriginalDataPopulation> tmpOriginalDataPopulations = new ArrayList<>(BATCH_NUM);
            if (i == loopCount - 1) {
                tmpOriginalDataPopulations = originalDataPopulations.subList(i * BATCH_NUM,
                                originalDataPopulations.size());
            } else {
                tmpOriginalDataPopulations = originalDataPopulations.subList(i * BATCH_NUM,
                                (i + 1) * BATCH_NUM - 1);
            }

            handleOriginalDataPopulation(tmpOriginalDataPopulations);

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
            tmpOriginalDataPopulation.setStatus(Boolean.TRUE);
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
