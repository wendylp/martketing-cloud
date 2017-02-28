package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.service.CustomTagNameEditService;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.vo.ActiveMqMessageVO;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagNameEditIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.jms.JMSException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hiro on 17/2/7.
 */
@Service
public class CustomTagNameEditServiceImpl implements CustomTagNameEditService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Autowired
    private MongoCustomTagDao mongoCustomTagDao;

    @Autowired
    private MQTopicService mqTopicService;

    private static final String MQ_CUSTOM_TAG_NAME_EDIT_TOPIC = "customTagNameEditTopic";
    private final String SEGMENTATION_UPDATE_BY_CUSTOMTAGE_EDIT_TASK = "segmentationUpdateByCstmTgEditTask";

    @Override
    public BaseOutput editCustomTagName(CustomTagNameEditIn customTagNameEditIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        //1通过分类Id查询所有标签的集合，构造出标签名称的集合
        CustomTag paramCustomTag = new CustomTag();
        paramCustomTag.setParentId(customTagNameEditIn.getCustomTagCategoryId());
        List<CustomTag> customTagList = mongoCustomTagDao.find(paramCustomTag);
        if(CollectionUtils.isEmpty(customTagList)){
            baseOutput.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.PARAMETER_ERROR.getMsg());
            return baseOutput;
        }
        List<String> existCustomTagNameList = new LinkedList<>();
        for(CustomTag customTag : customTagList){
            existCustomTagNameList.add(customTag.getCustomTagName());
        }
        //2判断是否包含了要修改的标签名称，如果有则返回相应的错误信息，没有进行下一步
        if(existCustomTagNameList.contains(customTagNameEditIn.getCustomTagNewName())){
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_DUPLICATED_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_DUPLICATED_ERROR.getMsg());
            return baseOutput;
        }
        //3如果没有则进行名称修改
        mongoCustomTagDao.updateCustomTagNameByCustomTagId(customTagNameEditIn.getCustomTagId(),customTagNameEditIn.getCustomTagNewName());

        //4发送标签名称发生修改的消息
        try {
            ActiveMqMessageVO message = new ActiveMqMessageVO();
            message.setTaskName("自定义标签更新名称导致细分更新");
            message.setServiceName(SEGMENTATION_UPDATE_BY_CUSTOMTAGE_EDIT_TASK);
            String usefulMsg = customTagNameEditIn.getCustomTagId() + ":" + customTagNameEditIn.getCustomTagNewName();
            message.setMessage(usefulMsg);
            mqTopicService.senderMessage(MQ_CUSTOM_TAG_NAME_EDIT_TOPIC,message);
        }catch (JMSException jmsException){
            logger.info("CustomTagUpdateParent JMS Exception: " + jmsException.getMessage() );
        }
        return baseOutput;
    }

}
