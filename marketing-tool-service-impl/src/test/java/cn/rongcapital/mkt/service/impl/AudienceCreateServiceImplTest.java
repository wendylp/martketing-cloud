/*************************************************
 * @功能简述: 保存固定人群服务测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/4/12
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.AudienceCreateIn;

@RunWith(PowerMockRunner.class)
public class AudienceCreateServiceImplTest {

    @Mock
    private AudienceListDao audienceListDao;

    @Mock
    private AudienceListPartyMapDao audienceListPartyMapDao;

    @InjectMocks
    private AudienceCreateServiceImpl audienceCreateService;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    /**
     * 目标人群名称重复
     */
    @Test
    public void testCreateAudience01() {
        PowerMockito.when(audienceListDao.selectListCount(Mockito.any())).thenReturn(1);
        AudienceCreateIn in = new AudienceCreateIn();
        in.setName("XX");
        in.setSource("XXXX");
        List<Integer> details = new ArrayList<Integer>();
        details.add(13);
        details.add(14);
        in.setDetails(details);
        BaseOutput output = audienceCreateService.createAudience(in);
        Assert.assertEquals(output.getCode(), ApiErrorCode.VALIDATE_ERROR.getCode());
        Assert.assertEquals(output.getMsg(), "目标人群名称重复");
    }

    /**
     * 目标人群名称重复
     */
    @Test
    public void testCreateAudience02() {
        PowerMockito.when(audienceListDao.selectListCount(Mockito.any())).thenReturn(0);
        AudienceCreateIn in = new AudienceCreateIn();
        in.setName("XX");
        in.setSource("XXXX");
        List<Integer> details = new ArrayList<Integer>();
        details.add(13);
        details.add(14);
        in.setDetails(details);
        PowerMockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                AudienceList arg = invocation.getArgumentAt(0, AudienceList.class);
                Assert.assertEquals(arg.getAudienceName(), "XX");
                Assert.assertEquals(arg.getAudienceRows().intValue(), 2);
                Assert.assertEquals(arg.getSource(), "XXXX");
                arg.setId(999);
                return null;
            }
        }).when(audienceListDao).insert(Mockito.any());

        PowerMockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> arg = invocation.getArgumentAt(0, List.class);
                Assert.assertEquals(arg.size(), 2);

                Assert.assertEquals(arg.get(0).get("audience_list_id"), 999);
                Assert.assertEquals(arg.get(0).get("party_id"), 13);
                Assert.assertNotNull(arg.get(0).get("create_time"));

                Assert.assertEquals(arg.get(1).get("audience_list_id"), 999);
                Assert.assertEquals(arg.get(1).get("party_id"), 14);
                Assert.assertNotNull(arg.get(1).get("create_time"));
                return null;
            }
        }).when(audienceListPartyMapDao).batchInsert(Mockito.any());

        BaseOutput output = audienceCreateService.createAudience(in);
        Assert.assertEquals(output.getCode(), ApiErrorCode.SUCCESS.getCode());
        Assert.assertEquals(output.getMsg(), ApiErrorCode.SUCCESS.getMsg());
    }

}
