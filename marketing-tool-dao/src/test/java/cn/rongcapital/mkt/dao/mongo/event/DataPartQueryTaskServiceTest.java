/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年4月11日 
 * @date(最后修改日期)：2017年4月11日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.dao.mongo.event;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.mongodb.DataParty;


@RunWith(SpringJUnit4ClassRunner.class)
public class DataPartQueryTaskServiceTest extends AbstractUnitTest {
    
    
    private Logger logger = LoggerFactory.getLogger(getClass());
   
    @Autowired
    private MongoTemplate mongoTemplate;
    
    private final int[] brithdays={1,3,7,31};
    
    private  LocalDate currdate =LocalDate.now();
    
    private ExecutorService executor = Executors.newCachedThreadPool();
    
    private int pageSizeCnt=100;
    
    
    public void set()
    {
        executor=Executors.newCachedThreadPool();
    }
     
    @Test
    public void test()
    {

        long begin =System.currentTimeMillis();
        Query query = new Query(Criteria.where("birthday").exists(true));
        Date date = null;
        LocalDate ldate = null;
        MonthDay md = null;
        long count =mongoTemplate.count(query, DataParty.class);
        int pageSize = (int) Math.ceil(count / (float) pageSizeCnt);
        List<Future<List<DataParty>>> resultList = new ArrayList<>();
        for (int i = 1; i <= pageSize; i++) {
            final int index = i;
            Future<List<DataParty>> dataparty=executor.submit(new Callable<List<DataParty>>() {
                @Override
                public List<DataParty> call() throws Exception {
                    Query query = new Query(Criteria.where("birthday").exists(true));
                    query.skip((index - 1) * pageSizeCnt).limit(pageSizeCnt);
                    List<DataParty> dataPartys = mongoTemplate.find(query, DataParty.class);
                    return dataPartys;
                }

            });
            resultList.add(dataparty);
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(3, TimeUnit.HOURS);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Map<Integer,List<DataParty>> mapdata =new HashMap<Integer,List<DataParty>>();
        if(CollectionUtils.isNotEmpty(resultList))
        {
            for(Future<List<DataParty>> dp : resultList)
            {
                try {
                    List<DataParty> datatemp=dp.get();
                    for(DataParty dpy:datatemp)
                    {
                        date = dpy.getBirthday();
                        ldate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        md = MonthDay.of(ldate.getMonth(), ldate.getDayOfMonth());
                        int dayIndex = getBritdayIndex(md);
                        if(dayIndex>=0)
                        {
                           if(mapdata.containsKey(dayIndex))
                           {
                               mapdata.get(dayIndex).add(dpy);
                           }else
                           {
                               List<DataParty> list =new ArrayList<DataParty>();
                               list.add(dpy);
                               mapdata.put(dayIndex, list);
                           }
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    // TODO Auto-generated catch block
                    logger.error("threadpool is error !!! ");
                    executor.shutdownNow();
                    e.printStackTrace();
                }
            }
            
            for(Map.Entry<Integer,List<DataParty>> entry:mapdata.entrySet()){
                List<DataParty> list = entry.getValue();
                for(DataParty dp:list)
                {
                    logger.info("提前的天数{},生日日期是:{},主MID:{}",entry.getKey(),dp.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),dp.getMid());    
                }
                
            }
            
            
        }
        
        long end =System.currentTimeMillis();
        logger.info("并行查询同时处理合并最后整合计算完成花费时间{}",(end-begin));
    
    }
    
    @Test
    public void test2()
    {
         long begin =System.currentTimeMillis();
        Query query = new Query(Criteria.where("birthday").exists(true));
        long count = mongoTemplate.count(query, DataParty.class);
        int pageSize = (int) Math.ceil(count / (float) pageSizeCnt);
        List<Future<Map<Integer,List<DataParty>>>> resultList = new ArrayList<>();
        for (int i = 1; i <= pageSize; i++) {
            final int index = i;
            Future<Map<Integer,List<DataParty>>> dataparty = executor.submit(new Callable<Map<Integer,List<DataParty>>>() {
                @Override
                public Map<Integer,List<DataParty>> call() throws Exception {
                    Query query = new Query(Criteria.where("birthday").exists(true));
                    query.skip((index - 1) * pageSizeCnt).limit(pageSizeCnt);
                    List<DataParty> dataPartys = mongoTemplate.find(query, DataParty.class);
                    Map<Integer,List<DataParty>> mapdata=getBritDays(dataPartys);
                    return mapdata;
                }

            });
            resultList.add(dataparty);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(3, TimeUnit.HOURS);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Map<Integer, List<DataParty>> mapdata = new HashMap<Integer, List<DataParty>>();
       
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Future<Map<Integer,List<DataParty>>> mapdp  : resultList) {
                try {
                    Map<Integer,List<DataParty>> datatemp = mapdp.get();
                    for (Map.Entry<Integer, List<DataParty>> entry : datatemp.entrySet()) {
                         if(mapdata.containsKey(entry.getKey()))
                         {
                             mapdata.get(entry.getKey()).addAll(entry.getValue());
                         }else
                         {
                             mapdata.put(entry.getKey(),entry.getValue());
                             
                         }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    // TODO Auto-generated catch block
                    executor.shutdownNow();
                    e.printStackTrace();
                }
            }

            for(Map.Entry<Integer,List<DataParty>> entry:mapdata.entrySet()){
                List<DataParty> list = entry.getValue();
                for(DataParty dp:list)
                {
                    logger.info("提前的天数{},生日日期是:{},主MID:{}",entry.getKey(),dp.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),dp.getMid());    
                }
                
            }


        }
        
        long end =System.currentTimeMillis();
        logger.info("并行查询后处理合并计算完成花费时间{}",(end-begin));
        
    }
    
    //@Test
    private int getBritdayIndex(MonthDay monday) {
        int result =-1;
        for (int i : brithdays) {
            LocalDate  temp = currdate.plusDays(i);
            if (monday.equals(MonthDay.from(temp))) {
                return i;
            }
        }
        return result;

    }
    
    private Map<Integer,List<DataParty>> getBritDays(List<DataParty> list)
    {
        Map<Integer,List<DataParty>> mapdata =new HashMap<Integer, List<DataParty>>();
        Date date = null;
        LocalDate ldate = null;
        MonthDay md = null;
        for(DataParty dp :list)
        {
            date = dp.getBirthday();
            ldate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            md = MonthDay.of(ldate.getMonth(), ldate.getDayOfMonth());
            int dayIndex = getBritdayIndex(md);
            if (dayIndex >= 0) {
                if (mapdata.containsKey(dayIndex)) {
                    mapdata.get(dayIndex).add(dp);
                } else {
                    List<DataParty> tlist = new ArrayList<DataParty>();
                    tlist.add(dp);
                    mapdata.put(dayIndex, tlist);
                }
            }
        }
        return mapdata;
    }

    
}
