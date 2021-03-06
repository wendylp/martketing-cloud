/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年5月3日 
 * @date(最后修改日期)：2017年5月3日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.job.test.eventjop;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;

import cn.rongcapital.mkt.job.service.base.PatchAggregationOperation;
import cn.rongcapital.mkt.job.service.vo.BrithDayData;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.testbase.AbstractUnitTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class NewBrithDay extends AbstractUnitTest {

    enum Brithday
    {
       one("1天",0), three("3天",2),seven("7天",6),tNine("30天",29);
       private String day;
       private int datas;
       private Brithday(String day,int datas)
       {
           this.day=day;
           this.datas=datas;
       }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public int getDatas() {
        return datas;
    }
    public void setDatas(int datas) {
        this.datas = datas;
    }
       
    }
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    private final Integer[] brithdays={0,2,6,29}; //相对于提前1,3,7,30 天
    
    private ExecutorService executor = Executors.newFixedThreadPool(4);
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Test
   public void test()
    {
        EnumMap<Brithday,AggregationResults<BrithDayData>> eMap=new EnumMap<Brithday, AggregationResults<BrithDayData>>(
                Brithday.class);
         
        for (Brithday emItem : Brithday.values()) {
            LocalDate currdate =LocalDate.now();  
            LocalDate tempdata =currdate.plusDays(emItem.datas); 
            executor.execute(new Runnable(){
                @Override
                public void run() {
                     long begin =System.currentTimeMillis();
                    logger.info("当前线程{},开始时间{}",Thread.currentThread().getName(),begin);
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
                            eMap.put(emItem, aggrResult);
                   logger.info("当前线程{},耗时{},生日提前{},数量{}",Thread.currentThread().getName(),System.currentTimeMillis()-begin,emItem.day,aggrResult.getMappedResults().size()); 
                      }
                    
                }}
            );
        }
        
      
       
        //Aggregation aggregation = Aggregation.newAggregation(matchOps,projectOps,matchOps2,Aggregation.skip(2),Aggregation.limit(1)); //可以添加分页
      /* String jsonCommand="{\"aggregate\":\"data_party\",\"pipeline\":[{\"$match\":{\"birthday\":{$ne:null }}},"+
               "{\"$project\": {\"month\": {$month: \"$birthday\" },\"day\": {$dayOfMonth:\"$birthday\"}}},"+
             "{\"$match\":{\"month\":{$eq:5},\"day\":{$eq:5}}},{\"$count\":\"mid\"}]}";  //查询每天总数
       CommandResult commandResult =mongoTemplate.executeCommand(jsonCommand);
       String jsonResult = commandResult.getString("result");
       System.out.println(jsonResult);*/
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Map.Entry<Brithday, AggregationResults<BrithDayData>> entry : eMap.entrySet()) {
            System.out.println("提前"+entry.getKey().day);
            List<BrithDayData>  aggregationResults =entry.getValue().getMappedResults();
            for(BrithDayData bdd:aggregationResults)
            {
                System.out.println("mid:"+bdd.getMid()+"生日日期为："+bdd.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    +" 月份："+bdd.getMonth()+"日期 :"+bdd.getDay());
            }
            
        }
            
        
    }
    
    
    
}
