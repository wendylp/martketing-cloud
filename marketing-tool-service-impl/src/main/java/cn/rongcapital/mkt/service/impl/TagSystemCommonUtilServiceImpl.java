package cn.rongcapital.mkt.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.TagSystemCommonUtilService;
import freemarker.template.utility.StringUtil;

@Service
public class TagSystemCommonUtilServiceImpl implements TagSystemCommonUtilService{
    
    @Autowired
    private TagValueCountDao tagValueCountDao;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    
    /**
     * 功能描述：根据标签tag_id获取标签覆盖率
     * 任何异常都返回：0%
     * 
     * @param tagId
     * @return
     */
    @Override
    public String getTagCover(String tagId) {
        
        // 标签覆盖人数
        double tagCount = 0;
        // 标签覆盖率
        String tagCover = "0%";
        
        TagValueCount tagValueCount = new TagValueCount();
        if(StringUtil.emptyToNull(tagId) != null) {
            tagValueCount.setTagId(tagId);
        } else {
            return tagCover;
        }
        
        
        List<TagValueCount> tagValueCountLists = tagValueCountDao.selectList(tagValueCount);
        
        if(CollectionUtils.isNotEmpty(tagValueCountLists)) {
            tagValueCount = tagValueCountLists.get(0);
            if(tagValueCount.getValueCount() != null) {
                tagCount = tagValueCount.getValueCount();
            }
        }
        // 获取总数
        double allCount = mongoTemplate.count(null, DataParty.class);
        
        if(allCount > 0) {
//            tagCover = new DecimalFormat("#%").format(tagCount / allCount);
            tagCover = (int)(tagCount*100 / allCount) + "%";
        }
        
        return tagCover;
    }

}
