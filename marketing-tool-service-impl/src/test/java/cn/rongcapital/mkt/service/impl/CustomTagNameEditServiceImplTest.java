package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.service.CustomTagNameEditService;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagNameEditIn;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiro on 17/2/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomTagNameEditServiceImplTest {

    private CustomTagNameEditService customTagNameEditService;

    @Mock
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Mock
    private MongoCustomTagDao mongoCustomTagDao;

    @Mock
    private MQTopicService mqTopicService;

    @Before
    public void setUp() throws Exception{
        customTagNameEditService = new CustomTagNameEditServiceImpl();
        ReflectionTestUtils.setField(customTagNameEditService, "mongoCustomTagCategoryDao", mongoCustomTagCategoryDao);
        ReflectionTestUtils.setField(customTagNameEditService, "mongoCustomTagDao", mongoCustomTagDao);
        ReflectionTestUtils.setField(customTagNameEditService, "mqTopicService", mqTopicService);
    }

    @Test
    public void editCustomTagName() throws Exception {
        CustomTagNameEditIn paramCustomTagNameEditIn = new CustomTagNameEditIn();
        paramCustomTagNameEditIn.setCustomTagId("customTagId");
        paramCustomTagNameEditIn.setCustomTagCategoryId("customTagCategoryId");
        paramCustomTagNameEditIn.setCustomTagCategoryName("customTagCategoryName");
        paramCustomTagNameEditIn.setCustomTagOldName("oldCustomTagName");
        paramCustomTagNameEditIn.setCustomTagNewName("newCutsomTagName");

        BaseOutput baseOutput = customTagNameEditService.editCustomTagName(paramCustomTagNameEditIn);
        Assert.assertEquals(ApiErrorCode.PARAMETER_ERROR.getCode(),baseOutput.getCode());

        List<CustomTag> resultCustomTagList = new ArrayList<>();
        CustomTag customTag = new CustomTag();
        resultCustomTagList.add(customTag);
        Mockito.when(mongoCustomTagDao.find((CustomTag) Mockito.any())).thenReturn(resultCustomTagList);
        baseOutput = customTagNameEditService.editCustomTagName(paramCustomTagNameEditIn);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(),baseOutput.getCode());
    }

}