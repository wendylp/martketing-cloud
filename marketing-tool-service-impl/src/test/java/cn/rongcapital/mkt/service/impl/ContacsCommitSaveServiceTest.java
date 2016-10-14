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


import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.dao.mongo.MongoBaseTagDaoImpl;
import cn.rongcapital.mkt.service.*;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(MockitoJUnitRunner.class)
public class ContacsCommitSaveServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private ContacsCommitSaveService contacsCommitSaveService;
    
    
    @Mock
    private ContactListDao contactListDao;
    
    @Mock
    private ContactTemplateDao contactTemplateDao;
    
    @Mock
    private DefaultContactTemplateDao defaultContactTemplateDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：ContacsCommitSaveService 准备---------------------");
        
        
        contacsCommitSaveService = new ContactsCommitSaveServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(contacsCommitSaveService, "contactDao", contactListDao);
        ReflectionTestUtils.setField(contacsCommitSaveService, "contactTemplateDao", contactTemplateDao);
        ReflectionTestUtils.setField(contacsCommitSaveService, "defaultContactTemplateDao", defaultContactTemplateDao);
        
    }
    
    @Test
    public void testContactsCommitDel() {
        logger.info("测试方法: contactsCommitDel ");
    }
    
    @Test
    public void testContactsCommitDownload() {
        logger.info("测试方法: contactsCommitDownload ");
    }
    
    @Test
    public void testContactsCommitSave() {
        logger.info("测试方法: contactsCommitSave ");
    }
    
    @Test
    public void testTransferNameListtoMap() {
        logger.info("测试方法: transferNameListtoMap ");
    }
    
    @Test
    public void testContactsCommitGet() {
        logger.info("测试方法: contactsCommitGet ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：ContacsCommitSaveService 结束---------------------");
    }

}


