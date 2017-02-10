package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomTagMoveToSpecifyCategoryService;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.vo.ActiveMqMessageVO;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CtMoveToSpeCategoryIn;
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
public class CustomTagMoveToSpecifyCategoryServiceImpl implements CustomTagMoveToSpecifyCategoryService {

    @Autowired
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Autowired
    private MongoCustomTagDao mongoCustomTagDao;

    @Autowired
    private MQTopicService mqTopicService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final String SEGMENTATION_UPDATE_BY_CUSTOMTAG_MOVE_TASK = "segmentationUpdateByCstmTgMoveTask";
    private static final String MQ_CUSTOM_TAG_UPDATE_PARENT_TOPIC = "customTagUpdateParentTopic";

    @Override
    public BaseOutput moveCustomTagToSpecifyCategory(CtMoveToSpeCategoryIn ctMoveToSpeCategoryIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        //Todo:0判断传递的分类是否是有效分类，如果是无效分类，则返回相应的错误码。
        CustomTagCategory verifyCustomTagCategory = new CustomTagCategory();
        verifyCustomTagCategory.setCustomTagCategoryId(ctMoveToSpeCategoryIn.getCustomTagOldCategoryId());
        CustomTagCategory targetOldCustomTagCategory = mongoCustomTagCategoryDao.findOne(verifyCustomTagCategory);
        verifyCustomTagCategory.setCustomTagCategoryId(ctMoveToSpeCategoryIn.getCustomTagNewCategoryId());
        CustomTagCategory targetNewCustomTagCategory = mongoCustomTagCategoryDao.findOne(verifyCustomTagCategory);
        if(targetOldCustomTagCategory == null || targetNewCustomTagCategory == null){
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_NOT_EXIST.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_NOT_EXIST.getMsg());
            return baseOutput;
        }

        //Todo:1根据要被移动的标签的名称选出他的所有的ParentId集合。
        CustomTag paramCustomTag = new CustomTag();
        paramCustomTag.setCustomTagName(ctMoveToSpeCategoryIn.getCustomTagName());
        List<CustomTag> customTagList = mongoCustomTagDao.find(paramCustomTag);
        List<String> customTagParentIdList = new LinkedList<>();
        for(CustomTag customTag : customTagList){
            customTagParentIdList.add(customTag.getParentId());
        }

        //Todo:2判断这个集合是否包含了新的分类Id，如果是返回无法移动，如果不是，进行下一步。
        if(customTagParentIdList.contains(ctMoveToSpeCategoryIn.getCustomTagNewCategoryId())){
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_DUPLICATED_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_DUPLICATED_ERROR.getMsg());
            return baseOutput;
        }

        //Todo:3更新原分类的children_tag_list，去掉被移动的标签Id
        CustomTagCategory paramOldCustomCategory = new CustomTagCategory();
        paramOldCustomCategory.setCustomTagCategoryId(ctMoveToSpeCategoryIn.getCustomTagOldCategoryId());
        CustomTagCategory oldCustomTagCategory = mongoCustomTagCategoryDao.findOne(paramOldCustomCategory);
        if(CollectionUtils.isEmpty(oldCustomTagCategory.getChildrenCustomTagList())){
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_NO_CHILDREN.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_NO_CHILDREN.getMsg());
            return baseOutput;
        }
        oldCustomTagCategory.getChildrenCustomTagList().remove(ctMoveToSpeCategoryIn.getCustomTagId());
        mongoCustomTagCategoryDao.updateCustomTagCategoryChildrenListById(ctMoveToSpeCategoryIn.getCustomTagOldCategoryId(),oldCustomTagCategory);

        //Todo:4更新新的分类的children_tag_list，添加被移动的标签Id
        CustomTagCategory paramNewcustomTagCategory = new CustomTagCategory();
        paramNewcustomTagCategory.setCustomTagCategoryId(ctMoveToSpeCategoryIn.getCustomTagNewCategoryId());
        CustomTagCategory newCustomTagCategory = mongoCustomTagCategoryDao.findOne(paramNewcustomTagCategory);
        newCustomTagCategory.getChildrenCustomTagList().add(ctMoveToSpeCategoryIn.getCustomTagId());
        mongoCustomTagCategoryDao.updateCustomTagCategoryChildrenListById(ctMoveToSpeCategoryIn.getCustomTagNewCategoryId(),newCustomTagCategory);

        //Todo:5更新标签的ParentId
        CustomTag paramUpdateCustomTag = new CustomTag();
        paramUpdateCustomTag.setCustomTagId(ctMoveToSpeCategoryIn.getCustomTagId());
        CustomTag updateCustomTag = mongoCustomTagDao.findOne(paramUpdateCustomTag);
        if(updateCustomTag == null){
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR.getMsg());
            return baseOutput;
        }
        updateCustomTag.setParentId(ctMoveToSpeCategoryIn.getCustomTagNewCategoryId());
        mongoCustomTagDao.updateCustomTagParentIdByCustomTagId(ctMoveToSpeCategoryIn.getCustomTagId(),ctMoveToSpeCategoryIn.getCustomTagNewCategoryId());

        //Todo:6发送自定义标签从一个分类移动到另一个分类的消息
        try {
            ActiveMqMessageVO message = new ActiveMqMessageVO();
            message.setTaskName("自定义标签更改分类导致细分更新");
            message.setServiceName(SEGMENTATION_UPDATE_BY_CUSTOMTAG_MOVE_TASK);
            String usefulMsg = ctMoveToSpeCategoryIn.getCustomTagId() + ":" + ctMoveToSpeCategoryIn.getCustomTagNewCategoryId() + ":" + ctMoveToSpeCategoryIn.getCustomTagNewCategoryName();
            message.setMessage(usefulMsg);
            mqTopicService.senderMessage(MQ_CUSTOM_TAG_UPDATE_PARENT_TOPIC,message);
        }catch (JMSException jmsException){
            logger.info("CustomTagUpdateParent JMS Exception: " + jmsException.getMessage() );
        }
        return baseOutput;
    }

}
