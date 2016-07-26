package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.WechatMember;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class DataPartySyncMongoTaskServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(DataPartySyncMongoTaskServiceImpl.class);
	
    private int BATCH_SIZE = 500;

    //主数据data_xxx
	@Autowired
    private DataPartyDao dataPartyDao;
    
    @Autowired
    private WechatMemberDao wechatMemberDao;

	@Autowired
	private MongoTemplate mongoTemplate;

    private static String MONGODB_COLLECTION = "data_party";
    private static String DATA_PARTY_SOURCE_FOR_WECHAT_PUB = "公众号";
    private static String DATA_PARTY_SOURCE_FOR_WECHAT_PERSON = "个人号";

    private ConcurrentHashMap<String, Field[]> filedMap = new ConcurrentHashMap<>();
	
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
                add2Mongo(tempDataParty);
                dataPartyIdList.add(tempDataParty.getId());
            }

            dataPartyDao.updateStatusByIds(dataPartyIdList, StatusEnum.PROCESSED.getStatusCode());
        }
	}
	
	private void add2Mongo(DataParty dataParty){

        Integer dataType = dataParty.getMdType();
        Integer dataPartyId = dataParty.getId();
        String mappingKeyId = dataParty.getMappingKeyid();
        Query query = Query.query(Criteria.where("mid").is(dataPartyId));

        if(dataType.intValue() == DataTypeEnum.WECHAT.getCode() && StringUtils.isNumeric(mappingKeyId)){
            WechatMember wechatMember = new WechatMember();
            wechatMember.setId(Long.valueOf(dataPartyId));
            List<WechatMember> dataList=wechatMemberDao.selectList(wechatMember);

            //insert into mongodb
            for(WechatMember dataObj : dataList){
                cn.rongcapital.mkt.po.mongodb.WechatMember mongoWechatMember =
                        new cn.rongcapital.mkt.po.mongodb.WechatMember();
                BeanUtils.copyProperties(dataObj, mongoWechatMember);
                if(!org.springframework.util.StringUtils.isEmpty(mongoWechatMember.getPubId())){
                    mongoWechatMember.setSource(DATA_PARTY_SOURCE_FOR_WECHAT_PUB);
                }else{
                    mongoWechatMember.setSource(DATA_PARTY_SOURCE_FOR_WECHAT_PERSON);
                }
                mongoWechatMember.setGender(dataObj.getSex().byteValue());
                mongoWechatMember.setMid(dataPartyId);
                mongoWechatMember.setMd_type(dataType);
                mongoWechatMember.setMapping_keyid(dataObj.getId().toString());
                mongoTemplate.upsert(query, buildBaseUpdate(mongoWechatMember), MONGODB_COLLECTION);
            }

        } else {
            cn.rongcapital.mkt.po.mongodb.DataParty mongoDataParty =
                    new cn.rongcapital.mkt.po.mongodb.DataParty();
            BeanUtils.copyProperties(dataParty, mongoDataParty);
            mongoDataParty.setMid(dataPartyId);
            mongoTemplate.upsert(query, buildBaseUpdate(mongoDataParty), MONGODB_COLLECTION);
        }
	}

    private <T> Update buildBaseUpdate(T t) {
        Update update = new Update();
        String className = t.getClass().getName();
        Field[] fields = filedMap.get(className);
        if (fields == null) {
            fields = t.getClass().getDeclaredFields();
            filedMap.putIfAbsent(className, fields);
        }
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (value != null) {
                    update.set(field.getName(), value);
                }
            } catch (Exception e) {
                logger.error("buildBaseUpdate failed", e);
            }
        }
        return update;
    }

}
