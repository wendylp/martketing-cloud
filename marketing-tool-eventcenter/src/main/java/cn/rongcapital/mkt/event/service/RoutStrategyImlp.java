/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的） 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统) @author:liuhaizhan
 * @version: 版本v1.6 @date(创建、开发日期)：2017年3月2日 @date(最后修改日期)：2017年3月2日 @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.common.util.CacheManage;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.event.po.Event;

@Service
public class RoutStrategyImlp implements RoutStrategy {

    /*
     * (non-Javadoc)
     * 
     * @see cn.rongcapital.mkt.event.service.RoutStrategy#iFRouter(java.lang.String)
     * 
     * 
     */
    @Autowired
    private EventDao eventDao;

    /*
     * (non-Javadoc)
     * 
     * @see cn.rongcapital.mkt.event.service.RoutStrategy#iFRouter(java.lang.String) 只有订阅的事件方可通过
     */
    @Override
    public boolean iFRouter(String message) throws ExecutionException {
        // TODO Auto-generated method stub
        JSONObject event = JSON.parseObject(message);
        event = event.getJSONObject("event");
        String code = event.getString("code");
        Long ObjectId= CacheManage.cache.get(code, new Callable<Long>(){
            @Override
            public Long call() throws Exception {
                // TODO Auto-generated method stub
                return returnObjectId(code);
            }

        });
        if (ObjectId > -1L) {
            // 增加缓存objectID
            return true;
        }
        return false;
    }
    
    private Long returnObjectId(String code)
    {
        Event event=eventDao.selectByCode(code);
        return event!=null?event.getObjectId():-1L;
    }

}
