package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.OriginalDataArchPointDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataArchPoint;
import cn.rongcapital.mkt.po.OriginalDataArchPoint;
import cn.rongcapital.mkt.service.OriginalDataArchPointScheduleService;

@Service
public class OriginalDataArchPointScheduleServiceImpl implements OriginalDataArchPointScheduleService, TaskService {

    @Autowired
    private OriginalDataArchPointDao originalDataArchPointDao;

    @Autowired
    private DataArchPointDao dataArchPointDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {

        // 1. 取出需要处理的数据
        OriginalDataArchPoint paramOriginalDataArchPoint = new OriginalDataArchPoint();
        paramOriginalDataArchPoint.setStatus(StatusEnum.ACTIVE.getStatusCode());
        // 查询没有被处理过的数据 (未删除的)
        List<OriginalDataArchPoint> originalDataArchPoints =
                        originalDataArchPointDao.selectList(paramOriginalDataArchPoint);

        if (originalDataArchPoints.isEmpty()) {
            // 根本没有要处理的数据
            return;
        }

        // 2. 分多次处理所有的数据,每次处理BATCH_NUM条数据

        // 一共有多少条要处理的数据
        int totalCount = originalDataArchPoints.size();

        // 需要多少次循环去处理,相当于一个房间住M个人,N个人需要多少房间的问题.不解释
        int loopCount = (totalCount + BATCH_NUM - 1) / BATCH_NUM;

        for (int i = 0; i < loopCount; i++) {
            // 每次循环中的临时数据表
            List<OriginalDataArchPoint> tmpOriginalDataArchPoints = new ArrayList<>(BATCH_NUM);
            if (i == loopCount - 1) {
                tmpOriginalDataArchPoints =
                                originalDataArchPoints.subList(i * BATCH_NUM, originalDataArchPoints.size());
            } else {
                tmpOriginalDataArchPoints = originalDataArchPoints.subList(i * BATCH_NUM, (i + 1) * BATCH_NUM - 1);
            }

            handleOriginalDataArchPoint(tmpOriginalDataArchPoints);

        }
    }

    // 处理OriginalDataArchPoint的数据
    private void handleOriginalDataArchPoint(List<OriginalDataArchPoint> tmpOriginalDataArchPoints) {
        if (tmpOriginalDataArchPoints.isEmpty()) {
            return;
        }

        int batchCount = tmpOriginalDataArchPoints.size();

        List<DataArchPoint> dataArchPoints = new ArrayList<>(batchCount);
        // 将OriginalDataArchPoint的数据同步到DataArchPoint
        for (int i = 0; i < batchCount; i++) {
            DataArchPoint paramDataArchPoint = new DataArchPoint();
            OriginalDataArchPoint tmpOriginalDataArchPoint = tmpOriginalDataArchPoints.get(i);
            BeanUtils.copyProperties(tmpOriginalDataArchPoint, paramDataArchPoint);

            // 因为在一个事务里 , 直接修改OriginalDataArchPoint的状态
            tmpOriginalDataArchPoint.setStatus(StatusEnum.DELETED.getStatusCode());
            originalDataArchPointDao.updateById(tmpOriginalDataArchPoint);
            dataArchPoints.add(paramDataArchPoint);
        }

        Map<String, List<DataArchPoint>> paramMap = new HashMap<>();
        paramMap.put("list", dataArchPoints);

        dataArchPointDao.cleanAndUpdateByOriginal(paramMap);
    }

    @Override
    public void task(Integer taskId) {
        cleanData();
    }

}
