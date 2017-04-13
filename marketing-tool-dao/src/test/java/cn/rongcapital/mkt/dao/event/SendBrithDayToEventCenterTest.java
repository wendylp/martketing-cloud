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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
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
    
    private String SUCCESS = "success";
    
    public void test()
    {
        Map<Integer, List<DataParty>> datamap =new HashMap<Integer, List<DataParty>>();
        datamap.forEach((k,v)->{
            
        });
    }
    
    
    
    private void sendEventToEventCenter(String  httpStr) {
        logger.info(" this is sendEventToEventCenter ");
        String eventReceiveUrl = env.getProperty("mkt.event.receive");
        String hostHeaderAddr = env.getProperty("host.header.adhttpStrdr");        
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
    
    
    

}
