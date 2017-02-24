/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的） 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统) @author:liuhaizhan
 * @version: 版本v1.6 @date(创建、开发日期)：2017年1月9日 @date(最后修改日期)：2017年1月9日 @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.event.EventObjectPropValueDao;
import cn.rongcapital.mkt.event.po.EventPropValue;
import cn.rongcapital.mkt.event.service.EventObjectPropValueService;
import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;

@Service
public class EventObjectPropValueServiceImpl implements EventObjectPropValueService {


    @Autowired
    private EventObjectPropValueDao eventObjectPropValueDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.rongcapital.mkt.event.service.EventObjectPropValueService#insertPropValue(cn.rongcapital.
     * mkt.po.mongodb.event.EventBehavior)
     */
    @Override
    public void insertPropValue(EventBehavior eventBehavior) {
        // TODO Auto-generated method stub
     
           // 利用 insert ignore into 来完成有的则不插入,没有插入
        List<EventPropValue> tempdatas = getEventPropValue(eventBehavior);
        if (CollectionUtils.isNotEmpty(tempdatas)) {

            eventObjectPropValueDao.insertBatchPropValue(tempdatas);
        } 


    }



    /**
     * @author liuhaizhan
     * @功能简述: 获取事件行为数据中的客体属性值
     * @param
     * @return
     */
    private List<EventPropValue> getEventPropValue(EventBehavior eventBehavior) {
        Map<String, Object> map = (Map<String, Object>) eventBehavior.getObject().get("attributes");
        List<EventPropValue> list = new ArrayList<EventPropValue>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            EventPropValue eventObjectPropValue = new EventPropValue();
            eventObjectPropValue.setObjectId(eventBehavior.getObjectId());
            eventObjectPropValue.setPropName(entry.getKey());
            eventObjectPropValue.setPropValue(entry.getValue().toString());
            list.add(eventObjectPropValue);
        }

        return list;
    }

}
