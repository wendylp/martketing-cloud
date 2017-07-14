/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年7月4日 
 * @date(最后修改日期)：2017年7月4日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.job.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.po.WechatQrcodeFocus;
import cn.rongcapital.mkt.testbase.AbstractUnitTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class WechatFixTaskTest extends AbstractUnitTest {

    @Autowired
    private WechatQrcodeFocusDao  wechatQrcodeFocusDao;
    
    @Autowired
    private  WechatMemberDao  wechatMemberDao;
    
    
    @Autowired
    private AudienceListPartyMapDao audienceListPartyMapDao;
    
    
    @Autowired
    private AudienceListDao audienceListDao;
    
    @Autowired
    private TaskService wechatFixTaskImpl;
    
     //@Test
    //@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void test()
    {
        List<WechatQrcodeFocus> dataFocus=wechatQrcodeFocusDao.getWeChatAudienceInfo();
        Map<Integer,List<String>> collect =dataFocus.stream()
                .collect(Collectors.groupingBy(WechatQrcodeFocus::getId,Collectors.mapping(WechatQrcodeFocus::getOpenid, Collectors.toList())));
        AudienceList aulist =new AudienceList();
        
        doSomeThing(collect,aulist);
        //然后把数据需要分组插入到 audience_list_party_map 表中,audience_list_id=id  需要等到  wechat_member 表也就 同步完成
          //也就是selected =1 
        //party_id= select b.keyid from  wechat_member where selected = 1  a join 
        //(select id,keyid from  data_population where status=2 and source='公众号'  ) b
        //on a.keyid=b.id; 
        
    }
    @Test
    public void t()
    {
        wechatFixTaskImpl.task(1);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private  void doSomeThing(Map<Integer,List<String>> collect,AudienceList aulist)
    {
        
        for (Entry<Integer, List<String>> entry: collect.entrySet()) {

            System.out.println("分组 :"+entry.getKey());
            List<String> te=entry.getValue();
            List<Integer> keyids= wechatMemberDao.getPopulationKeyId(te);
            if(CollectionUtils.isNotEmpty(keyids)) //插入  audience_list_party_map 数据
              doInsert(keyids,entry.getKey());
            aulist.setId(entry.getKey());
            aulist.setAudienceRows(keyids.size());
            aulist.setUpdateTime(new Date(System.currentTimeMillis()));
            audienceListDao.updateById(aulist);  //更新 audience_list表中的audience_rows
           
        }
        
    }
    
    private void doInsert(List<Integer> keyids,int key)
    {
        
        List<Map<String,Object>> paramInsertLists = new ArrayList<Map<String,Object>>();
        Map<String,Object> paramMap ;
        for(Integer populationKeyid : keyids){
                paramMap = new HashMap<String,Object>();
                System.out.println("keyid:"+populationKeyid);
                paramMap.put("audience_list_id",key);
                paramMap.put("party_id",populationKeyid);
                paramMap.put("create_time",new Date(System.currentTimeMillis()));
                paramInsertLists.add(paramMap);
            
        }
        
        if(paramInsertLists != null && paramInsertLists.size() > 0){
            audienceListPartyMapDao.batchInsert(paramInsertLists);
        }
        
        
    }
    
}
