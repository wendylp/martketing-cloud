package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.mongodb.DataPartyRepository;
import cn.rongcapital.mkt.service.DataPartySyncMongoTaskService;
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
    private WechatMemberDao wechatMemberDao;
	
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
                add2Mongo(tempDataParty.getMdType(), tempDataParty.getMappingKeyid(), tempDataParty.getId());
            }

            dataPartyDao.updateStatusByIds(dataPartyIdList, StatusEnum.PROCESSED.getStatusCode());
        }
	}
	
	private void add2Mongo(Integer dataType,String dataKeyId, Integer dataPartyId){
	    
	    String collection="data_party";
	    Integer dataKeyIdInteger = Integer.valueOf(dataKeyId);
	    //批量插入
	    if(dataType.intValue() == 1){
	        
	        DataPopulation data=new DataPopulation();
	        data.setId(dataKeyIdInteger);
	        
	        List<DataPopulation> dataList=dataPopulationDao.selectList(data);
	        //insert into mongodb
	        for(DataPopulation dataObj : dataList){
                cn.rongcapital.mkt.po.mongodb.DataPopulation mongoDataPopulation =
                        new cn.rongcapital.mkt.po.mongodb.DataPopulation();
                BeanUtils.copyProperties(dataObj, mongoDataPopulation);
                mongoDataPopulation.setMid(dataPartyId);
                mongoDataPopulation.setMd_type(dataType);
                mongoDataPopulation.setMapping_keyid(dataObj.getId().toString());
                mongoTemplate.insert(mongoDataPopulation, collection);
	        }

	    }else if(dataType.intValue()==2){
	        
	        DataCustomerTags data=new DataCustomerTags();
            data.setId(dataKeyIdInteger);
	        
	        List<DataCustomerTags> dataList=dataCustomerTagsDao.selectList(data);
            
            //insert into mongodb
            for(DataCustomerTags dataObj : dataList){
                cn.rongcapital.mkt.po.mongodb.DataCustomerTags mongoDataCustomerTags =
                        new cn.rongcapital.mkt.po.mongodb.DataCustomerTags();
                BeanUtils.copyProperties(dataObj, mongoDataCustomerTags);
                mongoDataCustomerTags.setMid(dataPartyId);
                mongoDataCustomerTags.setMd_type(dataType);
                mongoDataCustomerTags.setMapping_keyid(dataObj.getId().toString());
                mongoTemplate.insert(mongoDataCustomerTags,collection);
            }
	        
	    }else if(dataType.intValue()==3){
	        
	        DataArchPoint data=new DataArchPoint();
            data.setId(dataKeyIdInteger);
	        
	        List<DataArchPoint> dataList=dataArchPointDao.selectList(data);
            
            //insert into mongodb
            for(DataArchPoint dataObj : dataList){
                cn.rongcapital.mkt.po.mongodb.DataArchPoint mongoDataArchPoint =
                        new cn.rongcapital.mkt.po.mongodb.DataArchPoint();
                BeanUtils.copyProperties(dataObj, mongoDataArchPoint);
                mongoDataArchPoint.setMid(dataPartyId);
                mongoDataArchPoint.setMd_type(dataType);
                mongoDataArchPoint.setMapping_keyid(dataObj.getId().toString());
                mongoTemplate.insert(mongoDataArchPoint,collection);
            }
        }else if(dataType.intValue()==4){
            
            DataMember data=new DataMember();
            data.setId(dataKeyIdInteger);
            
            List<DataMember> dataList=dataMemberDao.selectList(data);
            
            //insert into mongodb
            for(DataMember dataObj : dataList){
                cn.rongcapital.mkt.po.mongodb.DataMember mongoDataMember =
                        new cn.rongcapital.mkt.po.mongodb.DataMember();
                BeanUtils.copyProperties(dataObj, mongoDataMember);
                mongoDataMember.setMid(dataPartyId);
                mongoDataMember.setMd_type(dataType);
                mongoDataMember.setMapping_keyid(dataObj.getId().toString());
                mongoTemplate.insert(mongoDataMember,collection);
            }
        }else if(dataType.intValue()==5){
            
            DataLogin data=new DataLogin();
            data.setId(dataKeyIdInteger);
            
            List<DataLogin> dataList=dataLoginDao.selectList(data);
            
            //insert into mongodb
            for(DataLogin dataObj : dataList){
                cn.rongcapital.mkt.po.mongodb.DataLogin mongoDataLogin =
                        new cn.rongcapital.mkt.po.mongodb.DataLogin();
                BeanUtils.copyProperties(dataObj, mongoDataLogin);
                mongoDataLogin.setMid(dataPartyId);
                mongoDataLogin.setMd_type(dataType);
                mongoDataLogin.setMapping_keyid(dataObj.getId().toString());
                mongoTemplate.insert(mongoDataLogin,collection);
            }
        }else if(dataType.intValue()==6){
            
            DataPayment data=new DataPayment();
            data.setId(dataKeyIdInteger);
            
            List<DataPayment> dataList=dataPaymentDao.selectList(data);
            
            //insert into mongodb
            for(DataPayment dataObj : dataList){
                cn.rongcapital.mkt.po.mongodb.DataPayment mongoDataPayment =
                        new cn.rongcapital.mkt.po.mongodb.DataPayment();
                BeanUtils.copyProperties(dataObj, mongoDataPayment);
                mongoDataPayment.setMid(dataPartyId);
                mongoDataPayment.setMd_type(dataType);
                mongoDataPayment.setMapping_keyid(dataObj.getId().toString());
                mongoTemplate.insert(mongoDataPayment,collection);
            }
        }else if(dataType.intValue()==7){
            
            DataShopping data=new DataShopping();
            data.setId(dataKeyIdInteger);
            List<DataShopping> dataList=dataShoppingDao.selectList(data);
            
            //insert into mongodb
            for(DataShopping dataObj : dataList){
                cn.rongcapital.mkt.po.mongodb.DataShopping mongoDataShopping =
                        new cn.rongcapital.mkt.po.mongodb.DataShopping();
                BeanUtils.copyProperties(dataObj, mongoDataShopping);
                mongoDataShopping.setMid(dataPartyId);
                mongoDataShopping.setMd_type(dataType);
                mongoDataShopping.setMapping_keyid(dataObj.getId().toString());
                mongoTemplate.insert(mongoDataShopping,collection);
            }
        }else if(dataType.intValue() == 8){

            WechatMember wechatMember = new WechatMember();
            wechatMember.setId(Long.valueOf(dataKeyIdInteger));
            List<WechatMember> dataList=wechatMemberDao.selectList(wechatMember);

            //insert into mongodb
            for(WechatMember dataObj : dataList){
                cn.rongcapital.mkt.po.mongodb.WechatMember mongoWechatMember =
                        new cn.rongcapital.mkt.po.mongodb.WechatMember();
                BeanUtils.copyProperties(dataObj, mongoWechatMember);
                mongoWechatMember.setMid(dataPartyId);
                mongoWechatMember.setMd_type(dataType);
                mongoWechatMember.setMapping_keyid(dataObj.getId().toString());
                mongoTemplate.insert(mongoWechatMember,collection);
            }

        }
	}
	
}
