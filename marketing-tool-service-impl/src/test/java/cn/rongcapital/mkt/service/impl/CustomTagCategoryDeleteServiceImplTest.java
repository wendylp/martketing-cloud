package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomTagCategoryDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagCategoryDeleteIn;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;

/**
 * Created by hiro on 17/2/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomTagCategoryDeleteServiceImplTest {

    private CustomTagCategoryDeleteService customTagCategoryDeleteService;

    @Mock
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Before
    public void setUp() throws Exception{
        customTagCategoryDeleteService = new CustomTagCategoryDeleteServiceImpl();
        ReflectionTestUtils.setField(customTagCategoryDeleteService, "mongoCustomTagCategoryDao", mongoCustomTagCategoryDao);
    }

    @Test
    public void deleteCustomTagCategory() throws Exception {
        CustomTagCategoryDeleteIn paramCustomTagCategoryDeleteIn = new CustomTagCategoryDeleteIn();
        paramCustomTagCategoryDeleteIn.setCustomTagCategoryId("customTagCategoryId");
        paramCustomTagCategoryDeleteIn.setCustomTagCategoryName("customTagCategoryName");

        BaseOutput baseOutput = customTagCategoryDeleteService.deleteCustomTagCategory(paramCustomTagCategoryDeleteIn);
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_NOT_EXIST.getCode(),baseOutput.getCode());

        Mockito.when(mongoCustomTagCategoryDao.findOne((CustomTagCategory) Mockito.any())).thenReturn(new CustomTagCategory());
        baseOutput = customTagCategoryDeleteService.deleteCustomTagCategory(paramCustomTagCategoryDeleteIn);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(),baseOutput.getCode());
    }


}