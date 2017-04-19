/*************************************************
 * @功能及特点的描述简述: 贝贝熊会员mongo
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

import org.omg.CORBA.StringHolder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
@Document(collection = "TBBXMember")
public class TBBXMember implements Serializable {
    @Id
    private String id;
    private String bbxuploadtime;
    private Date birthday;
    private String membercode;
    private Integer memberid;
    private String name;
    private String phone;
    private int sex;
    private String source;
    private int status;
    private String store;
    private String strategy;
    private String updateRMCmode;
    private Date updateRMCTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBbxuploadtime() {
        return bbxuploadtime;
    }

    public void setBbxuploadtime(String bbxuploadtime) {
        this.bbxuploadtime = bbxuploadtime;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMembercode() {
        return membercode;
    }

    public void setMembercode(String membercode) {
        this.membercode = membercode;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
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
}
