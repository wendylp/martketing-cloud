
package cn.rongcapital.mkt.job.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.BaseTagData;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.ShoppingWechat;

@Service
public class TagDataWeimobServiceImpl extends BaseTagData implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void task(Integer taskId) {
        logger.info("tag task : 公众号下，openid有购买记录的用户标记为是购买用户，其余的标记为不是购买用户");
        logger.info("（新增）品牌联系强度－客户流失概率－是否购买用户－（是／否）");
        handleData(getWeimob());
    }

    public List<ShoppingWechat> getWeimob() {
        return dataShoppingDao.selectAllDataByWeimob();
    }

    @Override
    public void tagData(ShoppingWechat shoppingWechat) {
        Criteria criteria = Criteria.where("mid").is(shoppingWechat.getDataPartyId());
        Update update = new Update().set("weimob", shoppingWechat.getWeimob());
        mongoTemplate.findAndModify(new Query(criteria), update, cn.rongcapital.mkt.po.mongodb.DataParty.class);
    }

}
