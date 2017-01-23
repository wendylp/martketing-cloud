package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomtagFuzzyNameListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class CustomtagFuzzyNameListServiceTest {

    private CustomtagFuzzyNameListService customtagFuzzyNameListService;

    @Mock
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Mock
    private MongoCustomTagDao mongoCustomTagDao;

    private CustomTagCategory customTagCategory = new CustomTagCategory();

    List<CustomTag> customTagLists = new ArrayList<CustomTag>();

    @Before
    public void setUp() throws Exception {

        customTagCategory.setCustomTagCategoryId("1");
        customTagCategory.setCustomTagCategoryName("一个自定义分类");
        ArrayList<String> customTagList = new ArrayList<String>();
        customTagList.add("自定义标签");
        customTagCategory.setChildrenCustomTagList(customTagList);

        CustomTag customTagOne = new CustomTag();
        customTagOne.setCustomTagName("自定义标签1");
        customTagLists.add(customTagOne);
        CustomTag customTagTwo = new CustomTag();
        customTagTwo.setCustomTagName("完全匹配");
        customTagLists.add(customTagTwo);

        Mockito.when(mongoCustomTagCategoryDao.findByCategoryId(any())).thenReturn(customTagCategory);
        Mockito.when(mongoCustomTagDao.findByCustomTagIdListAndNameFuzzy(any(), any())).thenReturn(customTagLists);

        customtagFuzzyNameListService = new CustomtagFuzzyNameListServiceImpl();

        ReflectionTestUtils.setField(customtagFuzzyNameListService, "mongoCustomTagCategoryDao",
                mongoCustomTagCategoryDao);
        ReflectionTestUtils.setField(customtagFuzzyNameListService, "mongoCustomTagDao", mongoCustomTagDao);

    }

    @Test
    public void testCustomtagFuzzyNameListGet() {
        BaseOutput baseOutput = customtagFuzzyNameListService.customtagFuzzyNameListGet("", "完全匹配");
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(2, baseOutput.getTotal());
        List<String> resultOne = new ArrayList<String>();
        resultOne.add("完全匹配");
        resultOne.add("自定义标签1");
        Assert.assertEquals(resultOne, baseOutput.getData());
        
        baseOutput = customtagFuzzyNameListService.customtagFuzzyNameListGet("", null);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(2, baseOutput.getTotal());
        List<String> resultTwo = new ArrayList<String>();
        resultTwo.add("自定义标签1");
        resultTwo.add("完全匹配");
        Assert.assertEquals(resultTwo, baseOutput.getData());
    }

    @After
    public void tearDown() throws Exception {}



}
