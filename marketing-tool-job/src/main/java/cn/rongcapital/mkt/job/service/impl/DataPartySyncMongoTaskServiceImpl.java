package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import org.springframework.util.CollectionUtils;

//同步数据至Mongodb

@Service
public class DataPartySyncMongoTaskServiceImpl implements TaskService {  
	
	//private Logger logger = LoggerFactory.getLogger(getClass());

    private int BATCH_SIZE = 500;
	
	
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
        dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
        int totalCount = dataPartyDao.selectListCount(dataParty);
        if (totalCount < 1) {
            return;
        }
        int totalPages = (totalCount + BATCH_SIZE - 1) / BATCH_SIZE;
        dataParty.setPageSize(Integer.valueOf(BATCH_SIZE));
        for (int i = 0; i < totalPages; i++) {
            dataParty.setStartIndex(Integer.valueOf(i * BATCH_SIZE));
            List<DataParty> datapartyList=dataPartyDao.selectList(dataParty);
            if (CollectionUtils.isEmpty(datapartyList)) {
                return;
            }

            //2.根据keyid在data_xxx表中查出所有字段插入mongodb data_party表中
            List<Integer> dataPartyIdList = new ArrayList<>();
            for(DataParty tempDataParty : datapartyList){
                dataPartyIdList.add(tempDataParty.getId());
                add2Mongo(tempDataParty.getMdType(), tempDataParty.getMappingKeyid());
            }

            dataPartyDao.updateStatusByIds(dataPartyIdList, StatusEnum.PROCESSED.getStatusCode());
        }
	}
	
	private void add2Mongo(int dataType,String keyid){
	    
	    String collection="data_party";
	    
	    //批量插入
	    if(dataType==1){
	        
	        DataPopulation data=new DataPopulation();
	        data.setId(Integer.parseInt(keyid));
	        
	        List<DataPopulation> dataList=dataPopulationDao.selectList(data);
	        //insert into mongodb
	        for(DataPopulation dataObj : dataList){
	            dataObj.setMid(keyid);
	            dataObj.setMd_type("1");
	            dataObj.setMapping_keyid(dataObj.getId()+"");
                cn.rongcapital.mkt.po.mongodb.DataPopulation mongoDataPopulation =
                        new cn.rongcapital.mkt.po.mongodb.DataPopulation();
                BeanUtils.copyProperties(dataObj, mongoDataPopulation);
                mongoTemplate.insert(mongoDataPopulation, collection);
	        }

	    }else if(dataType==2){
	        
	        DataCustomerTags data=new DataCustomerTags();
            data.setId(Integer.parseInt(keyid));
	        
	        List<DataCustomerTags> dataList=dataCustomerTagsDao.selectList(data);
            
            //insert into mongodb
            for(DataCustomerTags dataObj : dataList){
                dataObj.setMid(keyid);
                dataObj.setMd_type("2");
                dataObj.setMapping_keyid(dataObj.getId()+"");
                cn.rongcapital.mkt.po.mongodb.DataCustomerTags mongoDataCustomerTags =
                        new cn.rongcapital.mkt.po.mongodb.DataCustomerTags();
                BeanUtils.copyProperties(dataObj, mongoDataCustomerTags);
                mongoTemplate.insert(mongoDataCustomerTags,collection);
            }
	        
	    }else if(dataType==3){
	        
	        DataArchPoint data=new DataArchPoint();
            data.setId(Integer.parseInt(keyid));
	        
	        List<DataArchPoint> dataList=dataArchPointDao.selectList(data);
            
            //insert into mongodb
            for(DataArchPoint dataObj : dataList){
                dataObj.setMid(keyid);
                dataObj.setMd_type("3");
                dataObj.setMapping_keyid(dataObj.getId()+"");
                cn.rongcapital.mkt.po.mongodb.DataArchPoint mongoDataArchPoint =
                        new cn.rongcapital.mkt.po.mongodb.DataArchPoint();
                BeanUtils.copyProperties(dataObj, mongoDataArchPoint);
                mongoTemplate.insert(mongoDataArchPoint,collection);
            }
        }else if(dataType==4){
            
            DataMember data=new DataMember();
            data.setId(Integer.parseInt(keyid));
            
            List<DataMember> dataList=dataMemberDao.selectList(data);
            
            //insert into mongodb
            for(DataMember dataObj : dataList){
                dataObj.setMid(keyid);
                dataObj.setMd_type("4");
                dataObj.setMapping_keyid(dataObj.getId()+"");
                cn.rongcapital.mkt.po.mongodb.DataMember mongoDataMember =
                        new cn.rongcapital.mkt.po.mongodb.DataMember();
                BeanUtils.copyProperties(dataObj, mongoDataMember);
                mongoTemplate.insert(mongoDataMember,collection);
            }
        }else if(dataType==5){
            
            DataLogin data=new DataLogin();
            data.setId(Integer.parseInt(keyid));
            
            List<DataLogin> dataList=dataLoginDao.selectList(data);
            
            //insert into mongodb
            for(DataLogin dataObj : dataList){
                dataObj.setMid(keyid);
                dataObj.setMd_type("5");
                dataObj.setMapping_keyid(dataObj.getId()+"");
                cn.rongcapital.mkt.po.mongodb.DataLogin mongoDataLogin =
                        new cn.rongcapital.mkt.po.mongodb.DataLogin();
                BeanUtils.copyProperties(dataObj, mongoDataLogin);
                mongoTemplate.insert(mongoDataLogin,collection);
            }
        }else if(dataType==6){
            
            DataPayment data=new DataPayment();
            data.setId(Integer.parseInt(keyid));
            
            List<DataPayment> dataList=dataPaymentDao.selectList(data);
            
            //insert into mongodb
            for(DataPayment dataObj : dataList){
                dataObj.setMid(keyid);
                dataObj.setMd_type("6");
                dataObj.setMapping_keyid(dataObj.getId()+"");
                cn.rongcapital.mkt.po.mongodb.DataPayment mongoDataPayment =
                        new cn.rongcapital.mkt.po.mongodb.DataPayment();
                BeanUtils.copyProperties(dataObj, mongoDataPayment);
                mongoTemplate.insert(mongoDataPayment,collection);
            }
        }else if(dataType==7){
            
            DataShopping data=new DataShopping();
            data.setId(Integer.parseInt(keyid));
            
            List<DataShopping> dataList=dataShoppingDao.selectList(data);
            
            //insert into mongodb
            for(DataShopping dataObj : dataList){
                dataObj.setMid(keyid);
                dataObj.setMd_type("7");
                dataObj.setMapping_keyid(dataObj.getId()+"");
                cn.rongcapital.mkt.po.mongodb.DataShopping mongoDataShopping =
                        new cn.rongcapital.mkt.po.mongodb.DataShopping();
                BeanUtils.copyProperties(dataObj, mongoDataShopping);
                mongoTemplate.insert(mongoDataShopping,collection);
            }
        }else{
            ;
        }
	        
	    
	}
	
}
