package cn.rongcapital.mkt.job.service.impl;

import java.util.List;

import cn.rongcapital.mkt.job.service.BaseTagData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.ShoppingWechat;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class TagDataTotalIncomeAmountServiceImpl extends BaseTagData implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataPaymentDao dataPaymentDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void task(Integer taskId) {
        logger.info("tag task : 支付记录中支付状态成功，单个微信用户（公众号标识＋openid）收入金额总额");
        logger.info("用户价值－购买价值－总计交易金额－（50元以下、 51-100元 、101-150元、 151-200 、201-300 、301以上）末级标签需替换之前的5000、5001-20000-20001-50000、50001以上  ");
    }

    // TODO 根本不能这么搞, mapping_keyid还不知道怎么查呢.
    // 已经将shopping总数获取到, 交给mongoDB处理
    public List<ShoppingWechat> getByTotalShoppingCount() {
        return dataPaymentDao.selectTotalIncomeAmountByWechatInfo();
    }

    @Override
    public void tagData(ShoppingWechat shoppingWechat) {
        Query query = Query.query(Criteria.where("mid").is(shoppingWechat.getDataPartyId()));
        Update update = new Update().set("totalIncome", shoppingWechat.getTotalIncome());
        mongoTemplate.findAndModify(query,update,cn.rongcapital.mkt.po.mongodb.DataParty.class);
    }
}
