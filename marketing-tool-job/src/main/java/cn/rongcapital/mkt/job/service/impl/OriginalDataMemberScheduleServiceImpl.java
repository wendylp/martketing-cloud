package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.OriginalDataMemberDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataMember;
import cn.rongcapital.mkt.po.OriginalDataMember;
import cn.rongcapital.mkt.service.OriginalDataMemberScheduleService;

@Service
public class OriginalDataMemberScheduleServiceImpl implements OriginalDataMemberScheduleService , TaskService{

    @Autowired
    private OriginalDataMemberDao originalDataMemberDao;

    @Autowired
    private DataMemberDao dataMemberDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {
        // 1. 取出需要处理的数据
        OriginalDataMember paramOriginalDataMember = new OriginalDataMember();
        paramOriginalDataMember.setStatus(StatusEnum.ACTIVE.getStatusCode());

        List<OriginalDataMember> originalDataMembers = originalDataMemberDao.selectList(paramOriginalDataMember);

        if (CollectionUtils.isEmpty(originalDataMembers)) {
            // 根本没有要处理的数据
            return;
        }

        // 2. 分多次处理所有的数据,每次处理BATCH_NUM条数据

        // 一共有多少条要处理的数据
        int totalCount = originalDataMembers.size();

        // 需要多少次循环去处理,相当于一个房间住M个人,N个人需要多少房间的问题.不解释
        int loopCount = (totalCount + BATCH_NUM - 1) / BATCH_NUM;

        for (int i = 0; i < loopCount; i++) {
            // 每次循环中的临时数据表
            List<OriginalDataMember> tmpOriginalDataMembers = new ArrayList<>(BATCH_NUM);
            if (i == loopCount - 1) {
                tmpOriginalDataMembers = originalDataMembers.subList(i * BATCH_NUM, originalDataMembers.size());
            } else {
                tmpOriginalDataMembers = originalDataMembers.subList(i * BATCH_NUM, (i + 1) * BATCH_NUM - 1);
            }

            handleOriginalDataMember(tmpOriginalDataMembers);

        }
    }

    // 处理OriginalDataMember的数据
    private void handleOriginalDataMember(List<OriginalDataMember> tmpOriginalDataMembers) {
        if (tmpOriginalDataMembers.isEmpty()) {
            return;
        }

        int batchCount = tmpOriginalDataMembers.size();

        List<DataMember> dataMembers = new ArrayList<>(batchCount);
        // 将OriginalDataMember的数据同步到DataMember
        for (int i = 0; i < batchCount; i++) {
            DataMember paramDataMember = new DataMember();
            OriginalDataMember tmpOriginalDataMember = tmpOriginalDataMembers.get(i);
            BeanUtils.copyProperties(tmpOriginalDataMember, paramDataMember);
            // 因为在一个事务里 , 直接修改OriginalDataMember的状态
            tmpOriginalDataMember.setStatus(StatusEnum.DELETED.getStatusCode());
            originalDataMemberDao.updateById(tmpOriginalDataMember);
            dataMembers.add(paramDataMember);
        }

        Map<String, List<DataMember>> paramMap = new HashMap<>();
        paramMap.put("list", dataMembers);

        dataMemberDao.cleanAndUpdateByOriginal(paramMap);
    }
    
    @Override
    public void task(Integer taskId) {
        cleanData();
    }

}
