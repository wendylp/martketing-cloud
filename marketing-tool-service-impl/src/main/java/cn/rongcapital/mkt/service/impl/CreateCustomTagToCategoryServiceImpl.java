package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CreateCustomTagToCategoryService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagCreateIn;
import cn.rongcapital.mkt.vo.in.CustomTagNameIn;
import cn.rongcapital.mkt.vo.in.CustomTagSaveToCategoryIn;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.LinkedList;
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
    public BaseOutput createCustomTagToCategory(@NotEmpty CustomTagSaveToCategoryIn customTagSaveToCategoryIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        if(customTagSaveToCategoryIn.getCustomTagList().size() == 0){
            return baseOutput;
        }

        //0判断分类是否存在，如果不存在，返回相关的错误代码
        CustomTagCategory paramCustomTagCategory = new CustomTagCategory();
        paramCustomTagCategory.setCustomTagCategoryId(customTagSaveToCategoryIn.getCustomTagCategoryId());
        CustomTagCategory resutlCustomTagCategory = mongoCustomTagCategoryDao.findOne(paramCustomTagCategory);
        if(resutlCustomTagCategory == null){
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_NOT_EXIST.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_NOT_EXIST.getMsg());
            return baseOutput;
        }

        //1选出当前分类下的所有标签，与前端所传的List进行逐个对比，没有被包含的添加到新的List当中。
        CustomTag paramCustomTag = new CustomTag();
        paramCustomTag.setParentId(customTagSaveToCategoryIn.getCustomTagCategoryId());
        List<CustomTag> customTagList = mongoCustomTagDao.find(paramCustomTag);

        List<String> oldCustomTagNameList = new LinkedList<>();
        List<String> insertCustomTagNameList = new LinkedList<>();
        if(!CollectionUtils.isEmpty(customTagList)){
            //选出所有老标签的名称
            for(CustomTag customTag : customTagList){
                oldCustomTagNameList.add(customTag.getCustomTagName());
            }

            //判断老标签是否已包含了前段所传的参数
            for(CustomTagNameIn customTagNameIn : customTagSaveToCategoryIn.getCustomTagList()){
                if(!oldCustomTagNameList.contains(customTagNameIn.getCustomTagName())){
                    insertCustomTagNameList.add(customTagNameIn.getCustomTagName());
                }
            }
        }else{
            for(CustomTagNameIn customTagNameIn : customTagSaveToCategoryIn.getCustomTagList()){
                insertCustomTagNameList.add(customTagNameIn.getCustomTagName());
            }
        }

        //2如果没有,新建标签,更新这个标签的相关属性
        if(CollectionUtils.isEmpty(insertCustomTagNameList)) return baseOutput;
        for(String insertTagName : insertCustomTagNameList){
            CustomTag insertCustomTag = new CustomTag();
            insertCustomTag.setParentId(customTagSaveToCategoryIn.getCustomTagCategoryId());    //父节点Id
            insertCustomTag.setCustomTagId(RandomStringUtils.randomAlphanumeric(10) + System.currentTimeMillis());    //子节点Id
            insertCustomTag.setCustomTagName(insertTagName);  //子节点名称
            insertCustomTag.setCustomTagSource(null);
            insertCustomTag.setCreateTime(new Date());
            insertCustomTag.setUpdateTime(new Date());
            insertCustomTag.setCustomTagType(ApiConstant.CUSTOM_TAG_CATEGORY_TYPE_DEFINE);
            insertCustomTag.setIsDeleted(Integer.valueOf(ApiConstant.TABLE_DATA_STATUS_VALID));
            insertCustomTag.setRecommendFlag(null);
            insertCustomTag.setCoverNumber(new Integer(0));
            insertCustomTag.setCoverFrequency(new Integer(0));
            mongoCustomTagDao.insertCustomTag(insertCustomTag);

            //Todo:3更新他所属分类的儿子节点列表
            paramCustomTagCategory = new CustomTagCategory();
            paramCustomTagCategory.setCustomTagCategoryId(customTagSaveToCategoryIn.getCustomTagCategoryId());
            CustomTagCategory customTagCategory = mongoCustomTagCategoryDao.findOne(paramCustomTagCategory);
            customTagCategory.getChildrenCustomTagList().add(insertCustomTag.getCustomTagId());
            mongoCustomTagCategoryDao.updateCustomTagCategoryChildrenListById(customTagSaveToCategoryIn.getCustomTagCategoryId(),customTagCategory);
        }

        return baseOutput;
    }

}
