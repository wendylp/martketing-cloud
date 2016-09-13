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
import cn.rongcapital.mkt.service.ContactListPvService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.util.CollectionUtils;


@Service
public class ContactListPvServiceImpl implements ContactListPvService{

	private static final Integer FIRST_PV = 1;

	@Autowired
	ContactTemplateDao contactTemplateDao;
	
	@Override
	public BaseOutput countPageViews(String contactId) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer upNumber = null;

		ContactTemplate contactTemplate = new ContactTemplate(); 
		contactTemplate.setContactId(Long.valueOf(contactId));
        List<ContactTemplate> contactTemplateList = contactTemplateDao.selectList(contactTemplate);
		if(!CollectionUtils.isEmpty(contactTemplateList)){
			ContactTemplate upContactTemplate = contactTemplateList.get(0);
			if(upContactTemplate.getPageViews() == null){
				upContactTemplate.setPageViews(FIRST_PV);
				contactTemplateDao.updatePageViewsById(upContactTemplate);
			}else{
				upContactTemplate.setPageSize(upContactTemplate.getPageViews() + 1);
				contactTemplateDao.updatePageViewsById(upContactTemplate);
			}
		}else {
			result.setMsg("该联系人表单");
			return result;
		}


//		int num = contactTemplateDao.updatePageViewsById(contactTemplate); // 更新pageview

		if(upNumber == null) {
			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			contactTemplateList = contactTemplateDao.selectList(contactTemplate);
			map.put("id", contactId);
			map.put("updatetime", format.format(contactTemplateList.get(0).getUpdateTime()));
			
			result.getData().add(map);
		}
		return result;
	}

}
