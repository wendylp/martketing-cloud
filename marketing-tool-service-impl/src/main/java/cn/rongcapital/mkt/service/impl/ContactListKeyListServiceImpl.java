/**
 * 接口 ：mkt.contact.list.key.list
 * 功能描述： 将联系人表单导入数据时选择的数据主键保存到数据库中
 * @author shuiyangyang
 * @Data 2016.08.18
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.dao.DefaultContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.po.DefaultContactTemplate;
import cn.rongcapital.mkt.service.ContactListKeyListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class ContactListKeyListServiceImpl implements ContactListKeyListService{

	@Autowired
	ContactTemplateDao contactTemplateDao;
	
	@Autowired
	DefaultContactTemplateDao defaultContactTemplateDao;
	
	@Override
	public BaseOutput getContactListKeyList(String contactId) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		ContactTemplate contactTemplate = new ContactTemplate();
		
		contactTemplate.setContactId(Long.valueOf(contactId));
		
		List<DefaultContactTemplate> defaultContactTemplateLists = defaultContactTemplateDao.selectKeyByContactId(contactTemplate);
		
		if(!defaultContactTemplateLists.isEmpty()){
			result.setTotal(defaultContactTemplateLists.size());
			
			List<String> keyLists = new ArrayList<String>();// 保存到keylist的
			
			for(DefaultContactTemplate defaultContactTemplateList : defaultContactTemplateLists) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("field_name", defaultContactTemplateList.getFieldName());
				map.put("field_code", defaultContactTemplateList.getFieldCode());
				map.put("is_selected", defaultContactTemplateList.getIsSelected());
				result.getData().add(map);
				
				keyLists.add(defaultContactTemplateList.getFieldName());
			}
			
			// 设置keylist
			List<ContactTemplate> contactTemplateLists = contactTemplateDao.selectIdByContactId(contactTemplate);
			if(!contactTemplateLists.isEmpty()) {
				for(ContactTemplate contactTemplateList : contactTemplateLists){
					contactTemplateList.setKeyList(new JSONArray(keyLists).toString());
					contactTemplateList.setIsRememberImportKey((byte)1);
					contactTemplateDao.updateById1(contactTemplateList);// 这个是用自己写的sql
				}
			}
			
		}
		
		return result;
	}

}
