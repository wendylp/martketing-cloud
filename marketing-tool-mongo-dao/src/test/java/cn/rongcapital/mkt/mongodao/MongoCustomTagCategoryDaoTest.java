package cn.rongcapital.mkt.mongodao;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;


@RunWith(SpringJUnit4ClassRunner.class)
public class MongoCustomTagCategoryDaoTest extends AbstractUnitTest {

    @Autowired
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    private List<CustomTagCategory> customTagCategoryLists = new ArrayList<CustomTagCategory>();

    private List<CustomTag> customTagLists = new ArrayList<CustomTag>();

    @Before
    public void setUp() throws Exception {

        CustomTag customTagOne = new CustomTag("CustomTagA0000000000001", "自定义标签1", 0, 100, 1000);
        CustomTag customTagTwo = new CustomTag("CustomTagA0000000000002", "自定义标签2", 0, 200, 2000);
        CustomTag customTagThree = new CustomTag("CustomTagA0000000000003", "自定义标签3", 0, 300, 3000);
        CustomTag customTagFour = new CustomTag("CustomTagA0000000000004", "自定义标签4", 1, 400, 4000);

        customTagLists.add(customTagOne);
        customTagLists.add(customTagTwo);
        customTagLists.add(customTagThree);
        customTagLists.add(customTagFour);

        List<String> customTagIdListOne = new ArrayList<String>();
        customTagIdListOne.add(customTagOne.getCustomTagId());

        List<String> customTagIdListTwo = new ArrayList<String>();
        customTagIdListTwo.add(customTagTwo.getCustomTagId());
        customTagIdListTwo.add(customTagFour.getCustomTagId());

        CustomTagCategory customTagCategoryOne =
                new CustomTagCategory(ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID, "未分类", 0, customTagIdListOne);
        CustomTagCategory customTagCategoryTwo =
                new CustomTagCategory("CategoryAA0000000000002", "自定义分类2", 0, customTagIdListTwo);
        CustomTagCategory customTagCategoryThree = new CustomTagCategory("CategoryAA0000000000003", "自定义分类3", 1, null);

        customTagCategoryLists.add(customTagCategoryOne);
        customTagCategoryLists.add(customTagCategoryTwo);
        customTagCategoryLists.add(customTagCategoryThree);


        mongoTemplate.insertAll(customTagLists);
        mongoTemplate.insertAll(customTagCategoryLists);


    }

    @Test
    public void testFindOneCustomTagCategory() {

        CustomTagCategory customTagCategorySelect = null;
        CustomTagCategory customTagCategoryResult = mongoCustomTagCategoryDao.findOne(customTagCategorySelect);
        Assert.assertNull(customTagCategoryResult);

        CustomTagCategory customTagCategory = customTagCategoryLists.get(0);
        customTagCategorySelect = new CustomTagCategory();
        customTagCategorySelect.setCustomTagCategoryId(customTagCategory.getCustomTagCategoryId());

        customTagCategoryResult = mongoCustomTagCategoryDao.findOne(customTagCategorySelect);

        Assert.assertEquals(customTagCategory.getCustomTagCategoryId(),
                customTagCategoryResult.getCustomTagCategoryId());
        Assert.assertEquals(customTagCategory.getCustomTagCategoryName(),
                customTagCategoryResult.getCustomTagCategoryName());
    }

    @Test
    public void testFindOneQuery() {
        Query query = null;
        CustomTagCategory customTagCategoryResult = mongoCustomTagCategoryDao.findOne(query);
        Assert.assertNull(customTagCategoryResult);


        CustomTagCategory customTagCategory = customTagCategoryLists.get(0);
        query = new Query(Criteria.where("custom_tag_category_id").is(customTagCategory.getCustomTagCategoryId()));
        customTagCategoryResult = mongoCustomTagCategoryDao.findOne(query);
        Assert.assertEquals(customTagCategory.getCustomTagCategoryId(),
                customTagCategoryResult.getCustomTagCategoryId());
        Assert.assertEquals(customTagCategory.getCustomTagCategoryName(),
                customTagCategoryResult.getCustomTagCategoryName());
    }

    @Test
    public void testFindCustomTagCategory() {

        CustomTagCategory customTagCategorySelect = null;
        List<CustomTagCategory> customTagCategoryResultLists = mongoCustomTagCategoryDao.find(customTagCategorySelect);
        Assert.assertNull(customTagCategoryResultLists);


        CustomTagCategory customTagCategory = customTagCategoryLists.get(0);
        customTagCategorySelect = new CustomTagCategory();
        customTagCategorySelect.setCustomTagCategoryId(customTagCategory.getCustomTagCategoryId());
        customTagCategoryResultLists = mongoCustomTagCategoryDao.find(customTagCategorySelect);

        Assert.assertEquals(1, customTagCategoryResultLists.size());
        Assert.assertEquals(customTagCategory.getCustomTagCategoryId(),
                customTagCategoryResultLists.get(0).getCustomTagCategoryId());
        Assert.assertEquals(customTagCategory.getCustomTagCategoryName(),
                customTagCategoryResultLists.get(0).getCustomTagCategoryName());

    }

    @Test
    public void testFindQuery() {

        Query query = null;
        List<CustomTagCategory> customTagCategoryResultLists = mongoCustomTagCategoryDao.find(query);
        Assert.assertNull(customTagCategoryResultLists);


        CustomTagCategory customTagCategory = customTagCategoryLists.get(0);
        query = new Query(Criteria.where("custom_tag_category_id").is(customTagCategory.getCustomTagCategoryId()));
        customTagCategoryResultLists = mongoCustomTagCategoryDao.find(query);
        Assert.assertEquals(1, customTagCategoryResultLists.size());
        Assert.assertEquals(customTagCategory.getCustomTagCategoryId(),
                customTagCategoryResultLists.get(0).getCustomTagCategoryId());
        Assert.assertEquals(customTagCategory.getCustomTagCategoryName(),
                customTagCategoryResultLists.get(0).getCustomTagCategoryName());

    }

    @Test
    public void testInsertMongoCustomTagCategory() {

        CustomTagCategory customTagCategory = new CustomTagCategory("newCategoryAA0000000000000", "新插入的分类", 0, null);
        mongoCustomTagCategoryDao.insertMongoCustomTagCategory(customTagCategory);

        CustomTagCategory customTagCategoryResult = mongoTemplate.findOne(
                new Query(Criteria.where("custom_tag_category_id").is(customTagCategory.getCustomTagCategoryId())),
                CustomTagCategory.class);

        Assert.assertEquals(customTagCategory.getCustomTagCategoryId(),
                customTagCategoryResult.getCustomTagCategoryId());
        Assert.assertEquals(customTagCategory.getCustomTagCategoryName(),
                customTagCategoryResult.getCustomTagCategoryName());
    }


    @Test
    public void testFindByCategoryIdString() throws Exception {
        CustomTagCategory customTagCategoryResult =
                mongoCustomTagCategoryDao.findByCategoryId(ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID);
        Assert.assertEquals("未分类", customTagCategoryResult.getCustomTagCategoryName());
    }

    @Test
    public void testFindByCategoryIdStringInteger() {

        CustomTagCategory customTagCategory = customTagCategoryLists.get(2);

        CustomTagCategory customTagCategoryResult = mongoCustomTagCategoryDao
                .findByCategoryId(customTagCategory.getCustomTagCategoryId(), customTagCategory.getIsDeleted());
        Assert.assertEquals(customTagCategory.getCustomTagCategoryId(),
                customTagCategoryResult.getCustomTagCategoryId());
        Assert.assertEquals(customTagCategory.getCustomTagCategoryName(),
                customTagCategoryResult.getCustomTagCategoryName());

    }

    @Test
    public void testFindByChildrenCustomTagList() {
        CustomTag customTag = customTagLists.get(0);
        CustomTagCategory customTagCategory = customTagCategoryLists.get(0);
        CustomTagCategory customTagCategoryResult =
                mongoCustomTagCategoryDao.findByChildrenCustomTagList(customTag.getCustomTagId());

        Assert.assertEquals(customTagCategory.getCustomTagCategoryId(),
                customTagCategoryResult.getCustomTagCategoryId());
        Assert.assertEquals(customTagCategory.getCustomTagCategoryName(),
                customTagCategoryResult.getCustomTagCategoryName());
    }

    @After
    public void tearDown() throws Exception {
        mongoTemplate.remove(new Query(), CustomTag.class);
        mongoTemplate.remove(new Query(), CustomTagCategory.class);
    }
}
