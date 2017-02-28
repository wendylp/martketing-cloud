package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.HashMap;
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

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.TagSegmentFuzzyListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.CustomTagSegmentOut;
import cn.rongcapital.mkt.vo.out.SystemTagSegmentOut;

@RunWith(MockitoJUnitRunner.class)
public class TagSegmentFuzzyListServiceTest {

    private TagSegmentFuzzyListService tagSegmentFuzzyListService;

    @Mock
    private TagValueCountDao tagValueCountDao;

    @Mock
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Mock
    private MongoCustomTagDao mongoCustomTagDao;


    private List<TagValueCount> tagValueCountLists = new ArrayList<TagValueCount>();

    private int systemTotalCount = 100;


    private CustomTagCategory customTagCategory = new CustomTagCategory();

    private List<CustomTag> customTagLists = new ArrayList<CustomTag>();

    private long customTotalCount = 500;

    @Before
    public void setUp() throws Exception {

        TagValueCount tagValueCountOne = new TagValueCount("systemTagId-1", "系统标签1", "值1", Long.valueOf(10), "1",
                "太阳系>地球>", "0", Integer.valueOf(0));
        TagValueCount tagValueCountTwo = new TagValueCount("systemTagId-2", "系统标签2", "系统标签2", Long.valueOf(10), "1",
                "太阳系>地球>", "1", Integer.valueOf(0));
        tagValueCountLists.add(tagValueCountOne);
        tagValueCountLists.add(tagValueCountTwo);

        customTagCategory.setCustomTagCategoryId("customTagCategory");
        customTagCategory.setCustomTagCategoryName("自定义标签分类");

        CustomTag customTagOne = new CustomTag("customTagId-1", "自定义标签1");
        CustomTag customTagTwo = new CustomTag("customTagId-2", "自定义标签2");
        customTagLists.add(customTagOne);
        customTagLists.add(customTagTwo);

        Mockito.when(tagValueCountDao.selectFuzzyTagValue(any())).thenReturn(tagValueCountLists);
        Mockito.when(tagValueCountDao.selectFuzzyTagValueCount(any())).thenReturn(systemTotalCount);

        Mockito.when(mongoCustomTagCategoryDao.findByChildrenCustomTagList(any())).thenReturn(customTagCategory);
        Mockito.when(mongoCustomTagDao.findByCustomTagNameFuzzyAndCoverNumber(any(), any())).thenReturn(customTagLists);
        Mockito.when(mongoCustomTagDao.countByCustomTagNameFuzzyAndCoverNumber(any())).thenReturn(customTotalCount);

        tagSegmentFuzzyListService = new TagSegmentFuzzyListServiceImpl();

        ReflectionTestUtils.setField(tagSegmentFuzzyListService, "tagValueCountDao", tagValueCountDao);
        ReflectionTestUtils.setField(tagSegmentFuzzyListService, "mongoCustomTagCategoryDao",
                mongoCustomTagCategoryDao);
        ReflectionTestUtils.setField(tagSegmentFuzzyListService, "mongoCustomTagDao", mongoCustomTagDao);
    }

    @Test
    public void testTagSegmentFuzzyListGet() {
        BaseOutput baseOutput = tagSegmentFuzzyListService.tagSegmentFuzzyListGet("测试");

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());


        List<SystemTagSegmentOut> systemTagSegmentOutLists = new ArrayList<SystemTagSegmentOut>();
        for (TagValueCount tagValueCountList : tagValueCountLists) {
            SystemTagSegmentOut systemTagSegmentOut = new SystemTagSegmentOut(tagValueCountList.getTagId(),
                    tagValueCountList.getTagName(), tagValueCountList.getTagValue(), tagValueCountList.getTagPath(),
                    tagValueCountList.getIsTag(), tagValueCountList.getSearchMod(), tagValueCountList.getTagValueSeq());
            systemTagSegmentOutLists.add(systemTagSegmentOut);
        }

        List<CustomTagSegmentOut> customTagSegmentOutLists = new ArrayList<CustomTagSegmentOut>();
        for (CustomTag customTagList : customTagLists) {
            CustomTagSegmentOut customTagSegmentOut = new CustomTagSegmentOut(customTagList.getCustomTagId(),
                    customTagList.getCustomTagName(), customTagCategory.getCustomTagCategoryName() + ">",
                    customTagCategory.getCustomTagCategoryId(), customTagCategory.getCustomTagCategoryName());
            customTagSegmentOutLists.add(customTagSegmentOut);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("system_total", tagValueCountLists.size());
        map.put("system_total_count", systemTotalCount);
        map.put("system_tag", systemTagSegmentOutLists);
        map.put("custom_total", customTagLists.size());
        map.put("custom_total_count", customTotalCount);
        map.put("custom_tag", customTagSegmentOutLists);

        Assert.assertEquals(map.toString(), baseOutput.getData().get(0).toString());
    }

    @After
    public void tearDown() throws Exception {}



}
