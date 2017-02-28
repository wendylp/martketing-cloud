/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年1月22日 
 * @date(最后修改日期)：2017年1月22日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.interceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ConfigBoot implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    
    private static int serverPort;
    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        // TODO Auto-generated method stub
        this.serverPort = event.getEmbeddedServletContainer().getPort();
    }

    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    
    public static int getPort() {
        return serverPort;
    }
    
    
    public static String getIpPort()
    {   String ip=null;
        try {
            ip=InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip="无法获取IP";
        }
        return ip+":"+serverPort;
    }
}
