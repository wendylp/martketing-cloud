package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.service.SegmentCalcService;
import cn.rongcapital.mkt.vo.in.SegmentCreUpdateIn;
import cn.rongcapital.mkt.vo.in.SystemTagIn;
import cn.rongcapital.mkt.vo.in.SystemValueIn;
import cn.rongcapital.mkt.vo.in.TagGroupsIn;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Created by byf on 11/21/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreupdateSegmentServiceImplTest {

    @Mock
    private SegmentationHeadDao segmentationHeadDao;

    @Mock
    private SegmentationBodyDao segmentationBodyDao;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private SegmentCalcService segmentCalcService;

    private CreupdateSegmentServiceImpl segmentService = new CreupdateSegmentServiceImpl();
    private SegmentCreUpdateIn notContainFilterGroupIn;
    private SegmentCreUpdateIn notContainTagListIn;
    private SegmentCreUpdateIn notContainTagValueIn;

    @Before
    public void setUp() throws Exception{
        //1.MockServiceImpl
        ReflectionTestUtils.setField(segmentService,"segmentationHeadDao",segmentationHeadDao);
        ReflectionTestUtils.setField(segmentService,"segmentationBodyDao",segmentationBodyDao);
        ReflectionTestUtils.setField(segmentService,"mongoTemplate",mongoTemplate);
        ReflectionTestUtils.setField(segmentService,"segmentCalcService",segmentCalcService);

        //2.Mock不含标签组类型的传入参数
        notContainFilterGroupIn = new SegmentCreUpdateIn();
        notContainFilterGroupIn.setSegmentName("细分创建测试-不含标签组");
        notContainFilterGroupIn.setPublishStatus(Integer.valueOf(ApiConstant.SEGMENT_PUBLISH_STATUS_PUBLISH));

        //3.Mock包含空标签列表的传入参数
        notContainTagListIn = new SegmentCreUpdateIn();
        notContainTagListIn.setSegmentName("细分创建测试-标签组中包含空标签列表");
        notContainTagListIn.setPublishStatus(Integer.valueOf(ApiConstant.SEGMENT_PUBLISH_STATUS_NOT_PUBLISH));

        TagGroupsIn tagGroupsIn = new TagGroupsIn();
        tagGroupsIn.setGroupId("group1");
        tagGroupsIn.setGroupChange(1);
        tagGroupsIn.setGroupName("标签组-1");
        tagGroupsIn.setGroupIndex(1);

        SystemTagIn systemTagIn = new SystemTagIn();
        systemTagIn.setTagId("标签ID-01");
        systemTagIn.setTagName("标签1");
        systemTagIn.setTagIndex(1);
        systemTagIn.setTagExclude(1);

        SystemValueIn systemValueIn = new SystemValueIn();
        systemValueIn.setTagValueId("标签ID-01-值01");
        systemValueIn.setTagValue("是");
        systemTagIn.getTagValueList().add(systemValueIn);
        tagGroupsIn.getTagList().add(systemTagIn);

        systemTagIn = new SystemTagIn();
        systemTagIn.setTagId("标签ID-02");
        systemTagIn.setTagName("");

        notContainFilterGroupIn.getFilterGroups().add(tagGroupsIn);
        //4.Mock包含标签列表值为空的传入参数
        notContainTagValueIn = new SegmentCreUpdateIn();
    }

    @Test
    public void creupdateSegment() throws Exception {

    }

    @After
    public void tearDown() throws Exception{

    }
}