/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-4-19
 * @date(最后修改日期)：2017-4-19
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.webservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class StaticPropertiesSetting {

    @Value("${bbx.crm.webservice.address}")
    private String crmVipWebServiceAddress;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean mifb = new MethodInvokingFactoryBean();
        mifb.setStaticMethod("cn.rongcapital.mkt.webservice.BBXCrmWSUtils.setCrmVipWebServiceAddress");
        mifb.setArguments(new String[] { this.crmVipWebServiceAddress });
        return mifb;
    }

    public String getCrmVipWebServiceAddress() {
        return crmVipWebServiceAddress;
    }

    public void setCrmVipWebServiceAddress(String crmVipWebServiceAddress) {
        this.crmVipWebServiceAddress = crmVipWebServiceAddress;
    }
}
