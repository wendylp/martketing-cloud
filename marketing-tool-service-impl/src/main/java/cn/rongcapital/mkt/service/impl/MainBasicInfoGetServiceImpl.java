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
    private DataPartyDao dataPartyDao;

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
    
    @Override
    public String getMobileFromContactInfo(Integer contactId, Integer dataType, String userToken){

		String mobile = "";

        if (dataType == DataTypeEnum.PARTY.getCode()) {
            mobile = dataPartyDao.selectMobileById(contactId);
        } else if (dataType == DataTypeEnum.POPULATION.getCode()) {
            mobile = dataPopulationDao.selectMobileById(contactId);
        } else if (dataType == DataTypeEnum.CUSTOMER_TAGS.getCode()) {
            mobile = dataCustomerTagsDao.selectMobileById(contactId);
        } else if (dataType == DataTypeEnum.ARCH_POINT.getCode()) {
            mobile = dataArchPointDao.selectMobileById(contactId);
        } else if (dataType == DataTypeEnum.MEMBER.getCode()) {
            mobile = dataMemberDao.selectMobileById(contactId);
        } else if (dataType == DataTypeEnum.LOGIN.getCode()) {
            mobile = dataLoginDao.selectMobileById(contactId);
        } else if (dataType == DataTypeEnum.PAYMENT.getCode()) {
        } else if (dataType == DataTypeEnum.SHOPPING.getCode()) {
            mobile = dataShoppingDao.selectMobileById(contactId);
        }
		
    	return mobile;
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

        String mobile = this.getMobileFromContactInfo(contactId, dataType, userToken);


//        {
//            logger.error("传入错误的data type : {}", dataType);
//        	result.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
//        	result.setMsg("传入参数data type不合法!");
//            return result;
//        }
//        
//      if (CollectionUtils.isEmpty(partys)) {
//      return result;
//  }

//        DataParty party = new DataParty();
//        party.setMapping_keyid(mobile);
//        List<DataParty> partys = dataPartyDao.selectList(party);
//
//        if (CollectionUtils.isEmpty(partys)) {
//            return result;
//        }
//
//        party = partys.get(0);
//
//        if (party != null) {
//            List<Object> data = new ArrayList<Object>();
//            MainBasicInfoGetOut dataVo = new MainBasicInfoGetOut();
//            dataVo.setContactId(contactId);
//            dataVo.setName(party.getName());
//            if (party.getGender() != null) {
//                dataVo.setGender(GenderUtils.byteToChar(party.getGender()));
//            }
//            if (party.getMobile() != null) {
//                dataVo.setMobile(Long.valueOf(party.getMobile()));
//            }
//            data.add(dataVo);
//            result.setData(data);
//            result.setTotal(data.size());
//        }
        if (StringUtils.isBlank(mobile)) {
        	result.setCode(ApiErrorCode.BIZ_ERROR_CONTACTINFO_MOBILE.getCode());
        	result.setMsg("不能获取手机号!");
            return result;
        }
		DataPopulation dataPopulation = dataPopulationDao.getDataPopulationByMobile(mobile);
		
		if (dataPopulation != null) {
			List<Object> data = new ArrayList<Object>();
			BasicContactInfoOut dataVo = new BasicContactInfoOut();
			
			dataVo.setId(contactId);
			dataVo.setName(dataPopulation.getName());
			String gender = GenderUtils.byteToChar(dataPopulation.getGender());
			dataVo.setGender(gender);
			String birthday = DateUtil.getStringFromDate(dataPopulation.getBirthday(),"yyyy-MM-dd");
			dataVo.setBirthday(birthday);
			dataVo.setBloodType(dataPopulation.getBloodType());
			dataVo.setIq(dataPopulation.getIq());
			
			String identifyNo = dataPopulation.getIdentifyNo();
			if(StringUtils.isNotBlank(identifyNo)){
				dataVo.setIdentifyNo(Long.valueOf(dataPopulation.getIdentifyNo()));
			}
			
			String drivingLicense = dataPopulation.getDrivingLicense();
			if(StringUtils.isNotBlank(drivingLicense)){
				dataVo.setDrivingLicense(Long.valueOf(drivingLicense));
			}

			dataVo.setMobile(dataPopulation.getMobile());
			dataVo.setTel(dataPopulation.getTel());
			dataVo.setEmail(dataPopulation.getEmail());
			dataVo.setQq(dataPopulation.getQq());
			dataVo.setWechat(null);
			dataVo.setAcctType(dataPopulation.getAcctType());
			dataVo.setAcctNo(dataPopulation.getAcctNo());
			dataVo.setCitizenship(dataPopulation.getCitizenship());
			dataVo.setProvice(dataPopulation.getProvice());
			dataVo.setCity(dataPopulation.getCity());
			dataVo.setNationality(dataPopulation.getNationality());
			dataVo.setJob(dataPopulation.getJob());
			
			DecimalFormat df = new DecimalFormat("0.00");
			
			BigDecimal monthlyIncome = dataPopulation.getMonthlyIncome();
			
			if(monthlyIncome != null ){
				String monthIncome = df.format(monthlyIncome);
				dataVo.setMonthlyIncome(monthIncome);
			}
			
			BigDecimal monthlyConsume = dataPopulation.getMonthlyConsume();
			
			if(monthlyConsume != null ){
				String monthConsume = df.format(monthlyConsume);
				dataVo.setMonthlyConsume(monthConsume);
			}
			
			dataVo.setMaritalStatus(dataPopulation.getMaritalStatus());
			dataVo.setEducation(dataPopulation.getEducation());
			dataVo.setEmployment(dataPopulation.getEmployment());
			data.add(dataVo);
			result.setData(data);
			result.setTotal(data.size());
		}
        return result;
    }

}
