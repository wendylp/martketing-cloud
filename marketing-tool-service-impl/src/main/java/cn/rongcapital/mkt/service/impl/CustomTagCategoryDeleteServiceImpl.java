package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomTagCategoryDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagCategoryDeleteIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Created by hiro on 17/2/8.
 */
@Service
public class CustomTagCategoryDeleteServiceImpl implements CustomTagCategoryDeleteService{

    @Autowired
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Override
    public BaseOutput deleteCustomTagCategory(CustomTagCategoryDeleteIn customTagCategoryDeleteIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        //1判断是否是未定义分类，如果是返回未定义分类不能删除的提示信息，否则下一步
        if(customTagCategoryDeleteIn.getCustomTagCategoryId().equals(ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID)){
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_DEFAULT_CATEGORY_CANT_DELETE.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_DEFAULT_CATEGORY_CANT_DELETE.getMsg());
            return baseOutput;
        }

        //1.5判断是否是有效的自定义分类，如果是不存在的分类则直接返回错误
        CustomTagCategory verifyCustomTagCategory = new CustomTagCategory();
        verifyCustomTagCategory.setCustomTagCategoryId(customTagCategoryDeleteIn.getCustomTagCategoryId());
        CustomTagCategory resultCustomTagCategory = mongoCustomTagCategoryDao.findOne(verifyCustomTagCategory);
        if(resultCustomTagCategory == null){
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_NOT_EXIST.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_NOT_EXIST.getMsg());
            return baseOutput;
        }

        //2判断该分类下是否有自定义标签，如果有返回相应的信息，否则下一步
        CustomTagCategory paramCustomTagCategory = new CustomTagCategory();
        paramCustomTagCategory.setCustomTagCategoryId(customTagCategoryDeleteIn.getCustomTagCategoryId());
        CustomTagCategory toDeleteCustomTagCategory = mongoCustomTagCategoryDao.findOne(paramCustomTagCategory);
        if(toDeleteCustomTagCategory == null){
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_NOT_EXIST.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_NOT_EXIST.getMsg());
            return baseOutput;
        }
        if(!CollectionUtils.isEmpty(toDeleteCustomTagCategory.getChildrenCustomTagList())){
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_HAS_CHILDREN.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_HAS_CHILDREN.getMsg());
            return baseOutput;
        }
        //3对该自定义标签分类进行逻辑删除
        mongoCustomTagCategoryDao.logicalDeleteCustomTagCategoryById(customTagCategoryDeleteIn.getCustomTagCategoryId());

        return baseOutput;
    }

}
