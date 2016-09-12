package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.po.WechatQrcodeFocus;
import cn.rongcapital.mkt.service.QrcodeFocusInsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Yunfeng on 2016-9-9.
 */
public class QrcodeFocusInsertServiceImpl implements QrcodeFocusInsertService{

    private final Integer FIRST_INDEX = 0;

    @Autowired
    private WechatQrcodeDao wechatQrcodeDao;

    @Autowired
    private WechatQrcodeFocusDao wechatQrcodeFocusDao;

    @Override
    public boolean insertQrcodeFoucsInfo(String qrCodeTicket,String openId, Date scanQrTime, Integer focusStatus) {
        boolean insertSuccess = false;
        if(qrCodeTicket == null || scanQrTime == null || focusStatus == null){
            return insertSuccess;
        }

        WechatQrcode wechatQrcode = new WechatQrcode();
        wechatQrcode.setTicket(qrCodeTicket);
        List<WechatQrcode> wechatQrcodeList = wechatQrcodeDao.selectList(wechatQrcode);
        if(!CollectionUtils.isEmpty(wechatQrcodeList)){
            wechatQrcode = wechatQrcodeList.get(FIRST_INDEX);
            WechatQrcodeFocus wechatQrcodeFocus = new WechatQrcodeFocus();
            wechatQrcodeFocus.setQrcodeId(String.valueOf(wechatQrcode.getId()));
            wechatQrcodeFocus.setWxName(wechatQrcode.getWxName());
            wechatQrcodeFocus.setOpenid(openId);
            wechatQrcodeFocus.setChCode(wechatQrcode.getChCode());
            wechatQrcodeFocus.setFocusDatetime(scanQrTime);
            wechatQrcodeFocus.setFocusStatus(focusStatus.byteValue());
            Integer effectRow = wechatQrcodeFocusDao.insert(wechatQrcodeFocus);
            if(effectRow > 0){
                insertSuccess = true;
            }
        }

        return insertSuccess;
    }
}
