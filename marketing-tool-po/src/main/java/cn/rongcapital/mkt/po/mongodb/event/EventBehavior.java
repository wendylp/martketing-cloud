/*************************************************
 * @功能及特点的描述简述: 事件行为类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-01-07 
 * @date(最后修改日期)：2017-01-07 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.po.mongodb.event;

import java.io.Serializable;

import org.bson.types.BSONTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "event_behavior")
public class EventBehavior implements Serializable{

    private static final long serialVersionUID = 456107105313329296L;
    @Id
    private String id;
    private BSONTimestamp time;
    private boolean subscribed;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
  
    
    public BSONTimestamp getTime() {
        return time;
    }
    public void setTime(BSONTimestamp time) {
        this.time = time;
    }
    public boolean isSubscribed() {
        return subscribed;
    }
    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
    
   
}
