/*************************************************
 * @功能简述: 根据事件编码判断是否进行主数据合并
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/3/1
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.event.service.EventSubjectCombineService;

@Service
public class EventSubjectCombineServiceImpl implements EventSubjectCombineService {

    @Override
    public boolean needCombine(String eventCode) {
        // TODO Auto-generated method stub
        return true;
    }



}
