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
import cn.rongcapital.mkt.service.TagCampaignFuzzyListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.CustomTagCampaignOut;
import cn.rongcapital.mkt.vo.out.SystemTagCampaignOut;

@RunWith(MockitoJUnitRunner.class)
public class TagCampaignFuzzyListServiceTest {

    private TagCampaignFuzzyListService tagCampaignFuzzyListService;

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
                "太阳系>地球>", "0", Integer.valueOf(0));
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

        tagCampaignFuzzyListService = new TagCampaignFuzzyListServiceImpl();

        ReflectionTestUtils.setField(tagCampaignFuzzyListService, "tagValueCountDao", tagValueCountDao);
        ReflectionTestUtils.setField(tagCampaignFuzzyListService, "mongoCustomTagCategoryDao",
                mongoCustomTagCategoryDao);
        ReflectionTestUtils.setField(tagCampaignFuzzyListService, "mongoCustomTagDao", mongoCustomTagDao);
    }

    @Test
    public void testTagCampaignFuzzyListGet() {
        BaseOutput baseOutput = tagCampaignFuzzyListService.tagCampaignFuzzyListGet("测试");

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());


        List<SystemTagCampaignOut> systemTagCampaignOutLists = new ArrayList<SystemTagCampaignOut>();
        for (TagValueCount tagValueCountList : tagValueCountLists) {
            SystemTagCampaignOut systemTagCampaignOut = new SystemTagCampaignOut(tagValueCountList.getTagId(),
                    tagValueCountList.getTagName(), tagValueCountList.getTagValue(), tagValueCountList.getTagPath(),
                    tagValueCountList.getTagValueSeq());
            systemTagCampaignOutLists.add(systemTagCampaignOut);
        }

        List<CustomTagCampaignOut> customTagCampaignOutLists = new ArrayList<CustomTagCampaignOut>();
        for (CustomTag customTagList : customTagLists) {
            CustomTagCampaignOut customTagCampaignOut = new CustomTagCampaignOut(customTagList.getCustomTagId(),
                    customTagList.getCustomTagName(), customTagCategory.getCustomTagCategoryName() + ">",
                    customTagCategory.getCustomTagCategoryId(), customTagCategory.getCustomTagCategoryName());
            customTagCampaignOutLists.add(customTagCampaignOut);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("system_total", tagValueCountLists.size());
        map.put("system_total_count", systemTotalCount);
        map.put("system_tag", systemTagCampaignOutLists);
        map.put("custom_total", customTagLists.size());
        map.put("custom_total_count", customTotalCount);
        map.put("custom_tag", customTagCampaignOutLists);

        Assert.assertEquals(map.toString(), baseOutput.getData().get(0).toString());
    }

    @After
    public void tearDown() throws Exception {}



}
