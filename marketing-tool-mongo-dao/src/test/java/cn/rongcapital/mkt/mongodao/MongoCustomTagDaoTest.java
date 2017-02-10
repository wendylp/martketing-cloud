package cn.rongcapital.mkt.mongodao;

import static org.junit.Assert.*;

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
import cn.rongcapital.mkt.mongodao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;

@RunWith(SpringJUnit4ClassRunner.class)
public class MongoCustomTagDaoTest extends AbstractUnitTest {

    @Autowired
    private MongoCustomTagDao mongoCustomTagDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    private List<CustomTagCategory> customTagCategoryLists = new ArrayList<CustomTagCategory>();

    private List<CustomTag> customTagLists = new ArrayList<CustomTag>();

    @Before
    public void setUp() throws Exception {
        CustomTag customTagOne = new CustomTag("CustomTagA0000000000001", "自定义标签1", 0, 100, 1000);
        CustomTag customTagTwo = new CustomTag("CustomTagA0000000000002", "自定义标签2", 0, 200, 2000);
        CustomTag customTagThree = new CustomTag("CustomTagA0000000000003", "自定义标签3", 0, 0, 0);
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

    @After
    public void tearDown() throws Exception {
        mongoTemplate.remove(new Query(), CustomTag.class);
        mongoTemplate.remove(new Query(), CustomTagCategory.class);
    }

    @Test
    public void testFindOneCustomTag() {
        CustomTag customTagSelect = null;
        CustomTag customTagResult = mongoCustomTagDao.findOne(customTagSelect);
        Assert.assertNull(customTagResult);

        CustomTag customTag = customTagLists.get(0);
        customTagSelect = new CustomTag();
        customTagSelect.setCustomTagId(customTag.getCustomTagId());
        customTagResult = mongoCustomTagDao.findOne(customTagSelect);
        Assert.assertEquals(customTag.getCustomTagId(), customTagResult.getCustomTagId());
        Assert.assertEquals(customTag.getCustomTagName(), customTagResult.getCustomTagName());
    }

    @Test
    public void testFindOneQuery() {
        Query query = null;
        CustomTag customTagResult = mongoCustomTagDao.findOne(query);
        Assert.assertNull(customTagResult);
        
        CustomTag customTag = customTagLists.get(0);
        query = new Query(Criteria.where("custom_tag_id").is(customTag.getCustomTagId()));
        customTagResult = mongoCustomTagDao.findOne(query);
        Assert.assertEquals(customTag.getCustomTagId(), customTagResult.getCustomTagId());
        Assert.assertEquals(customTag.getCustomTagName(), customTagResult.getCustomTagName());
    }

    @Test
    public void testFindCustomTag() {
        CustomTag customTagSelect = null;
        List<CustomTag> customTagResultLists = mongoCustomTagDao.find(customTagSelect);
        Assert.assertNull(customTagResultLists);

        CustomTag customTag = customTagLists.get(0);
        customTagSelect = new CustomTag();
        customTagSelect.setCustomTagId(customTag.getCustomTagId());
        customTagResultLists = mongoCustomTagDao.find(customTagSelect);
        Assert.assertEquals(1, customTagResultLists.size());
        Assert.assertEquals(customTag.getCustomTagId(), customTagResultLists.get(0).getCustomTagId());
        Assert.assertEquals(customTag.getCustomTagName(), customTagResultLists.get(0).getCustomTagName());
    }

    @Test
    public void testFindQuery() {
        Query query = null;
        List<CustomTag> customTagResultLists = mongoCustomTagDao.find(query);
        Assert.assertNull(customTagResultLists);
        
        CustomTag customTag = customTagLists.get(0);
        query = new Query(Criteria.where("custom_tag_id").is(customTag.getCustomTagId()));
        customTagResultLists = mongoCustomTagDao.find(query);
        Assert.assertEquals(1, customTagResultLists.size());
        Assert.assertEquals(customTag.getCustomTagId(), customTagResultLists.get(0).getCustomTagId());
        Assert.assertEquals(customTag.getCustomTagName(), customTagResultLists.get(0).getCustomTagName());
    }

    @Test
    public void testCountValidCustomTag() {
        List<String> customTagIdSelect = null;
        long countResult = mongoCustomTagDao.countValidCustomTag(customTagIdSelect);
        Assert.assertEquals(0, countResult);
        
        customTagIdSelect = new ArrayList<String>();
        customTagIdSelect.add(customTagLists.get(0).getCustomTagId());
        customTagIdSelect.add(customTagLists.get(1).getCustomTagId());
        customTagIdSelect.add(customTagLists.get(3).getCustomTagId());
        countResult = mongoCustomTagDao.countValidCustomTag(customTagIdSelect);
        Assert.assertEquals(2, countResult);
    }

    @Test
    public void testInsertCustomTag() {
        CustomTag customTag = new CustomTag("newCustomTagA0000000000000", "新插入自定义标签", 0, 500, 5000);
        mongoCustomTagDao.insertCustomTag(customTag);
        
        CustomTag customTagResult = mongoTemplate.findOne(new Query(Criteria.where("custom_tag_id").is(customTag.getCustomTagId())), CustomTag.class);
        Assert.assertEquals(customTag.getCustomTagId(), customTagResult.getCustomTagId());
        Assert.assertEquals(customTag.getCustomTagName(), customTagResult.getCustomTagName());
    }

    @Test
    public void testFindByCustomTagIdString() {
        CustomTag customTag= customTagLists.get(0);
        CustomTag customTagResult = mongoCustomTagDao.findByCustomTagId(customTag.getCustomTagId());
        Assert.assertEquals(customTag.getCustomTagId(), customTagResult.getCustomTagId());
        Assert.assertEquals(customTag.getCustomTagName(), customTagResult.getCustomTagName());
    }

    @Test
    public void testFindByCustomTagIdStringInteger() {
        CustomTag customTag= customTagLists.get(3);
        
        CustomTag customTagResult = mongoCustomTagDao.findByCustomTagId(customTag.getCustomTagId(),customTag.getIsDeleted());
        Assert.assertEquals(customTag.getCustomTagId(), customTagResult.getCustomTagId());
        Assert.assertEquals(customTag.getCustomTagName(), customTagResult.getCustomTagName());
    }

    @Test
    public void testFindByCustomTagIdListListOfString() {
        List<String> customTagIdSelect = new ArrayList<String>();
        for(CustomTag customTag : customTagLists) {
            customTagIdSelect.add(customTag.getCustomTagId());
        }
        
        List<CustomTag> customTagResultLists = mongoCustomTagDao.findByCustomTagIdList(customTagIdSelect);
        
        Assert.assertEquals(customTagLists.size() -1, customTagResultLists.size());
        
        CustomTag customTag = customTagLists.get(0);
        Assert.assertEquals(customTag.getCustomTagId(), customTagResultLists.get(0).getCustomTagId());
        Assert.assertEquals(customTag.getCustomTagName(), customTagResultLists.get(0).getCustomTagName());
    }

    @Test
    public void testFindByCustomTagIdListListOfStringIntegerInteger() {
        List<String> customTagIdSelect = new ArrayList<String>();
        for(CustomTag customTag : customTagLists) {
            customTagIdSelect.add(customTag.getCustomTagId());
        }
        List<CustomTag> customTagResultLists = mongoCustomTagDao.findByCustomTagIdList(customTagIdSelect,1,1);
        
        Assert.assertEquals(1, customTagResultLists.size());
        
        // 返回结果默认顺序有关
        CustomTag customTag = customTagLists.get(1);
        Assert.assertEquals(customTag.getCustomTagId(), customTagResultLists.get(0).getCustomTagId());
        Assert.assertEquals(customTag.getCustomTagName(), customTagResultLists.get(0).getCustomTagName());
    }

    @Test
    public void testFindByCustomTagIdListAndNameFuzzy() {
        List<String> customTagIdSelect = new ArrayList<String>();
        for(CustomTag customTag : customTagLists) {
            customTagIdSelect.add(customTag.getCustomTagId());
        }
        List<CustomTag> customTagResultLists = mongoCustomTagDao.findByCustomTagIdListAndNameFuzzy(customTagIdSelect,"3");
        
        Assert.assertEquals(1, customTagResultLists.size());
        
        CustomTag customTag = customTagLists.get(2);
        Assert.assertEquals(customTag.getCustomTagId(), customTagResultLists.get(0).getCustomTagId());
        Assert.assertEquals(customTag.getCustomTagName(), customTagResultLists.get(0).getCustomTagName());
    }

    @Test
    public void testFindByCustomTagNameFuzzyAndCoverNumber() {
        List<CustomTag> customTagResultLists = mongoCustomTagDao.findByCustomTagNameFuzzyAndCoverNumber("自定义", 2);
        Assert.assertEquals(2, customTagResultLists.size());
        CustomTag customTag = customTagLists.get(1);
        Assert.assertEquals(customTag.getCustomTagId(), customTagResultLists.get(1).getCustomTagId());
        Assert.assertEquals(customTag.getCustomTagName(), customTagResultLists.get(1).getCustomTagName());
    }

    @Test
    public void testCountByCustomTagNameFuzzyAndCoverNumber() {
        long result = mongoCustomTagDao.countByCustomTagNameFuzzyAndCoverNumber("自定义");
        Assert.assertEquals(2, result);
    }

    @Test
    public void testFindByCustomTagNameFuzzy() {
        List<CustomTag> customTagResultLists = mongoCustomTagDao.findByCustomTagNameFuzzy("自定义", 2,2);
        Assert.assertEquals(1, customTagResultLists.size());
        CustomTag customTag = customTagLists.get(2);
        Assert.assertEquals(customTag.getCustomTagId(), customTagResultLists.get(0).getCustomTagId());
        Assert.assertEquals(customTag.getCustomTagName(), customTagResultLists.get(0).getCustomTagName());
    }

    @Test
    public void testCountByCustomTagNameFuzzy() {
        long result = mongoCustomTagDao.countByCustomTagNameFuzzy("自定义");
        Assert.assertEquals(3, result);
    }

    @Test
    public void testCountAll() {
        long result = mongoCustomTagDao.countAll();
        Assert.assertEquals(3, result);
    }

}
