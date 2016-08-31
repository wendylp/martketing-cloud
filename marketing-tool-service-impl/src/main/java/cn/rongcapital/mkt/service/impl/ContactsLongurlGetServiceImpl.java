/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactsLongurlGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
@Service
public class ContactsLongurlGetServiceImpl implements ContactsLongurlGetService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ContactTemplateDao contactTemplateDao;
	
	
	/*
	 * 获取联系人表单对应长链接URL
	 * 接口：mkt.contacts.longurl.get
	 * @author shuiyangyang
	 * @Date 2016.08.31
	 */
	@Override
	public BaseOutput getLongurl(String shortUrl) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		
		ContactTemplate contactTemplate = new ContactTemplate();
		
		// 需要去除短链的头
		contactTemplate.setQrcodeShorturl(shortUrl.substring(shortUrl.lastIndexOf('/') + 1));
		
		List<ContactTemplate> contactTemplateLists = contactTemplateDao.selectList(contactTemplate);
		
		if(contactTemplateLists== null || contactTemplateLists.size()<=0) {
			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
			result.setMsg(shortUrl + "在数据库中不存在");
			result.setTotal(0);
			logger.debug("{} 在数据库中不存在", shortUrl);
		} else {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("long_url", contactTemplateLists.get(0).getQrcodeUrl());
			result.getData().add(map);
			logger.debug("根据短链：{}, 总共查出{}条数据", shortUrl, contactTemplateLists.size());
		}
		
		return result;
	}

}
