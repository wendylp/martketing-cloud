/*************************************************
 * @功能简述: Service实现测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SegmentCustomAnalysisDataOut;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;

@RunWith(MockitoJUnitRunner.class)
public class SegmentAnalysisCustomServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private SegmentAnalysisCustomService segmentAnalysisCustomService;
    @Mock
	private SegmentManageCalService segmentManageCalService;
    @Mock
	private CustomTagActionService customTagActionService;
	
	private List<CustomTag> customList = new ArrayList<CustomTag>();
	
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：SegmentAnalysisCustomService 准备---------------------");
        
        CustomTag customTag1 = new CustomTag();
        
        customTag1.setCustomTagId("123");
        customTag1.setCustomTagName("123name");
        customTag1.setCoverFrequency(150);
        customList.add(customTag1);
        
        CustomTag customTag2 = new CustomTag();
        
        customTag2.setCustomTagId("1232");
        customTag2.setCustomTagName("1232name");
        customTag2.setCoverFrequency(135);
        customList.add(customTag2);
        
        CustomTag customTag3 = new CustomTag();
        
        customTag3.setCustomTagId("1233");
        customTag3.setCustomTagName("1233name");
        customTag3.setCoverFrequency(13);
        customList.add(customTag3);
        
    	Mockito.when(segmentManageCalService.scard(any(),any())).thenReturn(10l);

    	Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return null;
			}
    		
    	}).when(segmentManageCalService).sinterstore(any(), any(), any());
    	
    	Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return null;
			}
    		
    	}).when(segmentManageCalService).sunionstore(any(), any(), any());
    	
    	Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return null;
			}
    		
    	}).when(segmentManageCalService).deleteTempKey(any(), any());
        
   
    	Mockito.when(customTagActionService.findCustomTagTopList(any())).thenReturn(customList);
    	Mockito.when(customTagActionService.findCustomTagsByCategoryId(any())).thenReturn(customList);
    	
    	segmentAnalysisCustomService = new SegmentAnalysisCustomServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(segmentAnalysisCustomService, "segmentManageCalService", segmentManageCalService);
        ReflectionTestUtils.setField(segmentAnalysisCustomService, "customTagActionService", customTagActionService);
    }
    
    @Test
    public void testGetSegmentAudienctAnalysis() {
        logger.info("测试方法: getSegmentAudienctAnalysis ");
        
        BaseOutput out = segmentAnalysisCustomService.getSegmentAnalysisTopCustomList(1);
     
        List<Object> objList = out.getData();
        
        Assert.assertEquals(3, objList.size());
        
    }
    
    @Test
    public void testGetSegmentAnalysisTopCustomList() {
        logger.info("测试方法: getSegmentAnalysisTopCustomList ");
        
        BaseOutput out = segmentAnalysisCustomService.getSegmentCustomAnalysis("456", 193);
     
        SegmentCustomAnalysisDataOut segmentCustomAnalysisDataOut = (SegmentCustomAnalysisDataOut)out.getData().get(0);
        
        Assert.assertEquals(3, segmentCustomAnalysisDataOut.getPopulationCount().size());
        
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：SegmentAnalysisCustomService 结束---------------------");
    }

}


