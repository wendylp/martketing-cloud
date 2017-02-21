package cn.rongcapital.mkt.service.impl;

import static org.junit.Assert.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomtagQrcodeFuzzyListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.CustomtagQrcodeFuzzyListOut;
@RunWith(MockitoJUnitRunner.class)
public class CustomtagQrcodeFuzzyListServiceTest {
    
    private CustomtagQrcodeFuzzyListService customtagQrcodeFuzzyListService;

    @Mock
    private MongoCustomTagDao mongoCustomTagDao;

    @Mock
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;
    
    private CustomTagCategory customTagCategory = new CustomTagCategory();

    private List<CustomTag> customTagLists = new ArrayList<CustomTag>();

    private long totalCount = 10;
    
    @Before
    public void setUp() throws Exception {
        customTagCategory.setCustomTagCategoryId(ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID);
        customTagCategory.setCustomTagCategoryName("未分类");
        ArrayList<String> customTagList = new ArrayList<String>();
        customTagList.add("自定义标签");
        customTagCategory.setChildrenCustomTagList(customTagList);

        CustomTag customTagOne = new CustomTag();
        customTagOne.setCustomTagId("1");
        customTagOne.setCustomTagName("自定义分类1");
        customTagOne.setCoverNumber(100);
        customTagOne.setCoverFrequency(1000);
        customTagLists.add(customTagOne);
        CustomTag customTagTwo = new CustomTag();
        customTagTwo.setCustomTagId("2");
        customTagTwo.setCustomTagName("自定义分类2");
        customTagTwo.setCoverNumber(20);
        customTagTwo.setCoverFrequency(200);
        customTagLists.add(customTagTwo);
        
        Mockito.when(mongoCustomTagCategoryDao.findByChildrenCustomTagList(any())).thenReturn(customTagCategory);
        Mockito.when(mongoCustomTagDao.findByCustomTagNameFuzzy(any(), any(), any())).thenReturn(customTagLists);
        Mockito.when(mongoCustomTagDao.countByCustomTagNameFuzzy(any())).thenReturn(totalCount);
        
        customtagQrcodeFuzzyListService = new CustomtagQrcodeFuzzyListServiceImpl();
        
        ReflectionTestUtils.setField(customtagQrcodeFuzzyListService, "mongoCustomTagCategoryDao", mongoCustomTagCategoryDao);
        ReflectionTestUtils.setField(customtagQrcodeFuzzyListService, "mongoCustomTagDao", mongoCustomTagDao);
    }
    
    @Test
    public void testCustomtagQrcodeFuzzyListGet() {
        BaseOutput baseOutput =customtagQrcodeFuzzyListService.customtagQrcodeFuzzyListGet("测试", 1, 10);
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(customTagLists.size(), baseOutput.getTotal());
        Assert.assertEquals(totalCount, baseOutput.getTotalCount());
        
        List<CustomtagQrcodeFuzzyListOut> customtagQrcodeFuzzyListOutLists = new ArrayList<CustomtagQrcodeFuzzyListOut>();
        for (CustomTag customTagList : customTagLists) {
            CustomtagQrcodeFuzzyListOut customtagQrcodeFuzzyListOut =
                    new CustomtagQrcodeFuzzyListOut(customTagList.getCustomTagId(),
                            customTagList.getCustomTagName(), customTagCategory.getCustomTagCategoryId(), customTagCategory.getCustomTagCategoryName());
            customtagQrcodeFuzzyListOutLists.add(customtagQrcodeFuzzyListOut);
        }
        Assert.assertEquals(customtagQrcodeFuzzyListOutLists, baseOutput.getData());
        
    }

    @After
    public void tearDown() throws Exception {}



}
