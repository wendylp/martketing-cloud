/*************************************************
 * @功能简述: 保存固定人群接口
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/4/12
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.AudienceCreateIn;

public interface AudienceCreateService {

    public BaseOutput createAudience(AudienceCreateIn in);

}
