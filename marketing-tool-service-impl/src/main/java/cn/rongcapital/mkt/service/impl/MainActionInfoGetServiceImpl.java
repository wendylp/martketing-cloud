/*************************************************
 * @功能简述: 获取某联系人行为信息(微信、web、活动)的业务类实现 
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.PartyBehaviorDao;
import cn.rongcapital.mkt.po.PartyBehavior;
import cn.rongcapital.mkt.service.MainActionInfoGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class MainActionInfoGetServiceImpl implements MainActionInfoGetService{
	
	@Autowired
	PartyBehaviorDao partyBehaviorDao;
	
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput getMainActionInfo(String contactId,String behaviorType) {
		PartyBehavior param = new PartyBehavior();
		param.setContactId(contactId);
		param.setBehaviorType(behaviorType);
		List<PartyBehavior> resList = partyBehaviorDao.selectList(param);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				   ApiErrorCode.SUCCESS.getMsg(),
				   ApiConstant.INT_ZERO,null);
		if(resList != null && !resList.isEmpty()){
			result.setTotal(resList.size());
			for(PartyBehavior po : resList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("behavior_type", po.getBehaviorType());
				map.put("behavior_name", po.getBehaviorName());
				map.put("wechat_type", po.getWechatType());
				map.put("wechat_name", po.getWechatName());
				map.put("message", po.getMessage());
				map.put("createtime", po.getCreatetime());
				result.getData().add(map);
			}
		}
		return result;
	}

}
