package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.mongodb.DataPartyRepository;
import cn.rongcapital.mkt.po.Tag;
import cn.rongcapital.mkt.service.DataPartySyncMongoTaskService;
import cn.rongcapital.mkt.po.DataArchPoint;
import cn.rongcapital.mkt.po.DataCustomerTags;
import cn.rongcapital.mkt.po.DataLogin;
import cn.rongcapital.mkt.po.DataMember;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPayment;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.DataShopping;

//同步数据至Mongodb

@Service
public class DataPartySyncMongoTaskServiceImpl implements TaskService {  
	
	//private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	
    //主数据data_xxx
	@Autowired
    private DataPartyDao dataPartyDao;
    
    @Autowired
    private DataPaymentDao dataPaymentDao;
    
    @Autowired
    private DataCustomerTagsDao dataCustomerTagsDao;
    
    @Autowired
    private DataShoppingDao dataShoppingDao;
    
    @Autowired
    private DataPopulationDao dataPopulationDao;
    
    @Autowired
    private DataArchPointDao dataArchPointDao;
    
    @Autowired
    private DataMemberDao dataMemberDao;
    
    @Autowired
    private DataLoginDao dataLoginDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void task(Integer taskId) {
	    		
	    //1.从mysql data_party表中读取所有mid,keyid
	    DataParty dataParty=new DataParty();	    
	    
	    //分批读出
	    List<DataParty> datapartyList=dataPartyDao.selectList(dataParty); 
	    
	    //2.根据keyid在data_xxx表中查出所有字段插入mongodb data_party表中
	    for(DataParty data_party : datapartyList){
	        
	        Integer data_type=data_party.getMdType();
	        String keyid=data_party.getMappingKeyid();
	        
	        add2Mongo(data_type,keyid);
	        
	    }

	}
	
	private void add2Mongo(int data_type,String keyid){
	    
	    String collection="data_party";
	    
	    //批量插入
	    if(data_type==1){
	        
	        DataPopulation data=new DataPopulation();
	        data.setId(Integer.parseInt(keyid));
	        
	        List<DataPopulation> dataList=dataPopulationDao.selectList(data);
	        
	        //insert into mongodb
	        for(DataPopulation dataObj : dataList){
	            
	            dataObj.setMid(keyid);
	            dataObj.setMd_type("1");
	            dataObj.setMapping_keyid(dataObj.getId()+"");
	            
	            mongoTemplate.insert(dataObj,collection);
	            
	        }
	        	        
	        
	    }else if(data_type==2){
	        
	        DataCustomerTags data=new DataCustomerTags();
            data.setId(Integer.parseInt(keyid));
	        
	        List<DataCustomerTags> dataList=dataCustomerTagsDao.selectList(data);
            
            //insert into mongodb
            for(DataCustomerTags dataObj : dataList){
                dataObj.setMid(keyid);
                dataObj.setMd_type("2");
                dataObj.setMapping_keyid(dataObj.getId()+"");
                mongoTemplate.insert(dataObj,collection);
            }
	        
	    }else if(data_type==3){
	        
	        DataArchPoint data=new DataArchPoint();
            data.setId(Integer.parseInt(keyid));
	        
	        List<DataArchPoint> dataList=dataArchPointDao.selectList(data);
            
            //insert into mongodb
            for(DataArchPoint dataObj : dataList){
                dataObj.setMid(keyid);
                dataObj.setMd_type("3");
                dataObj.setMapping_keyid(dataObj.getId()+"");
                mongoTemplate.insert(dataObj,collection);
            }
        }else if(data_type==4){
            
            DataMember data=new DataMember();
            data.setId(Integer.parseInt(keyid));
            
            List<DataMember> dataList=dataMemberDao.selectList(data);
            
            //insert into mongodb
            for(DataMember dataObj : dataList){
                dataObj.setMid(keyid);
                dataObj.setMd_type("4");
                dataObj.setMapping_keyid(dataObj.getId()+"");
                mongoTemplate.insert(dataObj,collection);
            }
        }else if(data_type==5){
            
            DataLogin data=new DataLogin();
            data.setId(Integer.parseInt(keyid));
            
            List<DataLogin> dataList=dataLoginDao.selectList(data);
            
            //insert into mongodb
            for(DataLogin dataObj : dataList){
                dataObj.setMid(keyid);
                dataObj.setMd_type("5");
                dataObj.setMapping_keyid(dataObj.getId()+"");
                mongoTemplate.insert(dataObj,collection);
            }
        }else if(data_type==6){
            
            DataPayment data=new DataPayment();
            data.setId(Integer.parseInt(keyid));
            
            List<DataPayment> dataList=dataPaymentDao.selectList(data);
            
            //insert into mongodb
            for(DataPayment dataObj : dataList){
                dataObj.setMid(keyid);
                dataObj.setMd_type("6");
                dataObj.setMapping_keyid(dataObj.getId()+"");
                mongoTemplate.insert(dataObj,collection);
            }
        }else if(data_type==7){
            
            DataShopping data=new DataShopping();
            data.setId(Integer.parseInt(keyid));
            
            List<DataShopping> dataList=dataShoppingDao.selectList(data);
            
            //insert into mongodb
            for(DataShopping dataObj : dataList){
                dataObj.setMid(keyid);
                dataObj.setMd_type("7");
                dataObj.setMapping_keyid(dataObj.getId()+"");
                mongoTemplate.insert(dataObj,collection);
            }
        }else{
            ;
        }
	        
	    
	}
	
}
