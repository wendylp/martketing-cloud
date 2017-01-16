/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的） 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统) @author:liuhaizhan
 * @version: 版本v1.6 @date(创建、开发日期)：2017年1月9日 @date(最后修改日期)：2017年1月9日 @复审人：
 *************************************************/

package cn.rongcapital.mkt.dao.event;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.event.po.EventPropValue;


@RunWith(SpringJUnit4ClassRunner.class)
public class EventProValueTest extends AbstractUnitTest {

    @Autowired
    private EventObjectPropValueDao eventObjectPropValueDao;

    private EventPropValue epv =new EventPropValue();

    private long Objid = 2255;
     
    private List<EventPropValue> list =new ArrayList<EventPropValue>();
    @Before
    public void set() {
        epv.setObjectId(Objid);
        epv.setPropName("code_name");
        epv.setPropValue("優惠券");
        list.add(epv);
        eventObjectPropValueDao.insertBatchPropValue(list);
    }

    @Test
    public void test() {
        List<EventPropValue> ep = eventObjectPropValueDao.selectByObjectId(Objid);
        Assert.assertEquals(epv.getPropName(),((EventPropValue)ep.get(0)).getPropName());
        
    }



}
