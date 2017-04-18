/*************************************************
 * @功能及特点的描述简述: 用于描述当前组织对当前数据是否具有可写权限，如果无权限则抛出异常
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

public class NoWriteablePermissionException extends Exception {

    private static final long serialVersionUID = 890354370361923735L;


    public NoWriteablePermissionException() {
        super();
    }

    public NoWriteablePermissionException(String message) {
        super(message);
    }


    public NoWriteablePermissionException(String message, Throwable cause) {
        super(message, cause);
    }


    public NoWriteablePermissionException(Throwable cause) {
        super(cause);
    }
}


