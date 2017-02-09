/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-02-09 
 * @date(最后修改日期)：2017-02-09 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.dataauth.interceptor.DataAuthEvict;
import cn.rongcapital.mkt.dataauth.interceptor.DataAuthWriteable;
import cn.rongcapital.mkt.dataauth.interceptor.ParamType;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.service.SmsTempletOrganizationService;
@Service
public class SmsTempletOrganizationServiceImpl implements SmsTempletOrganizationService {

    private static final String TABLE_NAME ="sms_templet";// 资源对应表名
    @Autowired
    private SmsTempletDao smstempletDao;
    
    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.dataauth.service.SmsTempletTestService#delete(cn.rongcapital.mkt.po.SmsTemplet)
     */
    @Override
    @DataAuthWriteable(resourceType=TABLE_NAME,resourceId="#smsTempletDel.id",orgId="#orgId",type = ParamType.SpEl)
    @DataAuthEvict(resourceType = TABLE_NAME,resourceId = "#smsTempletDel.id",type = ParamType.SpEl)
    public int delete(SmsTemplet smsTempletDel) {
       return this.smstempletDao.updateById(smsTempletDel);

    }

}
