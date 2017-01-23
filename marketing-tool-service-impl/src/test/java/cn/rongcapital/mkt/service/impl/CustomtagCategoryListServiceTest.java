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
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomtagCategoryListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.CustomTagCategoryOut;
@RunWith(MockitoJUnitRunner.class)
public class CustomtagCategoryListServiceTest {
    
    private CustomtagCategoryListService customtagCategoryListService;
    
    @Mock
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Mock
    private MongoCustomTagDao mongoCustomTagDao;
    
    private CustomTagCategory customTagCategory = new CustomTagCategory();
    
    private List<CustomTagCategory> customTagCategoryLists = new ArrayList<CustomTagCategory>();
    
    private long customTagCount = 10;

    @Before
    public void setUp() throws Exception {
        
        CustomTagCategory customTagCategoryOne = new CustomTagCategory();
        customTagCategoryOne.setCustomTagCategoryId("yr1oorLSpl1484637382955");
        customTagCategoryOne.setCustomTagCategoryName("未分类");
        customTagCategoryOne.setLevel(1);
        customTagCategoryLists.add(customTagCategoryOne);
        CustomTagCategory customTagCategoryTwo = new CustomTagCategory();
        customTagCategoryTwo.setCustomTagCategoryId("111");
        customTagCategoryTwo.setCustomTagCategoryName("这个");
        customTagCategoryTwo.setLevel(1);
        customTagCategoryLists.add(customTagCategoryTwo);
        
        
        Mockito.when(mongoCustomTagCategoryDao.find(customTagCategory)).thenReturn(customTagCategoryLists);
        Mockito.when(mongoCustomTagDao.countValidCustomTag(any())).thenReturn(customTagCount);
        
        customtagCategoryListService = new CustomtagCategoryListServiceImpl();
        
        ReflectionTestUtils.setField(customtagCategoryListService, "mongoCustomTagCategoryDao", mongoCustomTagCategoryDao);
        ReflectionTestUtils.setField(customtagCategoryListService, "mongoCustomTagDao", mongoCustomTagDao);
        
        
    }
    
    @Test
    public void testCustomtagCategoryListGet() {
        BaseOutput baseOutput = customtagCategoryListService.customtagCategoryListGet();
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(customTagCategoryLists.size(), baseOutput.getTotal());
        CustomTagCategoryOut customTagCategoryOut = (CustomTagCategoryOut)baseOutput.getData().get(1);
        Assert.assertEquals(customTagCategoryLists.get(1).getCustomTagCategoryId(),customTagCategoryOut.getCustomTagCategoryId());
        Assert.assertEquals(customTagCount,customTagCategoryOut.getCustomTagCount());
    }

    @After
    public void tearDown() throws Exception {}

}
