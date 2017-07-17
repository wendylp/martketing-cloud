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
import scala.Int;

import java.io.Serializable;
import java.util.Date;
@Document(collection = "TBBXOrderPayDetail")
public class TBBXOrderPayDetail implements Serializable {
    @Id
    private String id;
    private long orderid;
    private String paytype;
    private String salem;
    private long couponid;
    private String bbxuploadtime;
    private String updateRMCmode;
    private Date updateRMCTime;
    private long status;

    private boolean checked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getSalem() {
        return salem;
    }

    public void setSalem(String salem) {
        this.salem = salem;
    }

    public long getCouponid() {
        return couponid;
    }

    public void setCouponid(long couponid) {
        this.couponid = couponid;
    }

    public String getBbxuploadtime() {
        return bbxuploadtime;
    }

    public void setBbxuploadtime(String bbxuploadtime) {
        this.bbxuploadtime = bbxuploadtime;
    }

    public String getUpdateRMCmode() {
        return updateRMCmode;
    }

    public void setUpdateRMCmode(String updateRMCmode) {
        this.updateRMCmode = updateRMCmode;
    }

    public Date getUpdateRMCTime() {
        return updateRMCTime;
    }

    public void setUpdateRMCTime(Date updateRMCTime) {
        this.updateRMCTime = updateRMCTime;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
