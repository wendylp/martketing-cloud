package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.GenderEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.ContactListDao;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactList;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContacsCommitSaveService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactsCommitDelIn;
import cn.rongcapital.mkt.vo.in.ContactsCommitSaveIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class ContactsCommitSaveServiceImpl implements ContacsCommitSaveService {

	private static final Integer SHOWN_IN_FEEDBACK_STATUS = 0;

	@Autowired
	ContactListDao contactDao;

	@Autowired
	ContactTemplateDao contactTemplateDao;
	
	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput contactsCommitSave(ContactsCommitSaveIn body) {
		ContactList contact = new ContactList();
		String gender = body.getGender();
		contact.setContactTemplId(body.getContact_templ_id());
		contact.setName(body.getName());
		if (GenderEnum.MALE.getDescription().equals(gender)) {
			contact.setGender(GenderEnum.MALE.getStatusCode());
		} else if (GenderEnum.FEMALE.getDescription().equals(gender)) {
			contact.setGender(GenderEnum.FEMALE.getStatusCode());
		} else if (GenderEnum.OTHER.getDescription().equals(gender)) {
			contact.setGender(GenderEnum.OTHER.getStatusCode());
		} else if (GenderEnum.UNSURE.getDescription().equals(gender)) {
			contact.setGender(GenderEnum.UNSURE.getStatusCode());
		}
		contact.setAge(body.getAge());
		contact.setMobile(body.getMobile());
		contact.setEmail(body.getEmail());
		contact.setAddress(body.getAddress());
		
		//新增保存2016-09-01
		contact.setBirthday(body.getBirthday());
		contact.setProvice(body.getProvice());
		contact.setCity(body.getCity());
		contact.setJob(body.getJob());
		contact.setMonthlyIncome(body.getMonthlyIncome());
		contact.setMonthlyConsume(body.getMonthlyIncome());
		contact.setMaritalStatus(body.getMaritalStatus());
		contact.setEducation(body.getEducation());
		contact.setEmployment(body.getEmployment());
		contact.setNationality(body.getNationality());
		contact.setBloodType(body.getBloodType());
		contact.setCitizenship(body.getCitizenship());
		contact.setIq(body.getIq());
		contact.setIdentifyNo(body.getIdentifyNo());
		contact.setDrivingLicense(body.getDrivingLicense());
		contact.setTel(body.getTel());
		contact.setQq(body.getQq());
		contact.setAcctType(body.getAcctType());
		contact.setAcctNo(body.getAcctNo());
		contact.setIdfa(body.getIdfa());
		contact.setImei(body.getImei());
		contact.setUdid(body.getUdid());
		contact.setPhoneMac(body.getPhoneMac());
		if(body.getStatus() != null){
			contact.setStatus(body.getStatus().intValue());
		}
		contact.setUpdateTime(body.getUpdateTime());
		contact.setSource(body.getSource());
		contact.setBitmap(body.getBitmap());
		contact.setKeyid(body.getKeyid());
		contact.setWxmpId(body.getWxmpId());
		contact.setWxCode(body.getWxCode());
		contact.setNickname(body.getNickname());
		contact.setHeadImgUrl(body.getHeadImgUrl());
		contact.setSubscribeTime(body.getSubscribeTime());
		contact.setLanguage(body.getLanguage());
		contact.setUnionid(body.getUnionid());
		contact.setRemark(body.getRemark());
		contact.setFillDevice(body.getFillDevice());
		contact.setOs(body.getOs());
		contact.setBrower(body.getBrower());
		contact.setIp(body.getIp());
		contact.setWxNickname(body.getWxNickname());
		contact.setWxGender(body.getWxGender());
		contact.setWxOpenid(body.getWxOpenid());
		contact.setWxHeaderUrl(body.getWxHeaderUrl());
		contact.setWxCountry(body.getWxCountry());
		contact.setWxCity(body.getWxCity());
		contact.setContactTemplId(body.getContact_templ_id());

		contactDao.insert(contact);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		return result;
	}

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput contactsCommitGet(Integer contact_id, Integer commit_time, Integer index, Integer size) {
		ContactList contact = new ContactList();
		ContactTemplate contactTemplate = new ContactTemplate();
		contactTemplate.setContactId(Long.valueOf(contact_id));
		contactTemplate.setIsShownInFeedback(SHOWN_IN_FEEDBACK_STATUS.byteValue());
		List<ContactTemplate> contactTemplateList = contactTemplateDao.selectListAll(contactTemplate);
		
		//Map<String, Object> cloMap = new LinkedHashMap<>();
		Map<String, Object> cloMap = null;
		List<Map<String, Object>> columnList = new ArrayList<>();
		List<String> filedNameList = new ArrayList<>();
		for(ContactTemplate template : contactTemplateList){
			cloMap = new LinkedHashMap<>();
			cloMap.put("col_id",template.getFieldIndex());
			cloMap.put("col_name",template.getFieldName());
			cloMap.put("col_code",template.getFieldCode());
			filedNameList.add(template.getFieldCode());
			columnList.add(cloMap);
		}
		contact.setContactTemplId(contact_id);

		Date startTime = null;
		Date endTime = null;
		Calendar canlendar = Calendar.getInstance();
		switch (commit_time) {
		case 0:
			break;
		case 1:
			startTime = DateUtil.getDateFromString(DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
			endTime = new Date();
			break;
		case 2:
			canlendar.add(Calendar.WEEK_OF_YEAR, -1);
			startTime = canlendar.getTime();
			endTime = new Date();
			break;
		case 3:
			canlendar.add(Calendar.MONTH, -1);
			startTime = canlendar.getTime();
			endTime = new Date();
			break;
		case 4:
			canlendar.add(Calendar.MONTH, -3);
			startTime = canlendar.getTime();
			endTime = new Date();
			break;
		}
		contact.setStartTime(startTime);
		contact.setEndTime(endTime);
		
		contact.setStartIndex((index-1)*size);
		contact.setPageSize(size);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		List<ContactList> list = contactDao.selectListByContactIdAndCommitTime(contact);
		
		if (!CollectionUtils.isEmpty(list)) {
			int selectListCount = contactDao.selectListCount(contact);
			result.setTotal(selectListCount);
			for (ContactList item : list) {
				Map<String, Object> map = new LinkedHashMap<>();
				for(String filedName : filedNameList){
					if(filedName.equals("name")){
						map.put(filedName, item.getName());
					}else if(filedName.equals("gender")){
						map.put(filedName, item.getGender());
					}else if(filedName.equals("birthday")){
						map.put(filedName, item.getBirthday());
					}else if(filedName.equals("mobile")){
						map.put(filedName, item.getMobile());
					}else if(filedName.equals("tel")){
						map.put(filedName, item.getTel());
					}else if(filedName.equals("email")){
						map.put(filedName, item.getEmail());
					}else if(filedName.equals("qq")){
						map.put(filedName, item.getQq());
					}else if(filedName.equals("blood_type")){
						map.put(filedName, item.getBloodType());
					}else if(filedName.equals("nationality")){
						map.put(filedName, item.getNationality());
					}else if(filedName.equals("citizenship")){
						map.put(filedName, item.getCitizenship());
					}else if(filedName.equals("city")){
						map.put(filedName, item.getCity());
					}else if(filedName.equals("monthly_income")){
						map.put(filedName, item.getMonthlyIncome());
					}else if(filedName.equals("job")){
						map.put(filedName, item.getJob());
					}else if(filedName.equals("education")){
						map.put(filedName, item.getEducation());
					}else if(filedName.equals("employment")){
						map.put(filedName, item.getEmployment());
					}else if(filedName.equals("iq")){
						map.put(filedName, item.getIq());
					}else if(filedName.equals("identify_no")){
						map.put(filedName, item.getIdentifyNo());
					}else if(filedName.equals("driving_license")){
						map.put(filedName, item.getDrivingLicense());
					}else if(filedName.equals("marital_status")){
						map.put(filedName, item.getMaritalStatus());
					}
				}
				map.put("commit_id", item.getId());
				result.getData().add(map);
			}
		}
		result.getColNames().add(columnList);
		return result;
	}

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput contactsCommitDel(ContactsCommitDelIn body) {
		ContactList contact = new ContactList();
		contact.setId(body.getCommit_id());
		contact.setContactTemplId(body.getContact_id());
		contact.setStatus(2);

		contactDao.updateByContactId(contact);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", body.getCommit_id());
		map.put("updatetime", DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		result.setTotal(1);
		result.getData().add(map);

		return result;
	}

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput contactsCommitDownload(Integer contact_id, Integer commit_time) {
		ContactList contact = new ContactList();
		contact.setContactTemplId(contact_id);

		Date startTime = null;
		Date endTime = null;
		Calendar canlendar = Calendar.getInstance();
		switch (commit_time) {
		case 1:
			startTime = DateUtil.getDateFromString(DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
			endTime = new Date();
			break;
		case 2:
			canlendar.add(Calendar.WEEK_OF_YEAR, -1);
			startTime = canlendar.getTime();
			endTime = new Date();
			break;
		case 3:
			canlendar.add(Calendar.MONTH, -1);
			startTime = canlendar.getTime();
			endTime = new Date();
			break;
		case 4:
			canlendar.add(Calendar.MONTH, -3);
			startTime = canlendar.getTime();
			endTime = new Date();
			break;
		}
		contact.setStartTime(startTime);
		contact.setEndTime(endTime);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		List<ContactList> list = contactDao.selectListByContactIdAndCommitTime(contact);
		if (!CollectionUtils.isEmpty(list)) {
			result.setTotal(list.size());
			for (ContactList item : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("download_url", item.getDownloadUrl());
				result.getData().add(map);
			}
		}

		return result;
	}
	
	public static void main(String[] args) {
		String gender = "女";
		 if (gender != null) {
	            if (GenderEnum.MALE.getDescription().equals(gender)) {
	             System.out.println(GenderEnum.MALE.getStatusCode());
	            } else if (GenderEnum.FEMALE.getDescription().equals(gender)) {
	            	  System.out.println(GenderEnum.FEMALE.getStatusCode());
	            } else if (GenderEnum.OTHER.getDescription().equals(gender)) {
	            	  System.out.println(GenderEnum.OTHER.getStatusCode());
	            } else if (GenderEnum.UNSURE.getDescription().equals(gender)) {
	            	  System.out.println(GenderEnum.UNSURE.getStatusCode());
	            }
	        }
	}
}
