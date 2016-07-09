package cn.rongcapital.mkt.job.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.DataPartyTagRuleMapDao;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.dao.TagGroupMapDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.mongodb.DataPartyRepository;
import cn.rongcapital.mkt.po.DataPartyTagRuleMap;
import cn.rongcapital.mkt.po.TagGroupMap;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Tag;

@Service
public class DataPartyTagSyncMongoTaskImpl implements TaskService {
	
	private static Logger logger = LoggerFactory.getLogger(DataPartyTagSyncMongoTaskImpl.class);	
	@Autowired
	private TagGroupMapDao tagGroupMapDao;
	@Autowired
	private DataPartyTagRuleMapDao dataPartyTagRuleMapDao;
	@Autowired
	private TagDao tagDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private DataPartyRepository dataPartyRepository;
	private static final int pageSize = 100;
	@Override
	public void task(Integer taskId) {
		int totalRecord = (int)mongoTemplate.count(null, DataParty.class);
		int totalPage = (totalRecord + pageSize -1) / pageSize;
		for(int index = 0; index < totalPage; index++) {
			List<DataParty> dataPartyList = getDataPartyList(index,pageSize);
			if(CollectionUtils.isEmpty(dataPartyList)) {
				break;
			}
			List<DataPartyTagRuleMap> dataPartyTagRuleMapList = getDataPartyTagRuleMap();
			if(CollectionUtils.isNotEmpty(dataPartyTagRuleMapList)) {
				for(DataParty dp:dataPartyList) {
					try {
						setTagToMongoDataParty(dp,dataPartyTagRuleMapList);
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
				}
			}
		}
	}
	
	private List<DataPartyTagRuleMap> getDataPartyTagRuleMap() {
		DataPartyTagRuleMap dataPartyTagRuleMapT = new DataPartyTagRuleMap();
		dataPartyTagRuleMapT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		int count = dataPartyTagRuleMapDao.selectListCount(dataPartyTagRuleMapT);
		dataPartyTagRuleMapT.setPageSize(count);
		List<DataPartyTagRuleMap> dataPartyTagRuleMapList = dataPartyTagRuleMapDao.selectList(dataPartyTagRuleMapT);
		return dataPartyTagRuleMapList;
	}

	/**
	 * 给mongodb里的data_party表里增加tagList
	 * @param dp
	 * @throws  
	 * @throws IllegalArgumentException 
	 */
	private void setTagToMongoDataParty(DataParty dp,List<DataPartyTagRuleMap> dataPartyTagRuleMapList) throws Exception {
		Integer mdType = dp.getMdType();
		Integer mid = dp.getMid();
		if(null == mdType || mid== null) {
			return;
		}
		Field fields[] = DataParty.class.getDeclaredFields();
		List<Tag> tagList = new ArrayList<Tag>();
		for(Field f:fields) {
			f.setAccessible(true);
			String fieldName = f.getName();
			Object fieldValueOfMongoObj = f.get(dp);
			for(DataPartyTagRuleMap dataPartyTagRuleMap:dataPartyTagRuleMapList) {
				if(mdType.intValue() == dataPartyTagRuleMap.getMdType().intValue() &&
				   StringUtils.equalsIgnoreCase(dataPartyTagRuleMap.getFieldName(), fieldName)) {
					String columnValueOfRuleTable = dataPartyTagRuleMap.getFieldValue();
				    boolean isMatchTagRule = isMatchTagRule(fieldValueOfMongoObj, columnValueOfRuleTable);
					if(isMatchTagRule) {
						Tag tagOfMongoDataParty = new Tag();
						tagOfMongoDataParty.setTagId(dataPartyTagRuleMap.getTagId());
						cn.rongcapital.mkt.po.Tag tagOfMysqlT = new cn.rongcapital.mkt.po.Tag();
						tagOfMysqlT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
						tagOfMysqlT.setId(dataPartyTagRuleMap.getTagId());
						List<cn.rongcapital.mkt.po.Tag> tagOfMysqlList =  tagDao.selectList(tagOfMysqlT);
						if(CollectionUtils.isNotEmpty(tagOfMysqlList)) {
							tagOfMongoDataParty.setTagName(tagOfMysqlList.get(0).getName());
						}
						TagGroupMap tagGroupMapT = new TagGroupMap();
						tagGroupMapT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
						tagGroupMapT.setTagId(dataPartyTagRuleMap.getTagId());
						List<TagGroupMap> tagGroupMapList = tagGroupMapDao.selectList(tagGroupMapT);
						if(CollectionUtils.isNotEmpty(tagGroupMapList)) {
							tagOfMongoDataParty.setTagGroupId(tagGroupMapList.get(0).getGroupId());
						}
						tagList.add(tagOfMongoDataParty);
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(tagList)) {
			Update update = new Update().set("tagList", tagList);
			Criteria criteria = Criteria.where("mid").is(mid);
//			DataParty dpp = 
			mongoTemplate.findAndModify(new Query(criteria),update,DataParty.class);
//			System.out.println(JSON.toJSONString(dpp));
		}
	}
	
	private boolean isMatchTagRule(Object fieldValueOfMongoObj,String columnValueOfRuleTable) {
		boolean res = false;
		if(fieldValueOfMongoObj instanceof String) {
			res = StringUtils.equals((String)fieldValueOfMongoObj, columnValueOfRuleTable);
		}
		if(fieldValueOfMongoObj instanceof Integer) {
			int columnValue = (int)fieldValueOfMongoObj;
			int columnValueFromRuleTable = Integer.parseInt(columnValueOfRuleTable);
			res = columnValue == columnValueFromRuleTable;
		}
		return res;
	}
	
	private List<DataParty> getDataPartyList(int index,int pageSize) {
        Page<DataParty> dataPartyPageList = dataPartyRepository.findAll(new PageRequest(index,pageSize));
        List<DataParty> dataPartyList = new ArrayList<DataParty>();
        for(DataParty dp:dataPartyPageList) {
        	dataPartyList.add(dp);
        }
		return dataPartyList;
	}
}
