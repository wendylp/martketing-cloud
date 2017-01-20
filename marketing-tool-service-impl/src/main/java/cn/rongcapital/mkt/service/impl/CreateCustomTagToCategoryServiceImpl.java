package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.service.CreateCustomTagToCategoryService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagCreateIn;
import com.sun.tools.corba.se.idl.InterfaceGen;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.NotEmpty;
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
    public BaseOutput createCustomTagToCategory(@NotEmpty CustomTagCreateIn customTagCreateIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        if(customTagCreateIn.getCustomTagList().size() == 0){
            return baseOutput;
        }

        //Todo:1检查当前分类下是否有同名标签
        CustomTag paramCustomTag = new CustomTag();
        paramCustomTag.setIsDeleted(Integer.valueOf(ApiConstant.TABLE_DATA_STATUS_VALID));
        paramCustomTag.setParentId(customTagCreateIn.getCustomTagCategoryId());
        List<CustomTag> customTagList = mongoCustomTagDao.find(paramCustomTag);

        //Todo:2如果没有,新建标签,更新这个标签的相关属性

        //Todo:3更新他所属分类的儿子节点列表

        return baseOutput;
    }

}
