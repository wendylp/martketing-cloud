/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年1月9日 
 * @date(最后修改日期)：2017年1月9日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.po;

import java.io.Serializable;

public class EventPropValue implements Serializable {
    
    private static  long serialVersionUID = 1L;
    
    private Long objectId;
    
    private String propName;

    private String propValue;

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
       if(!(obj instanceof EventPropValue))
            return false;
       EventPropValue ob =(EventPropValue)obj;
        return propName.equals(ob.getPropName())&& propValue.equals(ob.getPropValue());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        // TODO Auto-gretenerated method stub
        return (propName+propValue).hashCode();
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }


}
