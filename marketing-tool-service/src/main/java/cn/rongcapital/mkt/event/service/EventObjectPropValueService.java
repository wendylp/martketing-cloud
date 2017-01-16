/*************************************************
 * @功能及特点的描述简述: 事件客体表属性表
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年1月9日 
 * @date(最后修改日期)：2017年1月9日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.service;

import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;

public interface EventObjectPropValueService {
  
    
      /**
         * @author  liuhaizhan
         * @功能简述: 插入事件客体属性表
         * @param 
         * @return 
         */
    public void insertPropValue(EventBehavior eventBehavior);
}
