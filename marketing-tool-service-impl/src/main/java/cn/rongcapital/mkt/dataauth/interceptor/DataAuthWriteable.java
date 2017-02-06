/*************************************************
 * @功能及特点的描述简述: 克隆数据权限注解类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-02-06 
 * @date(最后修改日期)：2017-02-06 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.dataauth.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})//作用于参数或方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataAuthWriteable{
    
    String resourceType() ;

    String resourceId() ;
    
    String orgId();
    
    ParamType type() default ParamType.Default;

}
