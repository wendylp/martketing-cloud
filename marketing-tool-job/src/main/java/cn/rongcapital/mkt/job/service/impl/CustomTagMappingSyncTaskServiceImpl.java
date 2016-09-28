package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.TagSourceEnum;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.*;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagLeaf;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.FindCustomTagInfoService;
import cn.rongcapital.mkt.service.TagCustomTagToDataPartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Yunfeng on 2016-7-13.
 */
@Service
public class CustomTagMappingSyncTaskServiceImpl implements TaskService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomTagMapDao customTagMapDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ContactListDao contactListDao;
    @Autowired
    private TagCustomTagToDataPartyService tagCustomTagToDataPartyService;
    @Autowired
    private FindCustomTagInfoService findCustomTagInfoService;

    private final Integer BATCH_SIZE = 500;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void task(Integer taskId) {
        int totalPage = queryTotalPage();

        for(int i = 0; i<totalPage; i++){
            List<CustomTagMap> customTagMapList = this.queryCustomTagMapByBatchSize(i*BATCH_SIZE);
            if(CollectionUtils.isEmpty(customTagMapList)) return;
            for(CustomTagMap customTagMap : customTagMapList){
                switch (TagSourceEnum.getTagSourceEnum(customTagMap.getTagSource())){
                    case FILE_SOURCE_ACCESS:
                        Query query = new Query(Criteria.where("batch_id").is(customTagMap.getMapId()));
                        List<cn.rongcapital.mkt.po.mongodb.DataParty> dataPartyList = mongoTemplate.find(query,cn.rongcapital.mkt.po.mongodb.DataParty.class);
                        if(CollectionUtils.isEmpty(dataPartyList)) continue;
                        for(DataParty dataParty : dataPartyList){
                            tagCustomTagToDataPartyService.tagCustomTagToDataParty(dataParty,findCustomTagInfoService.findCustomTagInfoByTagId(customTagMap.getTagId()));
                        }
                        break;
                    case CONTACT_DOCUMENT_SOURCE_ACCESS:
                        String mapId = customTagMap.getMapId();
                        ContactList paramContactList = new ContactList();
                        paramContactList.setStatus(Integer.valueOf(ApiConstant.TABLE_DATA_STATUS_VALID));
                        paramContactList.setContactTemplId(Integer.valueOf(mapId));
                        List<ContactList> contactLists = contactListDao.selectList(paramContactList);
                        if(!CollectionUtils.isEmpty(contactLists)){
                            for(ContactList targetContactList : contactLists){
                                DataParty dataParty = new DataParty();
                                dataParty.setMid(targetContactList.getKeyid());
                                BaseTag baseTag = new CustomTagLeaf();
                                baseTag.setTagId(customTagMap.getTagId());
                                tagCustomTagToDataPartyService.tagCustomTagToDataParty(dataParty,baseTag);
                            }
                        }
                        break;
                    case WECHAT_QRCODE_SOURCE_ACCESS:
                        break;
                }
            }
        }

    }

    private List<CustomTagMap> queryCustomTagMapByBatchSize(Integer startIndex) {
        CustomTagMap paramCustomTagMap = new CustomTagMap();
        paramCustomTagMap.setStartIndex(startIndex);
        paramCustomTagMap.setPageSize(BATCH_SIZE);
        paramCustomTagMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<CustomTagMap> customTagMapList = customTagMapDao.selectList(paramCustomTagMap);
        return customTagMapList;
    }

    private int queryTotalPage() {
        CustomTagMap paramCustomTagMap = new CustomTagMap();
        paramCustomTagMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        Integer totalCount = customTagMapDao.selectListCount(paramCustomTagMap);
        return (totalCount + BATCH_SIZE - 1)/BATCH_SIZE;
    }

}
