/*************************************************
 * @功能及特点的描述简述: 事件库查询（分页）
 
 * @对应项目名称：营销云系统
 * @author: 单璟琦
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-01-11 
 * @date(最后修改日期)
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.SqlConvertUtils;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.event.service.EventModelListService;
import cn.rongcapital.mkt.event.vo.out.EventModelListOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class EventModelListServiceImpl implements EventModelListService{

    @Autowired
    private EventDao eventDao;
	
	@Override
	public BaseOutput getEventModelList(String channel, Long sourceId, String eventName, Integer index, Integer size) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("channel", channel);
        paramMap.put("event_name", SqlConvertUtils.escapeSQLCharacter(eventName));
        paramMap.put("source_id", sourceId);
        int proIndex = (index == null || index.intValue() == 0) ? 1 : index;
        int proSize = (size == null || size.intValue() == 0) ? 10 : size;
        paramMap.put("index", (proIndex - 1) * proSize);
        paramMap.put("size", proSize);
        
        int totalCnt = eventDao.getEventModelListCnt(paramMap);
        result.setTotalCount(totalCnt);
        if(totalCnt >0){
        	List<EventModelListOut> list = eventDao.getEventModelList(paramMap);
            if (CollectionUtils.isNotEmpty(list)) {
                result.setTotal(list.size());
                result.getData().addAll(list);
            }
        }
        
		return result;
	}

}
