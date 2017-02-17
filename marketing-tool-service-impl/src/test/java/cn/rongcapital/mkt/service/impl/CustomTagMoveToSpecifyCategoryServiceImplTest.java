package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomTagMoveToSpecifyCategoryService;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CtMoveToSpeCategoryIn;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

/**
 * Created by hiro on 17/2/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomTagMoveToSpecifyCategoryServiceImplTest {

    private CustomTagMoveToSpecifyCategoryService customTagMoveToSpecifyCategoryService;

    @Mock
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Mock
    private MongoCustomTagDao mongoCustomTagDao;

    @Mock
    private MQTopicService mqTopicService;

    @Before
    public void setUp() throws Exception{
        customTagMoveToSpecifyCategoryService = new CustomTagMoveToSpecifyCategoryServiceImpl();
        ReflectionTestUtils.setField(customTagMoveToSpecifyCategoryService, "mongoCustomTagCategoryDao", mongoCustomTagCategoryDao);
        ReflectionTestUtils.setField(customTagMoveToSpecifyCategoryService, "mongoCustomTagDao", mongoCustomTagDao);
        ReflectionTestUtils.setField(customTagMoveToSpecifyCategoryService, "mqTopicService", mqTopicService);
    }

    @Test
    public void moveCustomTagToSpecifyCategory() throws Exception {
        //0.设置出传入参数
        CtMoveToSpeCategoryIn paramCtMoveToSpeCategoryIn = new CtMoveToSpeCategoryIn();
        paramCtMoveToSpeCategoryIn.setCustomTagOldCategoryId("oldCustomTagCategoryId");
        paramCtMoveToSpeCategoryIn.setCustomTagOldCategoryName("olcCustomTagCategoryName");
        paramCtMoveToSpeCategoryIn.setCustomTagNewCategoryId("newCustomTagCategoryId");
        paramCtMoveToSpeCategoryIn.setCustomTagNewCategoryName("newCustomTagCategoryName");
        paramCtMoveToSpeCategoryIn.setCustomTagId("customTagId");
        paramCtMoveToSpeCategoryIn.setCustomTagName("customTagName");

        //1.时间关系只验证正确逻辑
        CustomTagCategory oldCustomTagCategory = new CustomTagCategory();
        oldCustomTagCategory.setChildrenCustomTagList(new ArrayList<>());
        oldCustomTagCategory.getChildrenCustomTagList().add("customTagId");
        CustomTagCategory newCustomTagCategory = new CustomTagCategory();
        Mockito.when(mongoCustomTagCategoryDao.findOne((CustomTagCategory) Mockito.any())).thenReturn(oldCustomTagCategory).thenReturn(newCustomTagCategory).thenReturn(oldCustomTagCategory);
        Mockito.when(mongoCustomTagDao.find((CustomTag) Mockito.any())).thenReturn(new ArrayList<CustomTag>());
        Mockito.when(mongoCustomTagDao.findOne((CustomTag) Mockito.any())).thenReturn(new CustomTag());
        BaseOutput baseOutput = customTagMoveToSpecifyCategoryService.moveCustomTagToSpecifyCategory(paramCtMoveToSpeCategoryIn);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(),baseOutput.getCode());
    }

}