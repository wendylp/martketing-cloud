package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.service.CreateCustomTagToCategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by hiro on 17/2/14.
 */

@RunWith(MockitoJUnitRunner.class)
public class CreateCustomTagToCategoryServiceImplTest {

    private CreateCustomTagToCategoryService createCustomTagToCategoryService;

    @Mock
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Mock
    private MongoCustomTagDao mongoCustomTagDao;

    @Before
    public void setUp() throws Exception{

    }


    @Test
    public void createCustomTagToCategory() throws Exception {

    }

}