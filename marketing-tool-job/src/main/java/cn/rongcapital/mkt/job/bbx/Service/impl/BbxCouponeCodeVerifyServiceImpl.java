/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-4-18
 * @date(最后修改日期)：2017-4-18
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.job.bbx.Service.impl;

import cn.rongcapital.mkt.bbx.service.BbxCouponCodeAddService;
import cn.rongcapital.mkt.dao.bbx.BbxCouponCodeAddDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.TaskSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BbxCouponeCodeVerifyServiceImpl implements TaskService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BbxCouponCodeAddService bbxCouponCodeAddService;
    @Override
    public void task(Integer taskId) {
        this.bbxCouponCodeAddService.verifyCouponCode();
    }
}
