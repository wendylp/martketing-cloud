package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface CustomtagQrcodeFuzzyListService {
    /**
     * 功能描述：微信二维码，搜索自定义标签列表
     * 
     * 接口：mkt.customtag.qrcode.fuzzy.list
     * 
     * @param customTagName
     * @param index
     * @param size
     * @return
     */
    public BaseOutput customtagQrcodeFuzzyListGet(String customTagName, Integer index, Integer size);
}
