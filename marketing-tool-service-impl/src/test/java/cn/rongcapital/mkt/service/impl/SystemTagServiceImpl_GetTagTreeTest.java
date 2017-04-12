/*************************************************
 * @功能简述: SystemTagServiceImpl GetTagTree测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 1.0
 * @date: 2017/4/12
 *************************************************/
package cn.rongcapital.mkt.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.po.mongodb.TagTree;
import cn.rongcapital.mkt.service.TagSystemCommonUtilService;
import cn.rongcapital.mkt.vo.out.TagSystemTreeOut;
import cn.rongcapital.mkt.vo.out.TagSystemTreeTagOut;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SystemTagServiceImpl.class)
public class SystemTagServiceImpl_GetTagTreeTest {

    @Mock
    protected MongoTemplate mongoTemplate;

    @Mock
    private TagSystemCommonUtilService commonUtilService;

    @InjectMocks
    private SystemTagServiceImpl service;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testChangeTagIncludeCount01() {
        TagSystemTreeOut Leve2 = new TagSystemTreeOut();
        TagSystemTreeOut Leve1 = new TagSystemTreeOut();
        Leve1.setIncludeCount(6);
        Leve2.setParent(Leve1);
        ReflectionTestUtils.invokeMethod(service, "changeTagIncludeCount", Leve2, 4);
        Assert.assertEquals(Leve2.getIncludeCount().intValue(), 4);
        Assert.assertEquals(Leve1.getIncludeCount().intValue(), 10);
    }

    /**
     * 传入TagID对应的标签分类不存在
     */
    @Test
    public void testGetTagTree01() throws Exception {
        PowerMockito.when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(null);
        Object reslult = ReflectionTestUtils.invokeMethod(service, "getTagTree", "a", 0);
        Assert.assertNull(reslult);
    }

    /**
     * Normal
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testGetTagTree02() throws Exception {
        service = PowerMockito.spy(new SystemTagServiceImpl());
        ReflectionTestUtils.setField(service, "mongoTemplate", mongoTemplate);
        ReflectionTestUtils.setField(service, "commonUtilService", commonUtilService);

        TagTree parentTagTree = new TagTree();
        parentTagTree.setLevel(2);
        parentTagTree.setTagId("XXX");
        parentTagTree.setTagName("XXXX");

        PowerMockito.when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(parentTagTree);
        PowerMockito.doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Integer param0 = invocation.getArgumentAt(0, Integer.class);
                Assert.assertEquals(param0.intValue(), 0);

                TagSystemTreeOut out = invocation.getArgumentAt(1, TagSystemTreeOut.class);
                List<Object> list = new ArrayList<Object>();
                list.add("XXX");
                out.setChildren(list);
                Assert.assertEquals(out.getTagId(), "XXX");
                Assert.assertEquals(out.getTagName(), "XXXX");
                Assert.assertEquals(out.getLevel().intValue(), 2);

                TagTree tagTree = invocation.getArgumentAt(2, TagTree.class);
                Assert.assertEquals(parentTagTree, tagTree);
                return null;
            }

        }).when(service, "getTagTreeRecursion", Mockito.any(), Mockito.any(), Mockito.any());;
        Object reslult = ReflectionTestUtils.invokeMethod(service, "getTagTree", "a", 0);
        List<Object> cast = (List<Object>) reslult;
        Assert.assertEquals(cast.size(), 1);
        Assert.assertEquals(cast.get(0), "XXX");
    }

    /**
     * 叶子节点为空
     */
    @Test
    public void testGetTagTreeRecursion01() throws Exception {
        service = PowerMockito.spy(new SystemTagServiceImpl());
        ReflectionTestUtils.setField(service, "mongoTemplate", mongoTemplate);
        ReflectionTestUtils.setField(service, "commonUtilService", commonUtilService);


        TagTree parentTagTree = new TagTree();
        parentTagTree.setLevel(2);
        parentTagTree.setTagId("XXX");
        parentTagTree.setTagName("XXXX");

        TagSystemTreeOut parentTagTreeOut =
                new TagSystemTreeOut(parentTagTree.getTagId(), parentTagTree.getTagName(), parentTagTree.getLevel(),
                        new ArrayList<Object>());
        parentTagTreeOut.setIncludeCount(5);
        ReflectionTestUtils.invokeMethod(service, "getTagTreeRecursion", 0, parentTagTreeOut, parentTagTree);
        PowerMockito.verifyZeroInteractions(mongoTemplate);
        PowerMockito.verifyZeroInteractions(commonUtilService);
        Assert.assertEquals(parentTagTreeOut.getChildren().size(), 0);
        Assert.assertEquals(parentTagTreeOut.getIncludeCount().intValue(), 5);
    }

    /**
     * 叶子节点包含不存在标签
     */
    @Test
    public void testGetTagTreeRecursion02() throws Exception {
        service = PowerMockito.spy(new SystemTagServiceImpl());
        ReflectionTestUtils.setField(service, "mongoTemplate", mongoTemplate);
        ReflectionTestUtils.setField(service, "commonUtilService", commonUtilService);


        TagTree parentTagTree = new TagTree();
        parentTagTree.setLevel(2);
        parentTagTree.setTagId("XXX");
        parentTagTree.setTagName("XXXX");
        parentTagTree.setBottomClassification(1);
        List<String> children = new ArrayList<String>();
        children.add("tag_1");
        parentTagTree.setChildren(children);

        TagSystemTreeOut parentTagTreeOut =
                new TagSystemTreeOut(parentTagTree.getTagId(), parentTagTree.getTagName(), parentTagTree.getLevel(),
                        new ArrayList<Object>());


        PowerMockito.when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(null);

        PowerMockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(service, "changeTagIncludeCount", Mockito.any(), Mockito.anyInt());

        ReflectionTestUtils.invokeMethod(service, "getTagTreeRecursion", 1, parentTagTreeOut, parentTagTree);

        PowerMockito.verifyZeroInteractions(commonUtilService);
        Assert.assertEquals(parentTagTreeOut.getChildren().size(), 0);
    }

    /**
     * 叶子节点为标签 叶子节点为标签覆盖率为3.3 && page_source_type = 0
     */
    @Test
    public void testGetTagTreeRecursion02_1() throws Exception {
        service = PowerMockito.spy(new SystemTagServiceImpl());
        ReflectionTestUtils.setField(service, "mongoTemplate", mongoTemplate);
        ReflectionTestUtils.setField(service, "commonUtilService", commonUtilService);


        TagTree parentTagTree = new TagTree();
        parentTagTree.setLevel(2);
        parentTagTree.setTagId("XXX");
        parentTagTree.setTagName("XXXX");
        parentTagTree.setBottomClassification(1);
        List<String> children = new ArrayList<String>();
        children.add("tag_1");
        parentTagTree.setChildren(children);

        TagSystemTreeOut parentTagTreeOut =
                new TagSystemTreeOut(parentTagTree.getTagId(), parentTagTree.getTagName(), parentTagTree.getLevel(),
                        new ArrayList<Object>());


        TagRecommend tag = new TagRecommend();
        tag.setTagId("1234");
        tag.setTagName("12345");
        tag.setFlag(true);
        tag.setTagList(null);
        PowerMockito.when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(tag);

        PowerMockito.when(commonUtilService.getTagCover(Mockito.anyString())).thenReturn("3.3");

        PowerMockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                TagSystemTreeOut out = invocation.getArgumentAt(0, TagSystemTreeOut.class);
                out.setIncludeCount(5);
                Assert.assertEquals(invocation.getArgumentAt(1, Integer.class).intValue(), 1);
                return null;
            }
        }).when(service, "changeTagIncludeCount", Mockito.any(), Mockito.anyInt());

        ReflectionTestUtils.invokeMethod(service, "getTagTreeRecursion", 0, parentTagTreeOut, parentTagTree);

        Assert.assertEquals(parentTagTreeOut.getChildren().size(), 1);
        Assert.assertTrue(parentTagTreeOut.getChildren().get(0) instanceof TagSystemTreeTagOut);

        TagSystemTreeTagOut tagOut = (TagSystemTreeTagOut) parentTagTreeOut.getChildren().get(0);
        Assert.assertEquals(tagOut.getTagId(), "1234");
        Assert.assertEquals(tagOut.getTagName(), "12345");
        Assert.assertEquals(tagOut.getFlag(), true);
        Assert.assertNull(tagOut.getTagList());
        Assert.assertEquals(tagOut.getTagCover(), "3.3");
        Assert.assertEquals(parentTagTreeOut.getIncludeCount().intValue(), 5);
    }

    /**
     * 叶子节点为标签覆盖率为0 && page_source_type = 1
     */
    @Test
    public void testGetTagTreeRecursion02_2() throws Exception {
        service = PowerMockito.spy(new SystemTagServiceImpl());
        ReflectionTestUtils.setField(service, "mongoTemplate", mongoTemplate);
        ReflectionTestUtils.setField(service, "commonUtilService", commonUtilService);


        TagTree parentTagTree = new TagTree();
        parentTagTree.setLevel(2);
        parentTagTree.setTagId("XXX");
        parentTagTree.setTagName("XXXX");
        parentTagTree.setBottomClassification(1);
        List<String> children = new ArrayList<String>();
        children.add("tag_1");
        parentTagTree.setChildren(children);

        TagSystemTreeOut parentTagTreeOut =
                new TagSystemTreeOut(parentTagTree.getTagId(), parentTagTree.getTagName(), parentTagTree.getLevel(),
                        new ArrayList<Object>());

        TagRecommend tag = new TagRecommend();
        tag.setTagId("1234");
        tag.setTagName("12345");
        tag.setFlag(true);
        tag.setTagList(null);
        PowerMockito.when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(tag);

        PowerMockito.when(commonUtilService.getTagCover(Mockito.anyString())).thenReturn("0.00%");

        PowerMockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(service, "changeTagIncludeCount", Mockito.any(), Mockito.anyInt());

        ReflectionTestUtils.invokeMethod(service, "getTagTreeRecursion", 1, parentTagTreeOut, parentTagTree);

        Assert.assertEquals(parentTagTreeOut.getChildren().size(), 0);
    }

    /**
     * 叶子节点为标签覆盖率为0 && page_source_type = NULL
     */
    @Test
    public void testGetTagTreeRecursion02_3() throws Exception {
        service = PowerMockito.spy(new SystemTagServiceImpl());
        ReflectionTestUtils.setField(service, "mongoTemplate", mongoTemplate);
        ReflectionTestUtils.setField(service, "commonUtilService", commonUtilService);


        TagTree parentTagTree = new TagTree();
        parentTagTree.setLevel(2);
        parentTagTree.setTagId("XXX");
        parentTagTree.setTagName("XXXX");
        parentTagTree.setBottomClassification(1);
        List<String> children = new ArrayList<String>();
        children.add("tag_1");
        parentTagTree.setChildren(children);

        TagSystemTreeOut parentTagTreeOut =
                new TagSystemTreeOut(parentTagTree.getTagId(), parentTagTree.getTagName(), parentTagTree.getLevel(),
                        new ArrayList<Object>());


        TagRecommend tag = new TagRecommend();
        tag.setTagId("1234");
        tag.setTagName("12345");
        tag.setFlag(true);
        tag.setTagList(null);
        PowerMockito.when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(tag);

        PowerMockito.when(commonUtilService.getTagCover(Mockito.anyString())).thenReturn("0.00%");

        PowerMockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                TagSystemTreeOut out = invocation.getArgumentAt(0, TagSystemTreeOut.class);
                out.setIncludeCount(5);
                Assert.assertEquals(invocation.getArgumentAt(1, Integer.class).intValue(), 1);
                return null;
            }
        }).when(service, "changeTagIncludeCount", Mockito.any(), Mockito.anyInt());

        ReflectionTestUtils.invokeMethod(service, "getTagTreeRecursion", null, parentTagTreeOut, parentTagTree);

        Assert.assertEquals(parentTagTreeOut.getChildren().size(), 1);
        Assert.assertTrue(parentTagTreeOut.getChildren().get(0) instanceof TagSystemTreeTagOut);

        TagSystemTreeTagOut tagOut = (TagSystemTreeTagOut) parentTagTreeOut.getChildren().get(0);
        Assert.assertEquals(tagOut.getTagId(), "1234");
        Assert.assertEquals(tagOut.getTagName(), "12345");
        Assert.assertEquals(tagOut.getFlag(), true);
        Assert.assertNull(tagOut.getTagList());
        Assert.assertEquals(tagOut.getTagCover(), "0.00%");
        Assert.assertEquals(parentTagTreeOut.getIncludeCount().intValue(), 5);
    }

    /**
     * 叶子节点为标签覆盖率为0 && page_source_type = 0
     */
    @Test
    public void testGetTagTreeRecursion02_4() throws Exception {
        service = PowerMockito.spy(new SystemTagServiceImpl());
        ReflectionTestUtils.setField(service, "mongoTemplate", mongoTemplate);
        ReflectionTestUtils.setField(service, "commonUtilService", commonUtilService);


        TagTree parentTagTree = new TagTree();
        parentTagTree.setLevel(2);
        parentTagTree.setTagId("XXX");
        parentTagTree.setTagName("XXXX");
        parentTagTree.setBottomClassification(1);
        List<String> children = new ArrayList<String>();
        children.add("tag_1");
        parentTagTree.setChildren(children);

        TagSystemTreeOut parentTagTreeOut =
                new TagSystemTreeOut(parentTagTree.getTagId(), parentTagTree.getTagName(), parentTagTree.getLevel(),
                        new ArrayList<Object>());


        TagRecommend tag = new TagRecommend();
        tag.setTagId("1234");
        tag.setTagName("12345");
        tag.setFlag(true);
        tag.setTagList(null);
        PowerMockito.when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(tag);

        PowerMockito.when(commonUtilService.getTagCover(Mockito.anyString())).thenReturn("0.00%");

        PowerMockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                TagSystemTreeOut out = invocation.getArgumentAt(0, TagSystemTreeOut.class);
                out.setIncludeCount(5);
                Assert.assertEquals(invocation.getArgumentAt(1, Integer.class).intValue(), 1);
                return null;
            }
        }).when(service, "changeTagIncludeCount", Mockito.any(), Mockito.anyInt());

        ReflectionTestUtils.invokeMethod(service, "getTagTreeRecursion", 0, parentTagTreeOut, parentTagTree);

        Assert.assertEquals(parentTagTreeOut.getChildren().size(), 1);
        Assert.assertTrue(parentTagTreeOut.getChildren().get(0) instanceof TagSystemTreeTagOut);

        TagSystemTreeTagOut tagOut = (TagSystemTreeTagOut) parentTagTreeOut.getChildren().get(0);
        Assert.assertEquals(tagOut.getTagId(), "1234");
        Assert.assertEquals(tagOut.getTagName(), "12345");
        Assert.assertEquals(tagOut.getFlag(), true);
        Assert.assertNull(tagOut.getTagList());
        Assert.assertEquals(tagOut.getTagCover(), "0.00%");
        Assert.assertEquals(parentTagTreeOut.getIncludeCount().intValue(), 5);
    }

    /**
     * 叶子节点为分类
     */
    @Test
    public void testGetTagTreeRecursion03() throws Exception {
        service = PowerMockito.spy(new SystemTagServiceImpl());
        ReflectionTestUtils.setField(service, "mongoTemplate", mongoTemplate);
        ReflectionTestUtils.setField(service, "commonUtilService", commonUtilService);

        TagTree parentTagTree = new TagTree();
        parentTagTree.setLevel(2);
        parentTagTree.setTagId("XXX");
        parentTagTree.setTagName("XXXX");
        parentTagTree.setBottomClassification(0);
        List<String> children = new ArrayList<String>();
        children.add("tag_class1");
        parentTagTree.setChildren(children);

        TagSystemTreeOut parentTagTreeOut =
                new TagSystemTreeOut(parentTagTree.getTagId(), parentTagTree.getTagName(), parentTagTree.getLevel(),
                        new ArrayList<Object>());

        TagTree tagTree = new TagTree();
        tagTree.setTagId("tag_class1");
        tagTree.setTagName("12345");
        tagTree.setLevel(3);
        PowerMockito.when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(tagTree);


        PowerMockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(service, "changeTagIncludeCount", Mockito.any(), Mockito.anyInt());

        ReflectionTestUtils.invokeMethod(service, "getTagTreeRecursion", 0, parentTagTreeOut, parentTagTree);
        PowerMockito.verifyZeroInteractions(commonUtilService);


        Assert.assertEquals(parentTagTreeOut.getChildren().size(), 1);
        Assert.assertTrue(parentTagTreeOut.getChildren().get(0) instanceof TagSystemTreeOut);

        TagSystemTreeOut tagOut = (TagSystemTreeOut) parentTagTreeOut.getChildren().get(0);
        Assert.assertEquals(tagOut.getTagId(), "tag_class1");
        Assert.assertEquals(tagOut.getTagName(), "12345");
        Assert.assertEquals(tagOut.getLevel().intValue(), 3);
        Assert.assertTrue(tagOut.getChildren().size() == 0);

        Assert.assertEquals(tagOut.getParent(), parentTagTreeOut);
    }

    /**
     * 叶子节点为分类包含不存在分类
     */
    @Test
    public void testGetTagTreeRecursion03_1() throws Exception {
        service = PowerMockito.spy(new SystemTagServiceImpl());
        ReflectionTestUtils.setField(service, "mongoTemplate", mongoTemplate);
        ReflectionTestUtils.setField(service, "commonUtilService", commonUtilService);


        TagTree parentTagTree = new TagTree();
        parentTagTree.setLevel(2);
        parentTagTree.setTagId("XXX");
        parentTagTree.setTagName("XXXX");
        parentTagTree.setBottomClassification(0);
        List<String> children = new ArrayList<String>();
        children.add("tag_class1");
        parentTagTree.setChildren(children);

        TagSystemTreeOut parentTagTreeOut =
                new TagSystemTreeOut(parentTagTree.getTagId(), parentTagTree.getTagName(), parentTagTree.getLevel(),
                        new ArrayList<Object>());


        PowerMockito.when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(null);


        PowerMockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(service, "changeTagIncludeCount", Mockito.any(), Mockito.anyInt());

        ReflectionTestUtils.invokeMethod(service, "getTagTreeRecursion", 0, parentTagTreeOut, parentTagTree);
        PowerMockito.verifyZeroInteractions(commonUtilService);
        Assert.assertEquals(parentTagTreeOut.getChildren().size(), 0);
    }


    /**
     * 递归
     * 
     * 用户分类0 ] ] --用户分类1 ----------tag1_1 ----------tag1_2
     * 
     * --用户分类2 ----------用户分类2_1 --------------------tag2_1_1 ----------用户分类2_2
     * --------------------tag2_2_1
     */
    @Test
    public void testGetTagTreeRecursion04() throws Exception {
        service = PowerMockito.spy(new SystemTagServiceImpl());
        ReflectionTestUtils.setField(service, "mongoTemplate", mongoTemplate);
        ReflectionTestUtils.setField(service, "commonUtilService", commonUtilService);

        TagTree parentTagTree = new TagTree();
        parentTagTree.setLevel(1);
        parentTagTree.setTagId("用户分类0");
        parentTagTree.setTagName("用户分类0");
        parentTagTree.setBottomClassification(0);
        List<String> children = new ArrayList<String>();
        children.add("用户分类1");
        children.add("用户分类2");
        parentTagTree.setChildren(children);

        TagSystemTreeOut parentTagTreeOut =
                new TagSystemTreeOut(parentTagTree.getTagId(), parentTagTree.getTagName(), parentTagTree.getLevel(),
                        new ArrayList<Object>());



        TagTree tagTree = new TagTree();
        tagTree.setTagId("用户分类1");
        tagTree.setTagName("用户分类1");
        tagTree.setBottomClassification(1);
        tagTree.setLevel(2);
        List<String> children1 = new ArrayList<String>();
        children1.add("tag1_1");
        children1.add("tag1_2");
        tagTree.setChildren(children1);

        Query q1 = new Query(Criteria.where("tag_id").is("用户分类1").and("status").is(ApiConstant.INT_ZERO));
        PowerMockito.when(mongoTemplate.findOne(q1, TagTree.class)).thenReturn(tagTree);


        Query q1_1 = new Query(Criteria.where("tag_id").is("tag1_1").and("status").is(ApiConstant.INT_ZERO));
        TagRecommend tag_1 = new TagRecommend();
        tag_1.setTagId("tag1_1");
        tag_1.setTagName("tag1_1");
        PowerMockito.when(mongoTemplate.findOne(q1_1, TagRecommend.class)).thenReturn(tag_1);
        PowerMockito.when(commonUtilService.getTagCover("tag1_1")).thenReturn("1.00%");

        Query q1_2 = new Query(Criteria.where("tag_id").is("tag1_2").and("status").is(ApiConstant.INT_ZERO));
        TagRecommend tag_2 = new TagRecommend();
        tag_2.setTagId("tag1_2");
        tag_2.setTagName("tag1_2");
        PowerMockito.when(mongoTemplate.findOne(q1_2, TagRecommend.class)).thenReturn(tag_2);
        PowerMockito.when(commonUtilService.getTagCover("tag1_2")).thenReturn("1.00%");



        TagTree tagTree2 = new TagTree();
        tagTree2.setTagId("用户分类2");
        tagTree2.setTagName("用户分类2");
        tagTree2.setBottomClassification(0);
        tagTree2.setLevel(2);
        List<String> children3 = new ArrayList<String>();
        children3.add("用户分类2_1");
        children3.add("用户分类2_2");
        tagTree2.setChildren(children3);

        Query q2 = new Query(Criteria.where("tag_id").is("用户分类2").and("status").is(ApiConstant.INT_ZERO));
        PowerMockito.when(mongoTemplate.findOne(q2, TagTree.class)).thenReturn(tagTree2);



        TagTree tagTree3 = new TagTree();
        tagTree3.setTagId("用户分类2_1");
        tagTree3.setTagName("用户分类2_1");
        tagTree3.setBottomClassification(1);
        tagTree3.setLevel(3);
        List<String> children4 = new ArrayList<String>();
        children4.add("tag2_1_1");
        tagTree3.setChildren(children4);

        Query q3 = new Query(Criteria.where("tag_id").is("用户分类2_1").and("status").is(ApiConstant.INT_ZERO));
        PowerMockito.when(mongoTemplate.findOne(q3, TagTree.class)).thenReturn(tagTree3);

        Query q3_1 = new Query(Criteria.where("tag_id").is("tag2_1_1").and("status").is(ApiConstant.INT_ZERO));
        TagRecommend tag_3_1 = new TagRecommend();
        tag_3_1.setTagId("tag2_1_1");
        tag_3_1.setTagName("tag2_1_1");
        PowerMockito.when(mongoTemplate.findOne(q3_1, TagRecommend.class)).thenReturn(tag_3_1);
        PowerMockito.when(commonUtilService.getTagCover("tag2_1_1")).thenReturn("1.00%");

        TagTree tagTree4 = new TagTree();
        tagTree4.setTagId("用户分类2_2");
        tagTree4.setTagName("用户分类2_2");
        tagTree4.setBottomClassification(1);
        tagTree4.setLevel(3);
        List<String> children5 = new ArrayList<String>();
        children5.add("tag2_2_1");
        tagTree4.setChildren(children5);

        Query q4 = new Query(Criteria.where("tag_id").is("用户分类2_2").and("status").is(ApiConstant.INT_ZERO));
        PowerMockito.when(mongoTemplate.findOne(q4, TagTree.class)).thenReturn(tagTree4);


        Query q4_1 = new Query(Criteria.where("tag_id").is("tag2_2_1").and("status").is(ApiConstant.INT_ZERO));
        TagRecommend tag_4_1 = new TagRecommend();
        tag_4_1.setTagId("tag2_2_1");
        tag_4_1.setTagName("tag2_2_1");
        PowerMockito.when(mongoTemplate.findOne(q4_1, TagRecommend.class)).thenReturn(tag_4_1);

        PowerMockito.when(commonUtilService.getTagCover("tag2_2_1")).thenReturn("1.00%");
        ReflectionTestUtils.invokeMethod(service, "getTagTreeRecursion", 0, parentTagTreeOut, parentTagTree);
        Assert.assertEquals(
                parentTagTreeOut.getChildren().toString(),
                "[TagSystemTreeOut [tagId=用户分类1, tagName=用户分类1, level=2, children=[TagSystemTreeTagOut [tagId=tag1_1, tagName=tag1_1, flag=null, tagNameEng=null, searchMod=null, tagList=null, tagCover=1.00%, tagDesc=null], TagSystemTreeTagOut [tagId=tag1_2, tagName=tag1_2, flag=null, tagNameEng=null, searchMod=null, tagList=null, tagCover=1.00%, tagDesc=null]]], TagSystemTreeOut [tagId=用户分类2, tagName=用户分类2, level=2, children=[TagSystemTreeOut [tagId=用户分类2_1, tagName=用户分类2_1, level=3, children=[TagSystemTreeTagOut [tagId=tag2_1_1, tagName=tag2_1_1, flag=null, tagNameEng=null, searchMod=null, tagList=null, tagCover=1.00%, tagDesc=null]]], TagSystemTreeOut [tagId=用户分类2_2, tagName=用户分类2_2, level=3, children=[TagSystemTreeTagOut [tagId=tag2_2_1, tagName=tag2_2_1, flag=null, tagNameEng=null, searchMod=null, tagList=null, tagCover=1.00%, tagDesc=null]]]]]]");
    }

}
