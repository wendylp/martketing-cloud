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
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataPartyRepository dataPartyRepository;
	
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
	    //dataParty.setStatus(0);	    
	    
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
	    
	    //data_entity
        List<Integer> idList=new ArrayList<Integer>();
        idList.add(Integer.parseInt(keyid));
	    
	    //批量插入
	    if(data_type==1){
	        
	        List<DataPopulation> dataList=dataPopulationDao.selectListByIdList(idList);
	        
	        //insert into mongodb
	        for(DataPopulation dataObj : dataList){
	            
	            mongoTemplate.insert(dataObj);
	            
	        }
	        	        
	        
	    }else if(data_type==2){
	        
	        List<DataCustomerTags> dataList=dataCustomerTagsDao.selectListByIdList(idList);
            
            //insert into mongodb
            for(DataCustomerTags dataObj : dataList){
                mongoTemplate.insert(dataObj);
            }
	        
	    }else if(data_type==3){
	        
	        List<DataArchPoint> dataList=dataArchPointDao.selectListByIdList(idList);
            
            //insert into mongodb
            for(DataArchPoint dataObj : dataList){
                mongoTemplate.insert(dataObj);
            }
        }else if(data_type==4){
            
            List<DataMember> dataList=dataMemberDao.selectListByIdList(idList);
            
            //insert into mongodb
            for(DataMember dataObj : dataList){
                mongoTemplate.insert(dataObj);
            }
        }else if(data_type==5){
            
            List<DataLogin> dataList=dataLoginDao.selectListByIdList(idList);
            
            //insert into mongodb
            for(DataLogin dataObj : dataList){
                mongoTemplate.insert(dataObj);
            }
        }else if(data_type==6){
            
            List<DataPayment> dataList=dataPaymentDao.selectListByIdList(idList);
            
            //insert into mongodb
            for(DataPayment dataObj : dataList){
                mongoTemplate.insert(dataObj);
            }
        }else if(data_type==7){
            
            List<DataShopping> dataList=dataShoppingDao.selectListByIdList(idList);
            
            //insert into mongodb
            for(DataShopping dataObj : dataList){
                mongoTemplate.insert(dataObj);
            }
        }else{
            ;
        }
	        
	    
	}
	
}
