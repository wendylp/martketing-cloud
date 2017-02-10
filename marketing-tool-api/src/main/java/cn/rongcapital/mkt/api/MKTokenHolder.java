/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年2月8日 
 * @date(最后修改日期)：2017年2月8日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.api;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.rongcapital.caas.agent.AccessTokenInfo;
import cn.rongcapital.caas.agent.spring.token.TokenHolder;
import cn.rongcapital.caas.agent.spring.util.RequestHolder;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;

public class MKTokenHolder implements TokenHolder {

   /* protected CaasOauth2Agent caasAgent;
    
    public void setCaasAgent(CaasOauth2Agent caasAgent){
        this.caasAgent = caasAgent;
    }*/
    
    /* (non-Javadoc)
     * @see cn.rongcapital.caas.agent.spring.token.TokenHolder#getToken()
     */
    @Override
    public AccessTokenInfo getToken() throws IOException {
        // TODO Auto-generated method stub
        HttpServletRequest httpRequest = RequestHolder.currentRequest();
        String user_id=httpRequest.getParameter(ApiConstant.API_USER_ID); //t
        String userKey ="user:"+user_id;
        AccessTokenInfo access=new AccessTokenInfo();
        try {
            Map<String, String> user_token_map=JedisClient.getuser(userKey);
             user_token_map.get("accessToken"); 
             access.setAccessToken(userKey);
        } catch (JedisException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return access;
    }

    /* (non-Javadoc)
     * @see cn.rongcapital.caas.agent.spring.token.TokenHolder#setToken(cn.rongcapital.caas.agent.AccessTokenInfo)
     */
    @Override
    public void setToken(AccessTokenInfo token) {
        // TODO Auto-generated method stub

    }

}
