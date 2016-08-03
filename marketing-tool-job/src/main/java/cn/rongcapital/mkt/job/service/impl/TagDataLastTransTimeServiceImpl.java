package cn.rongcapital.mkt.job.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.ShoppingWechat;

public class TagDataLastTransTimeServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Autowired
    private DataPartyDao dataPartyDao;

    @Override
    public void task(Integer taskId) {
        logger.info("tag task : 购物记录中单个微信用户（公众号标识＋openid）最后一次购买（取订单号的消费时间记最后一次购买时间）的时间");
        logger.info("用户价值－购买价值－最后一次购买－（本月内、三个月内、半年内）");
    }

    // 已经将包含时间的数据对象取出, 交给mongoDB处理
    private List<ShoppingWechat> getByLastTransTime() {
        return dataShoppingDao.selectListByLastTransTimeandWeChatInfo();
    }

    private void tagData(ShoppingWechat shoppingWechat) {

    }

    public void handleData(List<ShoppingWechat> shoppingWechats) {
        if (CollectionUtils.isEmpty(shoppingWechats)) {
            logger.info("购物记录中单个微信用户（公众号标识＋openid）最后一次购买（取订单号的消费时间记最后一次购买时间）的时间 因对应的数据不存在无法打标签");
            return;
        }

        for (ShoppingWechat shoppingWechat : shoppingWechats) {
            DataParty paramDataParty = new DataParty();
            paramDataParty.setWxmpCode(shoppingWechat.getPubId());
            paramDataParty.setOpenid(shoppingWechat.getOpenId());
            // 这里应该是一条数据
            List<DataParty> dataParties = dataPartyDao.selectList(paramDataParty);
            if (CollectionUtils.isEmpty(dataParties)) {
                // 这其实就是个问题, 根据外键不能查数据 ?
                logger.error("无法通过openId,pubId获取主数据");
                continue;
            } else {
                if (dataParties.size() > 0) {
                    logger.error("通过openId,pubId定位出超过一条的主数据了!openid={},pubid={}", shoppingWechat.getOpenId(),
                                    shoppingWechat.getPubId());
                    DataParty dataParty = dataParties.get(0);
                    shoppingWechat.setDataPartyId(dataParty.getId());
                    tagData(shoppingWechat);
                }
            }
        }
    }

}