package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataArchPoint;
import cn.rongcapital.mkt.po.DataCustomerTags;
import cn.rongcapital.mkt.po.DataLogin;
import cn.rongcapital.mkt.po.DataMember;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPayment;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.DataShopping;



@Service
public class DataPartySyncTaskServiceImpl implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
		
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
		
	
	@Override
	public void task(Integer taskId) {
	    
	    for(int i=1;i<8;i++){
	    
	        add2DataParty(i);
	    }


	}
	
	private void add2DataParty(int type){
	    
	    //从mysql data_xxx表中读取数据
        List<Integer> idList=new ArrayList<Integer>();
        
        
        if(type==1){            
            List<DataPopulation> dataPopulationList=dataPopulationDao.selectListByIdList(idList); 
            for(DataPopulation dataObj : dataPopulationList){                
                
                DataParty dataParty=new DataParty();                   
                
                dataParty.setMappingKeyid(dataObj.getId()+"");
                dataParty.setMdType(Integer.parseInt("1"));
                dataParty.setMobile(dataObj.getMobile());
                dataParty.setName(dataObj.getName());
                dataParty.setGender(dataObj.getGender());
                dataParty.setBirthday(dataObj.getBirthday());
                dataParty.setProvice(dataObj.getProvice());
                dataParty.setCity(dataObj.getCity());
                dataParty.setJob(dataObj.getJob());
                dataParty.setMonthlyIncome(dataObj.getMonthlyIncome());
                dataParty.setMonthlyConsume(dataObj.getMonthlyConsume());
                dataParty.setSource("1");
                dataParty.setBatchId(Integer.parseInt(dataObj.getBatchId()+""));
                                                
                dataPartyDao.insert(dataParty);
            }            
        }else if(type==2){            
            List<DataCustomerTags> dataCustomerTagsList=dataCustomerTagsDao.selectListByIdList(idList);
            for(DataCustomerTags dataObj : dataCustomerTagsList){                
                
                DataParty dataParty=new DataParty();        
                dataParty.setMappingKeyid(dataObj.getId()+""); 
                dataParty.setMdType(Integer.parseInt("2"));                
                dataParty.setSource("2");
                dataParty.setBatchId(Integer.parseInt(dataObj.getBatchId()+""));
            
                dataPartyDao.insert(dataParty);
            } 
            
        }else if(type==3){
            List<DataArchPoint> dataArchPointList=dataArchPointDao.selectListByIdList(idList);
            for(DataArchPoint dataObj : dataArchPointList){                
                
                DataParty dataParty=new DataParty();        
                dataParty.setMappingKeyid(dataObj.getId()+"");
                dataParty.setMdType(Integer.parseInt("3"));                
                dataParty.setSource("3");
                dataParty.setBatchId(Integer.parseInt(dataObj.getBatchId()+""));
                dataPartyDao.insert(dataParty);
            }
        }else if(type==4){
            List<DataMember> dataMemberList=dataMemberDao.selectListByIdList(idList);            
            for(DataMember dataObj : dataMemberList){                
                
                DataParty dataParty=new DataParty();        
                dataParty.setMappingKeyid(dataObj.getId()+"");
                dataParty.setMdType(Integer.parseInt("4"));
                dataParty.setSource("4");
                dataParty.setBatchId(Integer.parseInt(dataObj.getBatchId()+""));
            
                dataPartyDao.insert(dataParty);
            }
        }else if(type==5){
            List<DataLogin> dataLoginList=dataLoginDao.selectListByIdList(idList);
            for(DataLogin dataObj : dataLoginList){                
                
                DataParty dataParty=new DataParty();        
                dataParty.setMappingKeyid(dataObj.getId()+"");
                dataParty.setMdType(Integer.parseInt("5")); 
                dataParty.setSource("5");
                dataParty.setBatchId(Integer.parseInt(dataObj.getBatchId()+""));
            
                dataPartyDao.insert(dataParty);
            }
        }else if(type==6){
            List<DataPayment> dataPaymentList=dataPaymentDao.selectListByIdList(idList);
            for(DataPayment dataObj : dataPaymentList){                
                
                DataParty dataParty=new DataParty();        
                dataParty.setMappingKeyid(dataObj.getId()+"");
                dataParty.setMdType(Integer.parseInt("6"));
                dataParty.setSource("6");
                dataParty.setBatchId(Integer.parseInt(dataObj.getBatchId()+""));
            
                dataPartyDao.insert(dataParty);
            }
        }else if(type==7){
            List<DataShopping> dataShoppingList=dataShoppingDao.selectListByIdList(idList);
            for(DataShopping dataObj : dataShoppingList){                
                
                DataParty dataParty=new DataParty();        
                dataParty.setMappingKeyid(dataObj.getId()+"");
                dataParty.setMdType(Integer.parseInt("7"));
                dataParty.setSource("7");
                dataParty.setBatchId(Integer.parseInt(dataObj.getBatchId()+""));
            
                dataPartyDao.insert(dataParty);
            }
        }else{
            logger.debug("Invalid Type="+type);;
        }
        
	}
	
	
}
