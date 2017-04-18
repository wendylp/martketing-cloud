/*************************************************
 * @功能及特点的描述简述: 用于描述删除时，返回值类型必须是BaseOutput,如果不是就抛异常
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

public class ReturnTypeMustBaseOutputException extends RuntimeException {

    private static final long serialVersionUID = 890354370361923735L;


    public ReturnTypeMustBaseOutputException() {
        super();
    }

    public ReturnTypeMustBaseOutputException(String message) {
        super(message);
    }


    public ReturnTypeMustBaseOutputException(String message, Throwable cause) {
        super(message, cause);
    }


    public ReturnTypeMustBaseOutputException(Throwable cause) {
        super(cause);
    }
}


