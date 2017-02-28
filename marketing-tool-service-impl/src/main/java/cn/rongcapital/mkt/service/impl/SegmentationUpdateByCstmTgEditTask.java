package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.CustomTagMaterialMapDao;
import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CustomTagMaterialMap;
import cn.rongcapital.mkt.po.SegmentationBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hiro on 17/2/9.
 */

@Service
public class SegmentationUpdateByCstmTgEditTask implements TaskService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SegmentationBodyDao segmentationBodyDao;

    @Autowired
    private CustomTagMaterialMapDao customTagMaterialMapDao;

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

        String[] msgs = message.split(":");
        if(msgs.length != 2){
            logger.info("CustomTagUpdateParent JMS Exception: message is null");
            return;
        }

        String customTagId = msgs[0];
        String customTagName = msgs[1];
        //Todo:2更新segmentation_body表，根据custom_tag_id更新custom_tag_name
        SegmentationBody paramSegmentationBody = new SegmentationBody();
        paramSegmentationBody.setTagId(customTagId);
        paramSegmentationBody.setTagName(customTagName);
        segmentationBodyDao.updateCustomTagNameByCustomTagId(paramSegmentationBody);

        //Todo:3更新custom_tag_material_map表，根据custom_tag_id更新custom_tag_name
        CustomTagMaterialMap paramCustomTagMaterialMap = new CustomTagMaterialMap();
        paramCustomTagMaterialMap.setCustomTagId(customTagId);
        paramCustomTagMaterialMap.setCustomTagName(customTagName);
        customTagMaterialMapDao.updateCustomTagNameByCustomTagId(paramCustomTagMaterialMap);
    }
}
