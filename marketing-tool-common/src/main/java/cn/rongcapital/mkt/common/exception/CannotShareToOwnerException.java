/*************************************************
 * @功能及特点的描述简述: 不能给资源的拥有者分享
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-02-06 
 * @date(最后修改日期)：2017-02-06 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.common.exception;

public class CannotShareToOwnerException extends Exception {

    private static final long serialVersionUID = 890354370361923735L;


    public CannotShareToOwnerException() {
        super();
    }

    public CannotShareToOwnerException(String message) {
        super(message);
    }


    public CannotShareToOwnerException(String message, Throwable cause) {
        super(message, cause);
    }


    public CannotShareToOwnerException(Throwable cause) {
        super(cause);
    }
}


