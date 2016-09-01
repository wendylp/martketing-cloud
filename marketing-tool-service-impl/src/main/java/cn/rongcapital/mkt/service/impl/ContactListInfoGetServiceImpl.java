package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactListInfoGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.ContactListInfoOut;
import cn.rongcapital.mkt.vo.out.Field_List;

@Service
public class ContactListInfoGetServiceImpl implements ContactListInfoGetService {

	private static Integer DEFAULT_TEMPLATE_PAGE_SIZE = 50;

	@Autowired
	ContactTemplateDao contactTemplateDao;

	@Override
	public BaseOutput getContactListInfo(String contactId) {

		ContactTemplate contactTemplate = new ContactTemplate();
		contactTemplate.setContactId(Long.parseLong(contactId));
		contactTemplate.setDelStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		contactTemplate.setPageSize(DEFAULT_TEMPLATE_PAGE_SIZE);
		List<ContactTemplate> contactTemplates = contactTemplateDao.selectList(contactTemplate);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		if (CollectionUtils.isNotEmpty(contactTemplates)) {
			result.setTotal(contactTemplates.size());
			if (contactTemplates.size() > 0) {
				ContactListInfoOut contactListInfoOut = new ContactListInfoOut();
				contactListInfoOut.setContact_id(contactTemplates.get(0).getContactId().toString());
				contactListInfoOut.setContact_name(contactTemplates.get(0).getContactName());
				contactListInfoOut.setQrcode_url(contactTemplates.get(0).getQrcodeUrl());
				// contactListInfoOut.setQrcode_pic(contactTemplates.get(0).getqr);
				contactListInfoOut.setContact_descript(contactTemplates.get(0).getContactDescript());
				contactListInfoOut.setContact_status(contactTemplates.get(0).getStatus().toString());
				List<Field_List> lists = new ArrayList<Field_List>();
				for (ContactTemplate c : contactTemplates) {
					Field_List list = new Field_List();
					list.setField_name(c.getFieldName());
					list.setField_code(c.getFieldCode());
					list.setSelected(c.getSelected());
					list.setField_type(c.getFieldType());
					list.setIndex(c.getFieldIndex().toString());
					list.setRequired(c.getRequired());
					list.setIschecked(c.getIschecked());
					list.setSelect_data(getSelectData(c.getFieldName()));
					lists.add(list);
				}
				contactListInfoOut.setField_list(lists);
				result.getData().add(contactListInfoOut);
			}
		}
		return result;
	}
	
	
	
	private List<Map<String, Object>> getSelectData(String fieldName) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		
		switch (fieldName) {
		case "性别":
			dataList.add(getMap("男", 1));
			dataList.add(getMap("女", 0));
			break;
		case "婚否":
			dataList.add(getMap("已婚", 1));
			dataList.add(getMap("未婚", 0));
			break;
		case "血型":
			dataList.add(getMap("A", 1));
			dataList.add(getMap("B", 0));
			break;
		case "教育程度":
			dataList.add(getMap("幼儿园", 1));
			dataList.add(getMap("小学", 0));
			break;
		case "国籍":
			dataList.add(getMap("中国", 1));
			dataList.add(getMap("外国", 0));
			break;
		case "就业情况":
			dataList.add(getMap("已就业", 1));
			dataList.add(getMap("待业", 0));
			break;
		}
		
//		Map<String, Object> map = new HashMap<String, Object>();
//		if(fieldName.equals("性别")) {
//			map.put("name", "男");
//			map.put("value", 1);
//			dataList.add(map);
//			map.put("男", 1);
//			map.put("女",0);
//		} else if(fieldName.equals("婚否")) {
//			map.put("已婚", 1);
//			map.put("未婚", 0);
//		} else if(fieldName.equals("血型")) {
//			map.put("A", 1);
//			map.put("B", 0);
//		} else if(fieldName.equals("职业")) {
//			map.put("医生", 1);
//			map.put("杀手", 0);
//		} else if(fieldName.equals("教育程度")) {
//			map.put("幼儿园", 1);
//			map.put("小学", 0);
//		} else if(fieldName.equals("国籍")) {
//			map.put("中国", 1);
//			map.put("外国", 0);
//		} else if(fieldName.equals("就业情况")) {
//			map.put("已就业", 1);
//			map.put("待业", 0);
//		}
//		return map;
		return dataList;
	}
	
	/**
	 * @Title: getMap   
	 * @Description: 返回封装后数组
	 * @param: @param name
	 * @param: @param value
	 * @param: @return      
	 * @return: Map<String,Object>      
	 * @throws
	 */
	private Map<String, Object> getMap(String name,Object value){
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("value", value);
		return map;
	}
}