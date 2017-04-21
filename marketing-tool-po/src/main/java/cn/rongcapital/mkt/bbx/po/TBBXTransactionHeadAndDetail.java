/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-4-17
 * @date(最后修改日期)：2017-4-17
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.bbx.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
@Document(collection = "TBBXTransactionHeadAndDetail")
public class TBBXTransactionHeadAndDetail implements Serializable {
    /**
     * Description:
     */
    private static final long serialVersionUID = -2008386200111533822L;
    @Id
    private String id;
    private Long memberid;
    private Long orderid;
    private String saletime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getMemberid() {
        return memberid;
    }

    public void setMemberid(Long memberid) {
        this.memberid = memberid;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getSaletime() {
        return saletime;
    }

    public void setSaletime(String saletime) {
        this.saletime = saletime;
    }
}
