/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年5月3日 
 * @date(最后修改日期)：2017年5月3日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.job.service.vo;

import java.util.Date;

public class BrithDayData {
  
    private Integer mid;
    
    private Integer month;
    
    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    private Integer day;
    
    private String name;
    
    private Date birthday;
    
}
