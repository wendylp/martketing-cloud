package cn.rongcapital.mkt.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.common.enums.SmsTaskAppEnum;
import cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum;
import cn.rongcapital.mkt.common.enums.SmsTempleteAuditStatusEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.dataauth.DataAuthMapper;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.dataauth.po.DataAuth;
import cn.rongcapital.mkt.po.SmsTemplet;

public class SmsTempletDaoGetTempletCountByTypeTest extends AbstractUnitTest {

    @Autowired
    private SmsTempletDao smsTempletDao;

    @Autowired
    private DataAuthMapper dataAuthMapper;

    private static final Long orgId = 1000L;

    @Before
    public void setUp() throws Exception {

        for (int i = 0; i < 2; i++) {
            SmsTemplet template = new SmsTemplet();
            template.setName("测试模板");
            template.setAuditor("user1");
            template.setAuditStatus(NumUtil.int2OneByte(SmsTempleteAuditStatusEnum.AUDIT_STATUS_NO_PASS.getStatusCode()));
            template.setChannelType(SmsTaskAppEnum.ADVERT_SMS.getStatus());
            template.setCode("abcdefg123456789");
            template.setContent("测试模板");
            template.setCreateTime(new Date());
            template.setCreator("user1");
            template.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
            template.setUpdateTime(new Date());
            template.setUpdateUser("user1");
            if (i == 0) {
                template.setType(NumUtil.int2OneByte(SmsTempletTypeEnum.FIXED.getStatusCode()));
            } else {
                template.setType(NumUtil.int2OneByte(SmsTempletTypeEnum.VARIABLE.getStatusCode()));
            }
            smsTempletDao.insert(template);
            DataAuth auth = new DataAuth();
            auth.setTableName("sms_templet");
            auth.setResourceId(Long.valueOf(template.getId()));
            auth.setOrgId(orgId);
            DataAuth authData =
                    dataAuthMapper.selectByTableNameResourceIDOrgId(auth.getTableName(), auth.getOrgId(),
                            auth.getResourceId());
            if (authData == null) {
                auth.setFirsthand(Boolean.TRUE);// 设置直接权限
                auth.setWriteable(Boolean.TRUE);// 设置具有写权限
                auth.setShared(Boolean.FALSE);// 设置是否被分享过
                Date date = new Date();
                auth.setDaCreateTime(date);
                dataAuthMapper.insertSelective(auth);
            }
        }

    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testGetTempletCountByType() {
        List<Map<String, Object>> reslult =
                smsTempletDao.getTempletCountByType(SmsTaskAppEnum.ADVERT_SMS.getStatus().toString(), orgId);
        Assert.assertEquals(2, reslult.size());
        Assert.assertTrue(((Long) reslult.get(0).get("count")) > 1);
        Assert.assertTrue(((Long) reslult.get(1).get("count")) > 1);
    }

}
