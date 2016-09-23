/*************************************************
 * @功能简述: 获取某条主数据详细信息
 * @see MktApi：
 * @author: 朱学龙
 * @version: 1.0 @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.GenderUtils;
import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.service.MainBasicInfoGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.BasicContactInfoOut;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class MainBasicInfoGetServiceImpl implements MainBasicInfoGetService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DataPopulationDao dataPopulationDao;

	@Autowired
	private DataCustomerTagsDao dataCustomerTagsDao;

	@Autowired
	private DataArchPointDao dataArchPointDao;

	@Autowired
	private DataMemberDao dataMemberDao;

	@Autowired
	private DataLoginDao dataLoginDao;

	@Autowired
	private DataPaymentDao dataPaymentDao;

	@Autowired
	private DataShoppingDao dataShoppingDao;

	@Autowired
	private DataPartyDao dataPartyDao;

	@Override
	public Integer getKeyIdFromContactInfo(Integer contactId, Integer dataType, String userToken) {

		Integer keyid = null;

		if (dataType == DataTypeEnum.PARTY.getCode()) {
			keyid = contactId; // dataPartyDao.selectMobileById(contactId);
		} else if (dataType == DataTypeEnum.POPULATION.getCode()) {
			keyid = dataPopulationDao.selectObjectById(contactId).getKeyid();
		} else if (dataType == DataTypeEnum.CUSTOMER_TAGS.getCode()) {
			keyid = dataCustomerTagsDao.selectObjectById(contactId).getKeyid();
		} else if (dataType == DataTypeEnum.ARCH_POINT.getCode()) {
			keyid = dataArchPointDao.selectObjectById(contactId).getKeyid();
		} else if (dataType == DataTypeEnum.MEMBER.getCode()) {
			keyid = dataMemberDao.selectObjectById(contactId).getKeyid();
		} else if (dataType == DataTypeEnum.LOGIN.getCode()) {
			keyid = dataLoginDao.selectObjectById(contactId).getKeyid();
		} else if (dataType == DataTypeEnum.PAYMENT.getCode()) {
			keyid = dataPaymentDao.selectObjectById(contactId).getKeyid();
		} else if (dataType == DataTypeEnum.SHOPPING.getCode()) {
			keyid = dataShoppingDao.selectObjectById(contactId).getKeyid();
		}

		return keyid;
	}

	/**
	 * 微信视图显示信息
	 */
	private BasicContactInfoOut getWeiXinDataVo(Integer dataType,Integer keyId){
		BasicContactInfoOut dataVo = null;
		if (dataType == DataTypeEnum.WECHAT.getCode()){
			dataVo = new BasicContactInfoOut();
			DataPopulation dataPopulationTemp = new DataPopulation();
			dataPopulationTemp.setKeyid(keyId);
//			dataPopulationTemp.setBitmap(ApiConstant.WEIXIN_BITMAP);
			List<DataPopulation> dataPopulations = dataPopulationDao.selectList(dataPopulationTemp);
			if(CollectionUtils.isNotEmpty(dataPopulations)){
				for(Iterator<DataPopulation> iter = dataPopulations.iterator();iter.hasNext();){
					DataPopulation dataPopulation = iter.next();
					String bitMap = dataPopulation.getBitmap();
					if(bitMap.equals(ApiConstant.WEIXIN_BITMAP)){
						dataVo.setWx_country(dataPopulation.getCitizenship());			
						dataVo.setWx_provice(dataPopulation.getProvice());
						dataVo.setWx_city(dataPopulation.getCity());
						String gender = GenderUtils.byteToChar(dataPopulation.getGender());
						dataVo.setWx_gender(String.valueOf(gender));
						dataVo.setWx_nickname(dataPopulation.getNickname());
					}else{
						dataVo.setContactId(keyId);
						dataVo.setName(dataPopulation.getName());
						String gender = GenderUtils.byteToChar(dataPopulation.getGender());
						dataVo.setGender(gender);
						String birthday = DateUtil.getStringFromDate(dataPopulation.getBirthday(), "yyyy-MM-dd");
						dataVo.setBirthday(birthday);

						String identifyNo = dataPopulation.getIdentifyNo();
						if (StringUtils.isNotBlank(identifyNo)) {
							dataVo.setIdentifyNo(Long.valueOf(dataPopulation.getIdentifyNo()));
						}

						String drivingLicense = dataPopulation.getDrivingLicense();
						if (StringUtils.isNotBlank(drivingLicense)) {
							dataVo.setDrivingLicense(Long.valueOf(drivingLicense));
						}

						dataVo.setMobile(dataPopulation.getMobile());
						dataVo.setTel(dataPopulation.getTel());
						dataVo.setEmail(dataPopulation.getEmail());
						dataVo.setQq(dataPopulation.getQq());
						dataVo.setWechat(null);
						dataVo.setAcctNo(dataPopulation.getAcctNo());
						dataVo.setCitizenship(dataPopulation.getCitizenship());
						dataVo.setProvice(dataPopulation.getProvice());
						dataVo.setCity(dataPopulation.getCity());
						dataVo.setJob(dataPopulation.getJob());

						DecimalFormat df = new DecimalFormat("0.00");
						BigDecimal monthlyIncome = dataPopulation.getMonthlyIncome();
						if (monthlyIncome != null) {
							String monthIncome = df.format(monthlyIncome);
							dataVo.setMonthlyIncome(monthIncome);
						}

						BigDecimal monthlyConsume = dataPopulation.getMonthlyConsume();

						if (monthlyConsume != null) {
							String monthConsume = df.format(monthlyConsume);
							dataVo.setMonthlyConsume(monthConsume);
						}
					}
				}
				
				
			}
		}
		return dataVo;
		
	}
	
	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput getMainBasicInfo(Integer contactId, Integer dataType, String userToken) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		if (contactId == null || dataType == null) {
			result.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
			result.setMsg("传入参数不合法!");
			return result;
		}

		Integer keyId = this.getKeyIdFromContactInfo(contactId, dataType, userToken);

		// {
		// logger.error("传入错误的data type : {}", dataType);
		// result.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
		// result.setMsg("传入参数data type不合法!");
		// return result;
		// }
		//
		// if (CollectionUtils.isEmpty(partys)) {
		// return result;
		// }

		// DataParty party = new DataParty();
		// party.setMapping_keyid(mobile);
		// List<DataParty> partys = dataPartyDao.selectList(party);
		//
		// if (CollectionUtils.isEmpty(partys)) {
		// return result;
		// }
		//
		// party = partys.get(0);
		//
		// if (party != null) {
		// List<Object> data = new ArrayList<Object>();
		// MainBasicInfoGetOut dataVo = new MainBasicInfoGetOut();
		// dataVo.setContactId(contactId);
		// dataVo.setName(party.getName());
		// if (party.getGender() != null) {
		// dataVo.setGender(GenderUtils.byteToChar(party.getGender()));
		// }
		// if (party.getMobile() != null) {
		// dataVo.setMobile(Long.valueOf(party.getMobile()));
		// }
		// data.add(dataVo);
		// result.setData(data);
		// result.setTotal(data.size());
		// }
		if (keyId != null) {
			result.setCode(ApiErrorCode.BIZ_ERROR_CONTACTINFO_KEYID.getCode());
			result.setMsg("不能获取关联的keyid!");
			return result;
		}
		if (dataType == DataTypeEnum.WECHAT.getCode()){
			/**
			 * 微信视图显示信息
			 */
			BasicContactInfoOut dataVo = this.getWeiXinDataVo(dataType, keyId);
			List<Object> data = new ArrayList<Object>();
			data.add(dataVo);
			result.setData(data);
			result.setTotal(data.size());
		}else{
			DataParty dataParty = dataPartyDao.getDataById(keyId);
			if (dataParty != null) {
				List<Object> data = new ArrayList<Object>();
				BasicContactInfoOut dataVo = new BasicContactInfoOut();

				dataVo.setContactId(contactId);
				dataVo.setName(dataParty.getName());
				String gender = GenderUtils.byteToChar(dataParty.getGender());
				dataVo.setGender(gender);
				String birthday = DateUtil.getStringFromDate(dataParty.getBirthday(), "yyyy-MM-dd");
				dataVo.setBirthday(birthday);
				// dataVo.setBloodType(dataParty.getBloodType());
				// dataVo.setIq(dataParty.getIq());

				String identifyNo = dataParty.getIdentifyNo();
				if (StringUtils.isNotBlank(identifyNo)) {
					dataVo.setIdentifyNo(Long.valueOf(dataParty.getIdentifyNo()));
				}

				String drivingLicense = dataParty.getDrivingLicense();
				if (StringUtils.isNotBlank(drivingLicense)) {
					dataVo.setDrivingLicense(Long.valueOf(drivingLicense));
				}

				dataVo.setMobile(dataParty.getMobile());
				dataVo.setTel(dataParty.getTel());
				dataVo.setEmail(dataParty.getEmail());
				dataVo.setQq(dataParty.getQq());
				dataVo.setWechat(null);
				// dataVo.setAcctType(dataParty.getAcctType());
				dataVo.setAcctNo(dataParty.getAcctNo());
				dataVo.setCitizenship(dataParty.getCitizenship());
				dataVo.setProvice(dataParty.getProvice());
				dataVo.setCity(dataParty.getCity());
				// dataVo.setNationality(dataParty.getNationality());
				dataVo.setJob(dataParty.getJob());

				DecimalFormat df = new DecimalFormat("0.00");

				BigDecimal monthlyIncome = dataParty.getMonthlyIncome();

				if (monthlyIncome != null) {
					String monthIncome = df.format(monthlyIncome);
					dataVo.setMonthlyIncome(monthIncome);
				}

				BigDecimal monthlyConsume = dataParty.getMonthlyConsume();

				if (monthlyConsume != null) {
					String monthConsume = df.format(monthlyConsume);
					dataVo.setMonthlyConsume(monthConsume);
				}
				
				// dataVo.setMaritalStatus(dataParty.getMaritalStatus());
				// dataVo.setEducation(dataParty.getEducation());
				// dataVo.setEmployment(dataParty.getEmployment());
				data.add(dataVo);
				result.setData(data);
				result.setTotal(data.size());

				// dataVo.setContactId(contactId);
				// dataVo.setName(dataPopulation.getName());
				// String gender =
				// GenderUtils.byteToChar(dataPopulation.getGender());
				// dataVo.setGender(gender);
				// String birthday =
				// DateUtil.getStringFromDate(dataPopulation.getBirthday(),"yyyy-MM-dd");
				// dataVo.setBirthday(birthday);
				// dataVo.setBloodType(dataPopulation.getBloodType());
				// dataVo.setIq(dataPopulation.getIq());
				//
				// String identifyNo = dataPopulation.getIdentifyNo();
				// if(StringUtils.isNotBlank(identifyNo)){
				// dataVo.setIdentifyNo(Long.valueOf(dataPopulation.getIdentifyNo()));
				// }
				//
				// String drivingLicense = dataPopulation.getDrivingLicense();
				// if(StringUtils.isNotBlank(drivingLicense)){
				// dataVo.setDrivingLicense(Long.valueOf(drivingLicense));
				// }
				//
				// dataVo.setMobile(dataPopulation.getMobile());
				// dataVo.setTel(dataPopulation.getTel());
				// dataVo.setEmail(dataPopulation.getEmail());
				// dataVo.setQq(dataPopulation.getQq());
				// dataVo.setWechat(null);
				// dataVo.setAcctType(dataPopulation.getAcctType());
				// dataVo.setAcctNo(dataPopulation.getAcctNo());
				// dataVo.setCitizenship(dataPopulation.getCitizenship());
				// dataVo.setProvice(dataPopulation.getProvice());
				// dataVo.setCity(dataPopulation.getCity());
				// dataVo.setNationality(dataPopulation.getNationality());
				// dataVo.setJob(dataPopulation.getJob());
				//
				// DecimalFormat df = new DecimalFormat("0.00");
				//
				// BigDecimal monthlyIncome = dataPopulation.getMonthlyIncome();
				//
				// if(monthlyIncome != null ){
				// String monthIncome = df.format(monthlyIncome);
				// dataVo.setMonthlyIncome(monthIncome);
				// }
				//
				// BigDecimal monthlyConsume = dataPopulation.getMonthlyConsume();
				//
				// if(monthlyConsume != null ){
				// String monthConsume = df.format(monthlyConsume);
				// dataVo.setMonthlyConsume(monthConsume);
				// }
				//
				// dataVo.setMaritalStatus(dataPopulation.getMaritalStatus());
				// dataVo.setEducation(dataPopulation.getEducation());
				// dataVo.setEmployment(dataPopulation.getEmployment());
				// data.add(dataVo);
				// result.setData(data);
				// result.setTotal(data.size());
			}

		}
		return result;
	}

}
