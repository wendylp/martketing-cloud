/*************************************************
 * @功能简述: 获取某条主数据详细信息
 * @see MktApi：
 * @author: 朱学龙
 * @version: 1.0 @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.DataTypeEnum;
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
import cn.rongcapital.mkt.service.MainBasicInfoGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.MainBasicInfoGetOut;
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
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput getMainBasicInfo(Integer contactId, Integer dataType, String userToken) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);

        if (contactId == null || dataType == null) {
            return result;
        }

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
        } else {
            logger.error("传入错误的data type : {}", dataType);
        }

        DataParty party = new DataParty();
        party.setMapping_keyid(mobile);
        List<DataParty> partys = dataPartyDao.selectList(party);

        if (CollectionUtils.isEmpty(partys)) {
            return result;
        }

        party = partys.get(0);

        if (party != null) {
            List<Object> data = new ArrayList<Object>();
            MainBasicInfoGetOut dataVo = new MainBasicInfoGetOut();
            dataVo.setContactId(contactId);
            dataVo.setName(party.getName());
            if (party.getGender() != null) {
                dataVo.setGender(GenderUtils.byteToChar(party.getGender()));
            }
            if (party.getMobile() != null) {
                dataVo.setMobile(Long.valueOf(party.getMobile()));
            }
            data.add(dataVo);
            result.setData(data);
            result.setTotal(data.size());
        }
        return result;
    }

}
