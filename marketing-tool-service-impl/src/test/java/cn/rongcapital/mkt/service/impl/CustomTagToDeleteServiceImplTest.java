package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomTagToDeleteService;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagToDeleteIn;
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
public class CustomTagToDeleteServiceImplTest {

    private CustomTagToDeleteService customTagToDeleteService;

    @Mock
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Mock
    private MongoCustomTagDao mongoCustomTagDao;

    @Mock
    private MQTopicService mqTopicService;

    @Before
    public void setUp() throws Exception{
        customTagToDeleteService = new CustomTagToDeleteServiceImpl();
        ReflectionTestUtils.setField(customTagToDeleteService, "mongoCustomTagCategoryDao", mongoCustomTagCategoryDao);
        ReflectionTestUtils.setField(customTagToDeleteService, "mongoCustomTagDao", mongoCustomTagDao);
        ReflectionTestUtils.setField(customTagToDeleteService, "mqTopicService", mqTopicService);
    }

    @Test
    public void deleteCustomTag() throws Exception {
        CustomTagToDeleteIn paramCustomTagToDeleteIn = new CustomTagToDeleteIn();
        paramCustomTagToDeleteIn.setCustomTagCategoryId("customTagCategoryId");
        paramCustomTagToDeleteIn.setCustomTagCategoryName("customTagCategoryName");
        paramCustomTagToDeleteIn.setCustomTagId("customTagId");
        paramCustomTagToDeleteIn.setCustomTagName("customTagName");

        BaseOutput baseOutput = customTagToDeleteService.deleteCustomTag(paramCustomTagToDeleteIn);
        Assert.assertEquals(ApiErrorCode.PARAMETER_ERROR.getCode(),baseOutput.getCode());
        CustomTagCategory resultCustomTagCategory = new CustomTagCategory();
        resultCustomTagCategory.setChildrenCustomTagList(new ArrayList<>());
        resultCustomTagCategory.getChildrenCustomTagList().add(new String("customTagId"));
        Mockito.when(mongoCustomTagCategoryDao.findOne((CustomTagCategory) Mockito.any())).thenReturn(resultCustomTagCategory);

        baseOutput = customTagToDeleteService.deleteCustomTag(paramCustomTagToDeleteIn);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(),baseOutput.getCode());
    }

}