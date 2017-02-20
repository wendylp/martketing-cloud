package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomTagToDeleteService;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.vo.ActiveMqMessageVO;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagToDeleteIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.jms.JMSException;

/**
 * Created by hiro on 17/2/8.
 */
@Service
public class CustomTagToDeleteServiceImpl implements CustomTagToDeleteService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Autowired
    private MongoCustomTagDao mongoCustomTagDao;

    @Autowired
    private MQTopicService mqTopicService;

    private static final String MQ_CUSTOM_TAG_DELETE_TOPIC = "customTagDeleteTopic";
    private final String AFTER_DELETE_CUSTOM_TAG_TASK = "afterDeleteCustomTagTask";

    @Override
    public BaseOutput deleteCustomTag(CustomTagToDeleteIn customTagToDeleteIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        //1在该自定义标签所属的分类中移除这个自定义标签
        CustomTagCategory paramCustomTagCategory = new CustomTagCategory();
        paramCustomTagCategory.setCustomTagCategoryId(customTagToDeleteIn.getCustomTagCategoryId());
        CustomTagCategory upadateCustomTagCategory = mongoCustomTagCategoryDao.findOne(paramCustomTagCategory);
        if(upadateCustomTagCategory == null || CollectionUtils.isEmpty(upadateCustomTagCategory.getChildrenCustomTagList())){
            baseOutput.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.PARAMETER_ERROR.getMsg());
            return baseOutput;
        }
        upadateCustomTagCategory.getChildrenCustomTagList().remove(customTagToDeleteIn.getCustomTagId());
        mongoCustomTagCategoryDao.updateCustomTagCategoryChildrenListById(customTagToDeleteIn.getCustomTagCategoryId(),upadateCustomTagCategory);

        //2逻辑删除这个自定义标签
        mongoCustomTagDao.logicalDeleteCustomTagByCustomTagId(customTagToDeleteIn.getCustomTagId());

        //3发送自己被删除的消息通知其他模块进行相应的处理
        try {
            ActiveMqMessageVO message = new ActiveMqMessageVO();
            message.setTaskName("自定义标签删除导致细分更新");
            message.setServiceName(AFTER_DELETE_CUSTOM_TAG_TASK);
            String usefulMsg = customTagToDeleteIn.getCustomTagId();
            message.setMessage(usefulMsg);
            mqTopicService.senderMessage(MQ_CUSTOM_TAG_DELETE_TOPIC,message);
        }catch (JMSException jmsException){
            logger.info("CustomTagUpdateParent JMS Exception: " + jmsException.getMessage() );
        }
        return baseOutput;
    }

}
