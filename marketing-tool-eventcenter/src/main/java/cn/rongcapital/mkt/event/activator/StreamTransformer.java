/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年2月26日 
 * @date(最后修改日期)：2017年2月26日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.activator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

public class StreamTransformer {
 
    private static final String HEADER_KEY_EVENT_TYPE =EventConstant.EVENT_HEADER;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamTransformer.class);
    
    public Message<?> transformStream(final Message<?> streammessage)
    {
        String message =EventConstant.formatMessage((Map)streammessage.getPayload());
        return MessageBuilder.withPayload(convertMessage(message)).setHeader(HEADER_KEY_EVENT_TYPE,EventConstant.EVENT_STREAM).build();
    }
    
    
      /**
         * @author liuhaizhan
         * @功能简述: 转换成MC事件
         * @param 合成格式：
         * {
    "subject": {
        "o_mc_contact_sys_source": "yrdy,web",
        "o_mc_contact_sys_ids": "o_mc_contact_soc_mobile_phone,o_mc_contact_soc_mail",
        "o_mc_contact_attr_name": "ypsliu_test",
        "o_mc_contact_soc_mobile_phone":13842821191,
        "o_mc_contact_soc_mail":"ypsliu@163.com",
        "o_mc_contact_attr_age": 22,
        "o_mc_contact_attr_nationality": "少數民族"
    },
    "time": 1488874105000,
    "object": {
        "code": "form_signup_ruixuesoft",
        "attributes": {
            "o_mobile": "13823821028",
            "o_mail": "yps@173.com",
            "o_company": "ryld",
            "o_name": "haii"
        }
    },
    "event": {
        "code": "apply_submit_stream",
        "attributes": {
            "b_mc_party": "二方事件",
            "b_mc_platform": "stream",
            "b_mc_source": "瑞雪官网"
        }
    }
   }
         * 
         * 
         *  原：[
    {
        \"properties\": {
            \"b_mc_source\": \"瑞雪官网\",
            \"b_mc_platform\": \"STREAM\",
            \"b_mc_party\": \"二方事件\",
            \"b_dollar_os\": \"windows\",
            \"b_dollar_os_version\": \"10.0\",
            \"b_dollar_screen_height\": 1080,
            \"b_dollar_screen_width\": 1920,
            \"b_dollar_page_title\": \"瑞雪科技\",
            \"b_dollar_page_h1\": \"\",
            \"b_dollar_page_referrer\": \"\",
            \"b_dollar_page_url\": \"http: //www.ruixuesoft.com/#/free-trial\",
            \"b_dollar_page_url_path\": \"/\",
            \"b_dollar_lib\": \"js\",
            \"b_dollar_lib_version\": \"1.2.0\",
            \"b_dollar_browser\": \"chrome\",
            \"b_dollar_browser_version\": \"45\"
        },
        \"subject\": {
            \"o_mc_contact_soc_mobile_phone\": \"13842821033\",
            \"o_mc_contact_soc_mail\": \"ypsliu@163.com\",
            \"o_mc_contact_soc_qq\": \"12345678\",
            \"o_mc_contact_attr_name\": \"yyy\",
            \"o_mc_contact_sys_ids\": \"o_mc_contact_soc_mobile_phone,
            o_mc_contact_soc_mail\",
            \"o_mc_contact_sys_source\": \"瑞雪官网\"
        },
        \"object\": {
            \"o_form_type\": \"STREAM\",
            \"o_company\": \"ypsloiu\",
            \"o_mobile\": \"13842821033\",
            \"o_mail\": \"ypsliu@163.com\",
            \"o_qq\": \"12345678\",
            \"o_name\": \"yyy\"
        },
        \"action\": \"track\",
        \"event\": \"apply_submit_ruixuesoft\",
        \"time\": \"2017-03-21T14: 56: 45.652+08: 00\",
        \"sending\": true,
        \"cid\": \"15aefa756af23d-07c9c80b3-3b664008-1fa400-15aefa756b041b\",
        \"pid\": \"15aefa6849248-00d8939-3b664008-1fa400-15aefa6849359a\"
    }
]
         * 
         * 
         * @return 
         */
    private String convertMessage(final String mes)
    {
        StringBuilder   mcInfo=new StringBuilder("{");
        mcInfo.append("\"subject\":{");
        mcInfo.append(EventConstant.getParam(mes,"\\\"subject\\\":{","},"));
        mcInfo.append("},\"time\":").append(getDateTime(EventConstant.getParam(mes,"\\\"time\\\":\\\"", "\\\",")));
        mcInfo.append(",\"object\":{\"code\": \"form_signup_ruixuesoft\",\"attributes\":{");
        mcInfo.append(EventConstant.getParam(mes, "\\\"object\\\":{","},"));
        mcInfo.append("}},\"event\":{\"code\": \"apply_submit_ruixuesoft\",\"attributes\":{");
        mcInfo.append(EventConstant.getParam(mes, "\\\"properties\\\":{",",\\\"b_dollar_os\\\":"));
        mcInfo.append("}}}"); 
        return mcInfo.toString().replaceAll("\\\\", "");
        
    }
   
    
    private long getDateTime(String time)
    {
        
        return time==null?new DateTime().getMillis():new DateTime(time).getMillis();
    }
}