/*************************************************
 * @功能简述: AudienceSearchService实现类
 * @see: MkyApi
 * @author: xukun
 * @version: 1.0
 * @date: 2016/6/23 
*************************************************/


package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.common.util.GenderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceColumnsDao;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.po.AudienceListPartyMap;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.service.AudienceSearchService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class AudienceSearchServiceImpl implements AudienceSearchService {
	
    @Autowired
	AudienceListDao audienceListDao;
	@Autowired
	AudienceColumnsDao audienceColumnsDao;
	
	@Autowired
	AudienceListPartyMapDao audienceListPartyMapDao;
	
	@Autowired
	CustomTagMapDao  customTagMapDao;
	
	@Autowired
	DataPartyDao dataPartyDao;	
	
	//微信
	@Autowired
    //DataPartyDao dataPartyDao;
	
	private static final String ORDER_BY_FIELD_NAME = "field_order";//排序的字段名
	
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput audienceByName(String userToken,String audience_type,int audience_id,String audience_name,Integer size,Integer index) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO,null);

		if(audience_name == null || "".equals(audience_name)){
			return result;
		}

		AudienceList param = new AudienceList();
		param.setPageSize(size);
		param.setStartIndex((index-1)*size);

		audience_name = "%" + audience_name + "%";
		
		List<Integer> idList=new ArrayList<Integer>();

		List<Integer> partyIdList=new ArrayList<Integer>();

		List<Map<String,Object>> resultList = null;
				
		if(audience_type.equals("0")){		    
		    //全局模糊查询所有联系人		    		    
		    resultList=dataPartyDao.selectListByKeyName(audience_name);
		}else if(audience_type.equals("1")){
		    
		    //人群管理:在人群中查找
		    //1.根据人群ID查出联系人ID列表		    
		    idList.add(audience_id);
		    List<AudienceListPartyMap> cols=audienceListPartyMapDao.selectListByIdList(idList);
			if(cols != null && cols.size() > 0){
				for(AudienceListPartyMap col : cols){
					partyIdList.add(col.getPartyId());
				}
			}
		    //2.在联系人中查找名字匹配的
			resultList = SearchAudienceByName(audience_name, partyIdList, resultList);
		}else if(audience_type.equals("2")){
            //自定义标签:在人群中查找
            //1.根据自定义标签ID查出人群ID列表 
            CustomTagMap tagmap=new CustomTagMap();
            tagmap.setId(audience_id);            
            
            List<CustomTagMap> cols=customTagMapDao.selectList(tagmap);
            if(cols != null && cols.size() > 0){
				for(CustomTagMap col : cols){
					partyIdList.add(col.getMapId());
				}
			}

            //2.在人群(自定义标签)中查找名字匹配的
			resultList = SearchAudienceByName(audience_name, partyIdList, resultList);
		}

		for(Map<String,Object> map : resultList){
			if(map.get("gender") != null){
				map.put("gender", map.get("gender"));
			}
		}
		if(resultList != null && resultList.size() > 0){
			result.getData().addAll(resultList);
		}
		result.setTotal(result.getData().size());
		
		return result;
	}

	private List<Map<String, Object>> SearchAudienceByName(String audience_name, List<Integer> partyIdList, List<Map<String, Object>> resultList) {
		if(partyIdList.size() != 0){
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("partyIdList",partyIdList);
            paramMap.put("key_word",audience_name);
            resultList=dataPartyDao.selectListByNameInList(paramMap);
        }
		return resultList;
	}

}