package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.SegmentationBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hiro on 17/2/9.
 */

@Service
public class AfterDeleteCustomTagTask implements TaskService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SegmentationBodyDao segmentationBodyDao;

    @Override
    public void task(Integer taskId) {

    }

    @Override
    public void task(String message) {
        //Todo:1拆分字符串，获取相应的信息，按顺序依次为
        if(message == null){
            logger.info("CustomTagUpdateParent JMS Exception: message is null");
            return;
        }

        String customTagId = message;

        //Todo:2更新segmentation_body表，根据CustomTagId更新表状态。
        SegmentationBody paramSegmentationBody = new SegmentationBody();
        paramSegmentationBody.setTagId(customTagId);
        segmentationBodyDao.updateCustomTagStatusByCutsomTagId(paramSegmentationBody);
    }
}
