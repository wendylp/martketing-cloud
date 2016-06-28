/*************************************************
 * @功能简述: AudienceSearchService实现类
 * @see: MkyApi
 * @author: xukun
 * @version: 1.0
 * @date: 2016/6/23 
*************************************************/


package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	    
		AudienceList param = new AudienceList();
		param.setPageSize(size);
		param.setStartIndex((index-1)*size);
		
		List<Integer> idList=new ArrayList<Integer>();
		
		
		List<Integer> partyIdList=new ArrayList<Integer>();
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
		                   ApiErrorCode.SUCCESS.getMsg(),
		                   ApiConstant.INT_ZERO,null);
				
		if(audience_type.equals("0")){		    
		    //全局模糊查询所有联系人		    		    
		    Map<String,Object> map=dataPartyDao.selectListByKeyName(audience_name);	
		    
		    //返回结果
		    
		}else if(audience_type.equals("1")){
		    
		    //人群管理:在人群中查找
		    //1.根据人群ID查出联系人ID列表		    
		    idList.add(audience_id);
		    List<AudienceListPartyMap> cols=audienceListPartyMapDao.selectListByIdList(idList);
		    for(AudienceListPartyMap col : cols){
		        partyIdList.add(col.getPartyId());
		    }
		    
		    //2.在联系人中查找名字匹配的
		    Map<String,Object> map=dataPartyDao.selectListByNameInList(partyIdList,audience_name);   
		    	    
		    
		}else if(audience_type.equals("2")){
            //微信资产:在人群中查找
		    //1.根据人群ID查出微信ID列表 
            
            List<AudienceListPartyMap> cols=audienceListPartyMapDao.selectListByIdList(idList);
            for(AudienceListPartyMap col : cols){
                partyIdList.add(col.getPartyId());
            }
            
            //2.在微信中查找昵称匹配的(wechat_member)
            //Map<String,Object> map=wechatMemberDao.selectListByNameInList(partyIdList,audience_name); 
            
            //3.返回结果
            
            
        }else if(audience_type.equals("3")){
            //自定义标签:在人群中查找
            //1.根据自定义标签ID查出人群ID列表 
            CustomTagMap tagmap=new CustomTagMap();
            tagmap.setId(audience_id);            
            
            List<CustomTagMap> cols=customTagMapDao.selectList(tagmap);
            
            for(CustomTagMap col : cols){
                partyIdList.add(col.getMapId());
            }
            
            //2.在人群(自定义标签)中查找名字匹配的
            Map<String,Object> map=dataPartyDao.selectListByNameInList(partyIdList,audience_name);  
            
            //3.返回结果
            
            
        }else{
          ;
        }
		
		
		result.setTotal(result.getData().size());
		
		return result;
	}

}
