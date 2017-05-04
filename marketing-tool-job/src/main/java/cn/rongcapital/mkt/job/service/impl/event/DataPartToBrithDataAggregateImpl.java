/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年5月4日 
 * @date(最后修改日期)：2017年5月4日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.job.service.impl.event;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;

import cn.rongcapital.mkt.job.service.base.PatchAggregationOperation;
import cn.rongcapital.mkt.job.service.vo.BrithDayData;
import cn.rongcapital.mkt.po.mongodb.DataParty;

@Component
public class DataPartToBrithDataAggregateImpl {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    private Logger logger = LoggerFactory.getLogger(getClass());  
    
    private final Integer[] brithdays={0,2,6,29}; //相对于提前1,3,7,30 天
    
    private ExecutorService executor = Executors.newFixedThreadPool(brithdays.length);
    
    public Map<Integer,AggregationResults<BrithDayData>> getBrithMap()
    {
       
        Map<Integer,AggregationResults<BrithDayData>> bridata= new HashMap<Integer,AggregationResults<BrithDayData>>();
        for(Integer i : brithdays)
        {     
            LocalDate currdate =LocalDate.now();  
            LocalDate tempdata =currdate.plusDays(i); 
            executor.execute(new Runnable(){
                @Override
                public void run() {
                     long begin =System.currentTimeMillis();
                    logger.info("生日关怀开始处理线程:{}",Thread.currentThread().getName());
                    Criteria match =Criteria.where("birthday").ne(null);
                    MatchOperation matchOps = Aggregation.match(match);  //match     
                    BasicDBObject project1 = new BasicDBObject(); 
                    project1.put("birthday","$birthday");
                    project1.put("mid", "$mid");
                    project1.put("name", "$name");
                    project1.put("month", new BasicDBObject("$month","$birthday"));
                    project1.put("day",new BasicDBObject("$dayOfMonth","$birthday"));    
                   AggregationOperation projectOps = new PatchAggregationOperation (new BasicDBObject("$project", project1));
                   Criteria match2 =Criteria.where("month").is(tempdata.getMonthValue()).and("day").is(tempdata.getDayOfMonth());
                   MatchOperation matchOps2 = Aggregation.match(match2);     
                   Aggregation aggregation = Aggregation.newAggregation(matchOps,projectOps,matchOps2);    
                   AggregationResults<BrithDayData> aggrResult =mongoTemplate.aggregate(aggregation, DataParty.class, BrithDayData.class);
                      if(aggrResult!=null && aggrResult.getMappedResults().size()>0)
                      {
                          bridata.put(i,aggrResult); 
                   logger.info("生日关怀处理结束线程{},耗时{},生日提前天数{},数量{}",Thread.currentThread().getName(),System.currentTimeMillis()-begin,i+1,aggrResult.getMappedResults().size()); 
                      }
                    
                }}
            );     
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info("生日关怀取数据结束......");
        return bridata;
    }
    
    
}
