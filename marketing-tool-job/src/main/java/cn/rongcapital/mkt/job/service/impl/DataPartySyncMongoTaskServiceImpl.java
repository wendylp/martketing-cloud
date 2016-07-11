package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.*;
import cn.rongcapital.mkt.po.DataArchPoint;
import cn.rongcapital.mkt.po.DataCustomerTags;
import cn.rongcapital.mkt.po.DataLogin;
import cn.rongcapital.mkt.po.DataMember;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPayment;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.DataShopping;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.po.base.BaseQuery;
import cn.rongcapital.mkt.po.mongodb.*;
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

    private static String MONGODB_COLLECTION = "data_party";
	
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
                for (DataTypeEnum temDataTypeEnum : DataTypeEnum.values()) {
                    if (temDataTypeEnum == DataTypeEnum.PARTY) {
                        continue;
                    }
                    add2Mongo(temDataTypeEnum.getCode(), tempDataParty.getMappingKeyid(), tempDataParty.getId());
                }
            }

            dataPartyDao.updateStatusByIds(dataPartyIdList, StatusEnum.PROCESSED.getStatusCode());
        }
	}
	
	private void add2Mongo(Integer dataType,String mappingKeyId, Integer dataPartyId){
	    if(dataType.intValue() == DataTypeEnum.POPULATION.getCode()){
	        DataPopulation data = new DataPopulation();
	        data.setMobile(mappingKeyId);
            data.setStatus(StatusEnum.PROCESSED.getStatusCode());
            MongoProcessor<DataPopulation, cn.rongcapital.mkt.po.mongodb.DataPopulation> processor =
                    new MongoProcessor<DataPopulation, cn.rongcapital.mkt.po.mongodb.DataPopulation>() {
                        @Override
                        public cn.rongcapital.mkt.po.mongodb.DataPopulation process(DataPopulation obj, List<Integer> idList) {
                            cn.rongcapital.mkt.po.mongodb.DataPopulation mongoDataPopulation =
                                    new cn.rongcapital.mkt.po.mongodb.DataPopulation();
                            BeanUtils.copyProperties(obj, mongoDataPopulation);
                            mongoDataPopulation.setMappingKeyId(obj.getId().toString());
                            idList.add(obj.getId());
                            return mongoDataPopulation;
                        }
                        @Override
                        public void processAfter(List<Integer> idList) {
                            dataPopulationDao.updateStatusByIds(idList, StatusEnum.MONGO_PROCESSED.getStatusCode());

                        }
                    };
            syncToMongo(data, dataPopulationDao, processor, dataType, dataPartyId);
	    }else if(dataType.intValue() == DataTypeEnum.CUSTOMER_TAGS.getCode()){
            DataCustomerTags data=new DataCustomerTags();
            data.setMobile(mappingKeyId);
            data.setStatus(StatusEnum.PROCESSED.getStatusCode());
            MongoProcessor<DataCustomerTags, cn.rongcapital.mkt.po.mongodb.DataCustomerTags> processor =
                    new MongoProcessor<DataCustomerTags, cn.rongcapital.mkt.po.mongodb.DataCustomerTags>() {
                        @Override
                        public cn.rongcapital.mkt.po.mongodb.DataCustomerTags process(DataCustomerTags obj, List<Integer> idList) {
                            cn.rongcapital.mkt.po.mongodb.DataCustomerTags mongoDataCustomerTag =
                                    new cn.rongcapital.mkt.po.mongodb.DataCustomerTags();
                            BeanUtils.copyProperties(obj, mongoDataCustomerTag);
                            mongoDataCustomerTag.setMappingKeyId(obj.getId().toString());
                            idList.add(obj.getId());
                            return mongoDataCustomerTag;
                        }
                        @Override
                        public void processAfter(List<Integer> idList) {
                            dataCustomerTagsDao.updateStatusByIds(idList, StatusEnum.MONGO_PROCESSED.getStatusCode());

                        }
                    };
            syncToMongo(data, dataCustomerTagsDao, processor, dataType, dataPartyId);
	    }else if(dataType.intValue() == DataTypeEnum.ARCH_POINT.getCode()){
	        
	        DataArchPoint data = new DataArchPoint();
            data.setMobile(mappingKeyId);
            data.setStatus(StatusEnum.PROCESSED.getStatusCode());
            MongoProcessor<DataArchPoint, cn.rongcapital.mkt.po.mongodb.DataArchPoint> processor =
                    new MongoProcessor<DataArchPoint, cn.rongcapital.mkt.po.mongodb.DataArchPoint>() {
                        @Override
                        public cn.rongcapital.mkt.po.mongodb.DataArchPoint process(DataArchPoint obj, List<Integer> idList) {
                            cn.rongcapital.mkt.po.mongodb.DataArchPoint mongoDataArchPoint =
                                    new cn.rongcapital.mkt.po.mongodb.DataArchPoint();
                            BeanUtils.copyProperties(obj, mongoDataArchPoint);
                            mongoDataArchPoint.setMappingKeyId(obj.getId().toString());
                            idList.add(obj.getId());
                            return mongoDataArchPoint;
                        }
                        @Override
                        public void processAfter(List<Integer> idList) {
                            dataArchPointDao.updateStatusByIds(idList, StatusEnum.MONGO_PROCESSED.getStatusCode());

                        }
                    };
            syncToMongo(data, dataArchPointDao, processor, dataType, dataPartyId);
        }else if(dataType.intValue() == DataTypeEnum.MEMBER.getCode()){
            
            DataMember data = new DataMember();
            data.setMobile(mappingKeyId);
            data.setStatus(StatusEnum.PROCESSED.getStatusCode());

            MongoProcessor<DataMember, cn.rongcapital.mkt.po.mongodb.DataMember> processor =
                    new MongoProcessor<DataMember, cn.rongcapital.mkt.po.mongodb.DataMember>() {
                        @Override
                        public cn.rongcapital.mkt.po.mongodb.DataMember process(DataMember obj, List<Integer> idList) {
                            cn.rongcapital.mkt.po.mongodb.DataMember mongoDataMember =
                                    new cn.rongcapital.mkt.po.mongodb.DataMember();
                            BeanUtils.copyProperties(obj, mongoDataMember);
                            mongoDataMember.setMappingKeyId(obj.getId().toString());
                            idList.add(obj.getId());
                            return mongoDataMember;
                        }
                        @Override
                        public void processAfter(List<Integer> idList) {
                            dataMemberDao.updateStatusByIds(idList, StatusEnum.MONGO_PROCESSED.getStatusCode());

                        }
                    };
            syncToMongo(data, dataMemberDao, processor, dataType, dataPartyId);

        }else if(dataType.intValue() == DataTypeEnum.LOGIN.getCode()){
            
            DataLogin data=new DataLogin();
            data.setMobile(mappingKeyId);
            data.setStatus(StatusEnum.PROCESSED.getStatusCode());

            MongoProcessor<DataLogin, cn.rongcapital.mkt.po.mongodb.DataLogin> processor =
                    new MongoProcessor<DataLogin, cn.rongcapital.mkt.po.mongodb.DataLogin>() {
                        @Override
                        public cn.rongcapital.mkt.po.mongodb.DataLogin process(DataLogin obj, List<Integer> idList) {
                            cn.rongcapital.mkt.po.mongodb.DataLogin mongoDataLogin =
                                    new cn.rongcapital.mkt.po.mongodb.DataLogin();
                            BeanUtils.copyProperties(obj, mongoDataLogin);
                            mongoDataLogin.setMappingKeyId(obj.getId().toString());
                            idList.add(obj.getId());
                            return mongoDataLogin;
                        }
                        @Override
                        public void processAfter(List<Integer> idList) {
                            dataLoginDao.updateStatusByIds(idList, StatusEnum.MONGO_PROCESSED.getStatusCode());

                        }
                    };
            syncToMongo(data, dataLoginDao, processor, dataType, dataPartyId);

        }else if(dataType.intValue() == DataTypeEnum.PAYMENT.getCode()){

            DataPayment data=new DataPayment();
            data.setMobile(mappingKeyId);
            data.setStatus(StatusEnum.PROCESSED.getStatusCode());

            MongoProcessor<DataPayment, cn.rongcapital.mkt.po.mongodb.DataPayment> processor =
                    new MongoProcessor<DataPayment, cn.rongcapital.mkt.po.mongodb.DataPayment>() {
                        @Override
                        public cn.rongcapital.mkt.po.mongodb.DataPayment process(DataPayment obj, List<Integer> idList) {
                            cn.rongcapital.mkt.po.mongodb.DataPayment mongoDataPayment =
                                    new cn.rongcapital.mkt.po.mongodb.DataPayment();
                            BeanUtils.copyProperties(obj, mongoDataPayment);
                            mongoDataPayment.setMappingKeyId(obj.getId().toString());
                            idList.add(obj.getId());
                            return mongoDataPayment;
                        }
                        @Override
                        public void processAfter(List<Integer> idList) {
                            dataPaymentDao.updateStatusByIds(idList, StatusEnum.MONGO_PROCESSED.getStatusCode());

                        }
                    };
            syncToMongo(data, dataPaymentDao, processor, dataType, dataPartyId);

        }else if(dataType.intValue() == DataTypeEnum.SHOPPING.getCode()){
            
            DataShopping data=new DataShopping();
            data.setMobile(mappingKeyId);
            data.setStatus(StatusEnum.PROCESSED.getStatusCode());

            MongoProcessor<DataShopping, cn.rongcapital.mkt.po.mongodb.DataShopping> processor =
                    new MongoProcessor<DataShopping, cn.rongcapital.mkt.po.mongodb.DataShopping>() {
                        @Override
                        public cn.rongcapital.mkt.po.mongodb.DataShopping process(DataShopping obj, List<Integer> idList) {
                            cn.rongcapital.mkt.po.mongodb.DataShopping mongoDataShopping =
                                    new cn.rongcapital.mkt.po.mongodb.DataShopping();
                            BeanUtils.copyProperties(obj, mongoDataShopping);
                            mongoDataShopping.setMappingKeyId(obj.getId().toString());
                            idList.add(obj.getId());
                            return mongoDataShopping;
                        }
                        @Override
                        public void processAfter(List<Integer> idList) {
                            dataShoppingDao.updateStatusByIds(idList, StatusEnum.MONGO_PROCESSED.getStatusCode());

                        }
                    };
            syncToMongo(data, dataShoppingDao, processor, dataType, dataPartyId);

        }else if(dataType.intValue() == DataTypeEnum.WECHAT.getCode()){

            WechatMember wechatMember = new WechatMember();
            wechatMember.setId(Long.valueOf(mappingKeyId));
            List<WechatMember> dataList=wechatMemberDao.selectList(wechatMember);

            //insert into mongodb
            for(WechatMember dataObj : dataList){
                cn.rongcapital.mkt.po.mongodb.WechatMember mongoWechatMember =
                        new cn.rongcapital.mkt.po.mongodb.WechatMember();
                BeanUtils.copyProperties(dataObj, mongoWechatMember);
                mongoWechatMember.setMid(dataPartyId);
                mongoWechatMember.setMd_type(dataType);
                mongoWechatMember.setMapping_keyid(dataObj.getId().toString());
                mongoTemplate.insert(mongoWechatMember,MONGODB_COLLECTION);
            }

        }
	}

    private <T extends BaseQuery, D extends BaseDao<T>, V extends AbstractBaseMongoVO> void syncToMongo(
            T queryObj, D dao, MongoProcessor<T, V> processor,
            Integer dataType, Integer dataPartyId) {

        int totalCount = dao.selectListCount(queryObj);
        if (totalCount < 1) {
            return;
        }
        int totalPages = (totalCount + BATCH_SIZE - 1) / BATCH_SIZE;
        queryObj.setPageSize(Integer.valueOf(BATCH_SIZE));
        for (int i = 0; i < totalPages; i++) {
            queryObj.setStartIndex(Integer.valueOf(i * BATCH_SIZE));
            List<T> dataList = dao.selectList(queryObj);

            List<V> mongoList = new ArrayList<>(dataList.size());
            List<Integer> idList =  new ArrayList<>(dataList.size());
            for(T tempDataObj : dataList){
                V mongoObj = processor.process(tempDataObj, idList);
                mongoObj.setMid(dataPartyId);
                mongoObj.setMdType(dataType);
                mongoList.add(mongoObj);
            }

            mongoTemplate.insert(mongoList, MONGODB_COLLECTION);
            processor.processAfter(idList);
        }
    }

    interface MongoProcessor<T, V extends AbstractBaseMongoVO> {

        V process(T obj, List<Integer> idList);

        void processAfter(List<Integer> idList);
    }
	
}
