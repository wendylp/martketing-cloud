package cn.rongcapital.mkt.service.impl;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactListPvServer;
import cn.rongcapital.mkt.vo.BaseOutput;


@Service
public class ContactListPvServerImpl implements ContactListPvServer{
	
	@Autowired
	ContactTemplateDao contactTemplateDao;
	
	@Override
	public BaseOutput countPageViews(String contactId) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		ContactTemplate contactTemplate = new ContactTemplate(); 
		contactTemplate.setId(Integer.valueOf(contactId));
		
		
		int num = contactTemplateDao.updatePageViewsById(contactTemplate); // 更新pageview
		
		if(num<=0) {
			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			List<ContactTemplate> contactTemplateList = contactTemplateDao.selectList(contactTemplate);
			map.put("id", contactId);
			map.put("updatetime", format.format(contactTemplateList.get(0).getUpdateTime()));
			
			result.getData().add(map);
		}
		return result;
	}

}
