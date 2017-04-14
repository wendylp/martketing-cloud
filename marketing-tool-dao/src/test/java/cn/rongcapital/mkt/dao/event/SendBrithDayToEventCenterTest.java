/*************************************************
 * @功能及特点的描述简述:发送生日事件至事件中心测试类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年4月13日 
 * @date(最后修改日期)：2017年4月13日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.dao.event;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpClientUtil;
import cn.rongcapital.mkt.common.util.HttpUrl;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(SpringJUnit4ClassRunner.class)
public class SendBrithDayToEventCenterTest extends AbstractUnitTest {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    Environment env;
    
    private String format ="yyyy-MM-dd HH:mm:ss";  

    
    private String SUCCESS = "success";
    
    private  Map<Integer,List<DataParty>> mapdata;
    
    @Before
    public void set()
    {
        DataParty dt1 =new DataParty();  
        dt1.setMid(111111);
        dt1.setName("yps111");
        dt1.setBirthday(new Date());
        DataParty dt3 =new DataParty();  
        dt3.setMid(33333);
        dt3.setName("yps333");
        DataParty dt7 =new DataParty();  
        dt7.setMid(7777);
        dt7.setName("yps888");
        DataParty dt11 =new DataParty();  
        dt11.setMid(6666);
        dt11.setName("yps666");
        List<DataParty> list1=new ArrayList<DataParty>();
        List<DataParty> list3=new ArrayList<DataParty>();
        List<DataParty> list7=new ArrayList<DataParty>();
        list1.add(dt1);
        list1.add(dt11);
        list3.add(dt3);
        list7.add(dt7);
        mapdata = new HashMap<Integer,List<DataParty>>();
        mapdata.put(1, list1);
        mapdata.put(3, list3);
        mapdata.put(7, list7);
    }
    
    @Test
    public void test()
    {
       
        mapdata.forEach((k,v)->{
            List<String> str=getEventStrs(v,k);
            for(String st:str)
            {
                sendEventToEventCenter(st);
            }
        });
        
        
    }
    
   
    private List<String> getEventStrs(List<DataParty> v,Integer k)
    {
        List<String> list =new ArrayList<String>();
        for(DataParty dp:v)
        {
            list.add(changeEvent(dp,k));
        }
        return list;
    }
    
    /* {
    "subject": {
      "o_mc_contact_sys_source": "o0gycwIqxXT7DDkwqY-NyqmLb8Pg",
      "o_mc_contact_sys_mid": "gh_4685d4eef135",
      "o_mc_contact_sys_identity":"",
      "o_mc_contact_sys_ids":"",
      "o_mc_contact_attr_name":"",
      "o_mc_contact_attr_gender":"",
      "o_mc_contact_attr_age":"",
      "o_mc_contact_attr_birthday":"",
      "o_mc_contact_attr_nationality":"",
      "o_mc_contact_attr_citizenship":"",
      "o_mc_contact_attr_province":"",
      "o_mc_contact_attr_city":"",
      "o_mc_contact_soc_qq":"",
      "o_mc_contact_soc_wechat":"",
      "o_mc_contact_soc_weibo":"",
      "o_mc_contact_soc_mail":"",
      "o_mc_contact_soc_mobile_phone":"",
      "o_mc_contact_soc_phone":"",
      "o_mc_contact_soc_appid":"",
      "o_mc_contact_soc_openid":"",
      "o_mc_contact_id_identify_no":"",
      "o_mc_contact_id_driver_licence":""
    },
    "time": 1487903800000,
    "object": {
      "code": "brith_code",
      "attributes": {
        "brith_time":1
      }
    },
    "event": {
      "code": "customer_birthday_care",
      "attributes": {
        "b_mc_party": "gh_4685d4eef135",
        "b_mc_platform": "o0gycwIqxXT7DDkwqY-NyqmLb8Pg",
        "b_mc_source": 1487903800000
      }
    }       
  }

*/
    
    private String changeEvent(DataParty dp,int days)
    {
         Date date =new Date();
        StringBuilder str =new StringBuilder("{\"subject\":{");
        str.append("\"o_mc_contact_sys_source\":\"").append(dp.getSource()).append("\","); //用户来源
        str.append("\"o_mc_contact_sys_mid\":\"").append(dp.getMid()).append("\",");//mc用户标识
        str.append("\"o_mc_contact_sys_identity\":\"").append("").append("\",");//用户身份 参数值：空
        str.append("\"o_mc_contact_sys_ids\":\"").append("").append("\",");//用户识别ID串 参数值：空
        
        str.append("\"o_mc_contact_attr_name\":\"").append(dp.getName()).append("\",");//姓名
        str.append("\"o_mc_contact_attr_gender\":\"").append(getSex(dp.getGender()==null?0:dp.getGender())).append("\",");//性别
        str.append("\"o_mc_contact_attr_age\":\"").append("").append("\",");//年龄
        str.append("\"o_mc_contact_attr_birthday\":\"").append(processDate(dp.getBirthday())).append("\",");//生日
        str.append("\"o_mc_contact_attr_nationality\":\"").append("").append("\",");//民族
        str.append("\"o_mc_contact_attr_citizenship\":\"").append(dp.getCitizenship()).append("\",");//国籍
        str.append("\"o_mc_contact_attr_province\":\"").append(dp.getProvice()).append("\",");//省
        str.append("\"o_mc_contact_attr_city\":\"").append(dp.getCity()).append("\",");//市
        
        str.append("\"o_mc_contact_soc_qq\":\"").append("").append("\",");//QQ号
        str.append("\"o_mc_contact_soc_wechat\":\"").append(dp.getFansOpenId()).append("\",");//微信号
        str.append("\"o_mc_contact_soc_weibo\":\"").append("").append("\",");//微博号
        str.append("\"o_mc_contact_soc_mail\":\"").append("").append("\",");//邮箱
        str.append("\"o_mc_contact_soc_mobile_phone\":\"").append(dp.getMobile()).append("\",");//手机
        str.append("\"o_mc_contact_soc_phone\":\"").append("").append("\",");//手机
        str.append("\"o_mc_contact_soc_appid\":\"").append("").append("\",");//公众号id
        str.append("\"o_mc_contact_soc_openid\":\"").append(dp.getPubId()).append("\",");//用户openid
        
        str.append("\"o_mc_contact_id_identify_no\":\"").append(dp.getPubId()).append("\",");//身份证号
        str.append("\"o_mc_contact_id_driver_licence\":\"").append(dp.getPubId()).append("\"},");//驾驶证号
        
        str.append("\"time\":").append(date.getTime()).append(",");
        
        str.append("\"object\":{\"code\": \"brith_code\",\"attributes\":{");
        str.append("\"brithday_time\":\"").append(days+"天").append("\"}},");
      
        str.append("\"event\":{\"code\":\"customer_birthday_care\",\"attributes\":{");
        str.append("\"b_mc_party\":\"一方事件\"").append(",");
        str.append("\"b_mc_platform\":\"MC\"").append(",");
        str.append("\"b_mc_source\":\"MC\"").append("}}}");  
        return str.toString();
        
    }
    
    private String getSex(Byte ty) {
        String str = "";
        switch (ty) {
            case 1:
                str = "男";
                break;
            case 2:
                str = "女";
                break;
            case 3:
                str = "未确定";
            case 4:
                str = "不确定";
                break;
            default:
                str = "---";

        }
        return str;
    }
    
    private  LocalDate getLocalDate(Date date)
    {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    private void sendEventToEventCenter(String  httpStr) {
        logger.info(" this is sendEventToEventCenter ");
        String eventReceiveUrl = env.getProperty("mkt.event.receive");
        String hostHeaderAddr = env.getProperty("host.header.addr");        
        if(StringUtils.isNotEmpty(httpStr)){
            logger.info(httpStr);
            HttpUrl httpUrl = new HttpUrl();
            httpUrl.setHost(hostHeaderAddr);
            httpUrl.setPath(eventReceiveUrl);
            httpUrl.setRequetsBody(httpStr);
            httpUrl.setContentType(ApiConstant.CONTENT_TYPE_JSON);       
            HttpClientUtil httpClientUtil;
            try {
                httpClientUtil = HttpClientUtil.getInstance();
                PostMethod postResult = httpClientUtil.postExt(httpUrl);
                String postResStr = postResult.getResponseBodyAsString();
                BaseOutput baseOutput = JSON.parseObject(postResStr,BaseOutput.class);
                String msg = baseOutput.getMsg();
                if(!msg.equals(SUCCESS)){
                    logger.info(" EventCenter response fail ");
                }
            } catch (Exception e) {
                logger.error(" send to EventCenter error ");
                e.printStackTrace();
            }
        }       
    }
    
    private String processDate(Object value){  
        if(value instanceof Date){    
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);    
            return sdf.format(value);  
        }    
        return value == null ? "" : value.toString();    
    }  
    
    
    
}
