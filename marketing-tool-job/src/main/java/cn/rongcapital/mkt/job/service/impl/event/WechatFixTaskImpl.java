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

package cn.rongcapital.mkt.job.service.impl.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.po.WechatQrcodeFocus;

@Service
public class WechatFixTaskImpl implements TaskService {

    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private WechatQrcodeFocusDao  wechatQrcodeFocusDao;
    
    
    @Autowired
    private  WechatMemberDao  wechatMemberDao;
    
    
    @Autowired
    private AudienceListPartyMapDao audienceListPartyMapDao;
    
    
    @Autowired
    private AudienceListDao audienceListDao;
    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.job.service.base.TaskService#task(java.lang.Integer)
     */
    @Override
    public void task(Integer taskId) {
        // TODO Auto-generated method stub
        logger.info("微信扫描人群定时任务start....");
        List<WechatQrcodeFocus> dataFocus=wechatQrcodeFocusDao.getWeChatAudienceInfo();
        Map<Integer,List<String>> collect =dataFocus.stream()
                .collect(Collectors.groupingBy(WechatQrcodeFocus::getId,Collectors.mapping(WechatQrcodeFocus::getOpenid, Collectors.toList())));
       AudienceList aulist =new AudienceList();
         doSomeThing(collect,aulist);
        logger.info("微信扫描人群定时任务end....");   
         
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private  void doSomeThing(Map<Integer,List<String>> collect,AudienceList aulist)
    {
        
        for (Entry<Integer, List<String>> entry: collect.entrySet()) {
             Integer temp=entry.getKey();
            logger.info("微信扫描人群ID:{}",temp);
            List<String> te=entry.getValue();
            List<Integer> keyids= wechatMemberDao.getPopulationKeyId(te);
            if(CollectionUtils.isNotEmpty(keyids)) //插入  audience_list_party_map 数据
              doInsert(keyids,temp);
            aulist.setId(temp);
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
