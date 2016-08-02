package cn.rongcapital.mkt.job.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.ShoppingWechat;

public class TagDataSingleMonthShoppingCountServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Override
    public void task(Integer taskId) {
        logger.info("tag task : 购物记录中单个微信用户（公众号标识＋openid）单月购买次数（订单号）");
        logger.info("用户价值－购买价值－最后一次购买－（本月内、三个月内、半年内）");
    }

    public ShoppingWechat getSingleMonthShoppingCount() {
        return dataShoppingDao.selectSingleMonthShoppingCountByWeChatInfo();
    }

}
