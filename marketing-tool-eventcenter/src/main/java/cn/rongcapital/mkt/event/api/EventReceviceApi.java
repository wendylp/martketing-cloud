/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年1月10日 
 * @date(最后修改日期)：2017年1月10日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.vo.BaseOutput;

@Path(ApiConstant.EVENT_API_PATH)
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public interface EventReceviceApi {
  
    
    @POST
    @Path("/mkt.event.receive")
    @Consumes({ MediaType.APPLICATION_JSON })
    public  BaseOutput  eventReceive(String eventSend);
    
    @POST
    @Path("/mkt.event.weixin.receive")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput weixineventReceive(String eventSend);
    
    
    @POST
    @Path("/mkt.event.stream.testData")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput postTestData(String eventSend);
    
}
