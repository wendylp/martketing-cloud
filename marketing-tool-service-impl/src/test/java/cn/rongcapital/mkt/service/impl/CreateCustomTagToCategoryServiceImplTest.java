package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.Asset;
import cn.rongcapital.mkt.po.SmsSignature;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
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
import java.util.List;

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
        //1Mock这个Service的实现类的数据，一般情况下主要指Service里面包含的Dao。
        createCustomTagToCategoryService = new CreateCustomTagToCategoryServiceImpl();
        ReflectionTestUtils.setField(createCustomTagToCategoryService, "mongoCustomTagCategoryDao", mongoCustomTagCategoryDao);
        ReflectionTestUtils.setField(createCustomTagToCategoryService, "mongoCustomTagDao", mongoCustomTagDao);
    }


    @Test
    public void createCustomTagToCategory() throws Exception {
        //1根据业务逻辑对参数Mock不同的
        CustomTagSaveToCategoryIn customTagSaveToCategoryIn = new CustomTagSaveToCategoryIn();
        customTagSaveToCategoryIn.setCustomTagCategoryId("testId01");
        customTagSaveToCategoryIn.setCustomTagCategoryName("testCustomTagName");
        ArrayList<CustomTagNameIn> customTagNameInList = new ArrayList<>();
        CustomTagNameIn customTagNameIn = new CustomTagNameIn();
        customTagNameIn.setCustomTagName("customTagTest");
        customTagNameInList.add(customTagNameIn);
        customTagSaveToCategoryIn.setCustomTagList(customTagNameInList);

        //2覆盖标签分类不存在的业务逻辑
        CustomTagCategory paramCustomTagCategory = new CustomTagCategory();
        paramCustomTagCategory.setCustomTagCategoryId("testId01");

        CustomTagCategory resultCustomTagCategory = new CustomTagCategory();
        resultCustomTagCategory.setChildrenCustomTagList(new ArrayList<>());
        Mockito.when(mongoCustomTagCategoryDao.findOne(Mockito.argThat(new CreateCustomTagToCategoryServiceImplTest.CustomTagCategoryMatcher(paramCustomTagCategory)))).thenReturn(null).thenReturn(new CustomTagCategory()).thenReturn(resultCustomTagCategory);
        BaseOutput baseOutput = createCustomTagToCategoryService.createCustomTagToCategory(customTagSaveToCategoryIn);
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_CUSTOM_TAG_CATEGORY_NOT_EXIST.getCode(),baseOutput.getCode());
        //3覆盖要添加的新标签已经存在于所选分类中
        CustomTag paramCustomTag = new CustomTag();
        paramCustomTag.setParentId(customTagSaveToCategoryIn.getCustomTagCategoryId());
        List<CustomTag> mockResultList = new ArrayList<>();
        CustomTag resultCutsomTag = new CustomTag();
        resultCutsomTag.setCustomTagName("customTagTest");
        mockResultList.add(resultCutsomTag);

        List<CustomTag> mockResultList2 = new ArrayList<>();
        Mockito.when(mongoCustomTagDao.find(Mockito.argThat(new CreateCustomTagToCategoryServiceImplTest.CustomTagMatcher(paramCustomTag)))).thenReturn(mockResultList).thenReturn(mockResultList2);
        baseOutput = createCustomTagToCategoryService.createCustomTagToCategory(customTagSaveToCategoryIn);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(),baseOutput.getCode());

        //4覆盖正常的标签添加逻辑
        baseOutput = createCustomTagToCategoryService.createCustomTagToCategory(customTagSaveToCategoryIn);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(),baseOutput.getCode());
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

    class CustomTagMatcher extends ArgumentMatcher<CustomTag> {

        CustomTag customTag = null;

        public  CustomTagMatcher(CustomTag customTag) {
            this.customTag = customTag;
        }

        public boolean matches(Object obj) {
            String customTagParentId = customTag.getParentId();
            String objCustomTagParentId = ((CustomTag)obj).getParentId();
            if(customTagParentId.equals(objCustomTagParentId)) return true;
            return false;
        }
    }
}






















