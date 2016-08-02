package cn.rongcapital.mkt.job.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.ShoppingWechat;

public class TagDataTotalShoppingCountServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Override
    public void task(Integer taskId) {
        logger.info("tag task : 购物记录中单个微信用户（公众号标识＋openid）购买的总次数（订单号） ");
        logger.info("用户价值－购买价值－总购买频次－（1次、2次、3次、4次以上），末级标签需替换之前的高、中、低");
    }

    // TODO 根本不能这么搞, mapping_keyid还不知道怎么查呢.
    // 已经将shopping总数获取到, 交给mongoDB处理
    public ShoppingWechat getByTotalShoppingCount() {
        return dataShoppingDao.selectTotalShoppingCountByWeChatInfo();
    }
}
