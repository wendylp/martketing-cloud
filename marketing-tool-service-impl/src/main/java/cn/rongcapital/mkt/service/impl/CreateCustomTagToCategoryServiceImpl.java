package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.service.CreateCustomTagToCategoryService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagCreateIn;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by byf on 1/17/17.
 */
@Service
public class CreateCustomTagToCategoryServiceImpl implements CreateCustomTagToCategoryService{

    @Autowired
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Autowired
    private MongoCustomTagDao mongoCustomTagDao;

    @Override
    public BaseOutput createCustomTagToCategory(CustomTagCreateIn customTagCreateIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        if(customTagCreateIn.getCustomTagList().size() == 0){
            return baseOutput;
        }

        //Todo:0新建一个自定义Demo标签用来给其他人参考
        CustomTag insertCustomTag = new CustomTag();
        insertCustomTag.setCustomTagId(RandomStringUtils.random(10,true,true) + System.currentTimeMillis());
        insertCustomTag.setCustomTagName("自定义示例标签");
        insertCustomTag.setCreateTime(new Date());
        insertCustomTag.setParentId(ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID);
        insertCustomTag.setCustomTagType(ApiConstant.CUSTOM_TAG_CATEGORY_TYPE_DEFINE);
        insertCustomTag.setUpdateTime(new Date());
        insertCustomTag.setCustomTagSource("预留来源字段");
        insertCustomTag.setRecommnedFlag(0);
        insertCustomTag.setIsDeleted(Integer.valueOf(ApiConstant.TABLE_DATA_STATUS_VALID));
        mongoCustomTagDao.insertCustomTag(insertCustomTag);

        //Todo:1检查当前分类下是否有同名标签,如果分类ID为Null,则检查未分类的标签,如果有则不进行插入操作.
//        List<String> newCustomTag = new ArrayList<>();
//        if(StringUtils.isEmpty(customTagCreateIn.getCustomTagCategoryId())){
//            CustomTag paramCustomTag = new CustomTag();
//            paramCustomTag.setParentId(ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID);
//            paramCustomTag.setIsDeleted(Integer.valueOf(ApiConstant.TABLE_DATA_STATUS_VALID));
//            List<String> customTagNames = mongoCustomTagDao.findCustomTagNames(paramCustomTag);
//        }
        //Todo:2如果没有,新建标签,更新这个标签的相关属性

        //Todo:3更新他所属分类的儿子节点列表

        return baseOutput;
    }

}
