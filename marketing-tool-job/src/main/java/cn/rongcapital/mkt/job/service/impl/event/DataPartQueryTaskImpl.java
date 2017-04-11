/*************************************************
 * @功能及特点的描述简述: 查询mgdatapart主数据识别生日提醒数据
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年4月11日 
 * @date(最后修改日期)：2017年4月11日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.job.service.impl.event;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import cn.rongcapital.mkt.po.mongodb.DataParty;

public class DataPartQueryTaskImpl {

    private Logger logger = LoggerFactory.getLogger(getClass());  
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    private final int[] brithdays={1,3,7,31};
    
    private  LocalDate currdate =LocalDate.now();
    
    private final int pageSizeCnt=100;
    
    
    private ExecutorService executor = Executors.newCachedThreadPool();
    
    
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
    
    public  Map<Integer, List<DataParty>> getDataBritDay()
    {
        logger.info("生日提醒数据准备开始....");
        long begin=System.currentTimeMillis();
        Map<Integer, List<DataParty>> mapdata = new HashMap<Integer, List<DataParty>>();
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
            logger.error("DataPartQueryTaskImpl is error");
            executor.shutdownNow();
            e1.printStackTrace();
        }
        
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
                    logger.error(" 生日提醒数据合并出错 ...");
                    executor.shutdownNow();
                    e.printStackTrace();
                    
                }
            }
            
        }
        
        logger.info("生日提醒数据准备Suess end 耗时....{}",(System.currentTimeMillis()-begin));
         return mapdata;
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
