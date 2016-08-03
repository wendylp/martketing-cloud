package cn.rongcapital.mkt.job.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.BaseTagData;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.ShoppingWechat;

public class TagDataLastTransTimeServiceImpl extends BaseTagData implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Override
    public void task(Integer taskId) {
        logger.info("tag task : 购物记录中单个微信用户（公众号标识＋openid）最后一次购买（取订单号的消费时间记最后一次购买时间）的时间");
        logger.info("用户价值－购买价值－最后一次购买－（本月内、三个月内、半年内）");
    }

    // 已经将包含时间的数据对象取出, 交给mongoDB处理
    private List<ShoppingWechat> getByLastTransTime() {
        return dataShoppingDao.selectListByLastTransTimeandWeChatInfo();
    }

    @Override
    public void tagData(ShoppingWechat shoppingWechat) {

    }


}
