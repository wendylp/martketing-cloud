package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomtagListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class CustomtagListServiceTest {
    private CustomtagListService customtagListService;

    @Mock
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Mock
    private MongoCustomTagDao mongoCustomTagDao;

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

        Mockito.when(mongoCustomTagCategoryDao.findByCategoryId(any())).thenReturn(customTagCategory);
        Mockito.when(mongoCustomTagDao.findByCustomTagIdList(any(), any(), any())).thenReturn(customTagLists);
        Mockito.when(mongoCustomTagDao.countValidCustomTag(any())).thenReturn(totalCount);

        customtagListService = new CustomtagListServiceImpl();

        ReflectionTestUtils.setField(customtagListService, "mongoCustomTagCategoryDao", mongoCustomTagCategoryDao);
        ReflectionTestUtils.setField(customtagListService, "mongoCustomTagDao", mongoCustomTagDao);

    }

    @Test
    public void testCustomtagListGet() {
        BaseOutput baseOutput =
                customtagListService.customtagListGet(ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID, 1, 10);

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(2, baseOutput.getTotal());
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) baseOutput.getData().get(0);
        Assert.assertEquals(customTagCategory.getCustomTagCategoryId(), data.get("custom_tag_category_id"));
        Assert.assertEquals(false, data.get("add_status"));
        Assert.assertEquals(true, data.get("move_status"));

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> childernTag = (List<Map<String, Object>>) data.get("childern_tag");
        Assert.assertEquals(customTagLists.get(0).getCustomTagId(), childernTag.get(0).get("custom_tag_id"));
        Assert.assertEquals(customTagLists.get(1).getCustomTagId(), childernTag.get(1).get("custom_tag_id"));
    }

    @After
    public void tearDown() throws Exception {}


}
