package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.SmsSignature;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CreateCustomTagToCategoryService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagNameIn;
import cn.rongcapital.mkt.vo.in.CustomTagSaveToCategoryIn;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by hiro on 17/2/14.
 */

@RunWith(MockitoJUnitRunner.class)
public class CreateCustomTagToCategoryServiceImplTest {

    private CreateCustomTagToCategoryService createCustomTagToCategoryService;

    @Mock
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Mock
    private MongoCustomTagDao mongoCustomTagDao;

    @Before
    public void setUp() throws Exception{
        //Todo:1Mock这个Service的实现类的数据，一般情况下主要指Service里面包含的Dao。
        createCustomTagToCategoryService = new CreateCustomTagToCategoryServiceImpl();
        ReflectionTestUtils.setField(createCustomTagToCategoryService, "mongoCustomTagCategoryDao", mongoCustomTagCategoryDao);
        ReflectionTestUtils.setField(createCustomTagToCategoryService, "mongoCustomTagDao", mongoCustomTagDao);
    }


    @Test
    public void createCustomTagToCategory() throws Exception {
        //Todo:1根据业务逻辑对参数Mock不同的值
        CustomTagSaveToCategoryIn customTagSaveToCategoryIn = new CustomTagSaveToCategoryIn();
        customTagSaveToCategoryIn.setCustomTagCategoryId("testId01");
        customTagSaveToCategoryIn.setCustomTagCategoryName("testCustomTagName");
        ArrayList<CustomTagNameIn> customTagNameInList = new ArrayList<>();
        CustomTagNameIn customTagNameIn = new CustomTagNameIn();
        customTagNameIn.setCustomTagName("customTagTest");
        customTagNameInList.add(customTagNameIn);
        customTagSaveToCategoryIn.setCustomTagList(customTagNameInList);

        //Todo:2覆盖标签分类不存在的业务逻辑
        CustomTagCategory paramCustomTagCategory = new CustomTagCategory();
        paramCustomTagCategory.setCustomTagCategoryId("testId01");
        Mockito.when(mongoCustomTagCategoryDao.findOne(Mockito.argThat(new CreateCustomTagToCategoryServiceImplTest.CustomTagCategoryMatcher(paramCustomTagCategory)))).thenReturn(null);
        BaseOutput baseOutput = createCustomTagToCategoryService.createCustomTagToCategory(customTagSaveToCategoryIn);
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_NOT_EXIST.getCode(),baseOutput.getCode());
    }

    class CustomTagCategoryMatcher extends ArgumentMatcher<CustomTagCategory> {

        CustomTagCategory customTagCategory=null;

        public  CustomTagCategoryMatcher(CustomTagCategory customTagCategory) {
            this.customTagCategory = customTagCategory;
        }

        public boolean matches(Object obj) {
            String customTagCategoryId = customTagCategory.getCustomTagCategoryId();
            String objCustomTagCategoryId = ((CustomTagCategory)obj).getCustomTagCategoryId();
            if(customTagCategoryId.equals(objCustomTagCategoryId)) return true;
            return false;
        }
    }
}






















