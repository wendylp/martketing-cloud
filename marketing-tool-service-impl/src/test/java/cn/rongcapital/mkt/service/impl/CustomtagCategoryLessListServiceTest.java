package cn.rongcapital.mkt.service.impl;

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

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomtagCategoryLessListService;
import cn.rongcapital.mkt.vo.BaseOutput;
@RunWith(MockitoJUnitRunner.class)
public class CustomtagCategoryLessListServiceTest {
    
    private CustomtagCategoryLessListService customtagCategoryLessListService;
    
    private CustomTagCategory customTagCategory = new CustomTagCategory();
    
    private List<CustomTagCategory> customTagCategoryLists = new ArrayList<CustomTagCategory>();
    
    @Mock
    MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

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
        
        customtagCategoryLessListService = new CustomtagCategoryLessListServiceImpl(); 
        
        ReflectionTestUtils.setField(customtagCategoryLessListService, "mongoCustomTagCategoryDao", mongoCustomTagCategoryDao);
        
    }
    
    @Test
    public void testCustomtagCategoryLessListGet() {
        BaseOutput baseOutput = customtagCategoryLessListService.customtagCategoryLessListGet();
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(customTagCategoryLists.size()-1, baseOutput.getTotal());
        
        @SuppressWarnings("unchecked")
        Map<String,Object> map = (Map<String,Object>)baseOutput.getData().get(0);
        Assert.assertEquals(customTagCategoryLists.get(1).getCustomTagCategoryId(), map.get("custom_tag_category_id"));
    }

    @After
    public void tearDown() throws Exception {}



}
