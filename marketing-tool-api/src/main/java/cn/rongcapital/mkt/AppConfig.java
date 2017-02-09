/*************************************************
 * @功能及特点的描述简述: 注解形式进行配置aspectJ
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-2-9
 * @date(最后修改日期)：2017-2-9
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig {
}
