package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataMember;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPayment;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataPaymentToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

    @Autowired
    private DataPaymentDao dataPaymentDao;

    @Override
    public int queryTotalCount() {
        DataPayment dataPayment = new DataPayment();
        dataPayment.setStatus(StatusEnum.ACTIVE.getStatusCode());
        return dataPaymentDao.selectListCount(dataPayment);
    }

    @Override
    public DataPartySyncVO<Integer> querySyncData(Integer startIndex, Integer pageSize) {

        DataPayment dataPayment = new DataPayment();
        dataPayment.setStatus(StatusEnum.ACTIVE.getStatusCode());
        dataPayment.setPageSize(pageSize);
        dataPayment.setStartIndex(startIndex);
        List<DataPayment> dataPaymentList = dataPaymentDao.selectList(dataPayment);
        if (CollectionUtils.isEmpty(dataPaymentList)) {
            return null;
        }
        List<DataParty> dataPartyList = new ArrayList<>(dataPaymentList.size());
        List<Integer> idList = new ArrayList<>(dataPaymentList.size());
        for(DataPayment dataObj : dataPaymentList){
            DataParty dataParty=new DataParty();
//            dataParty.setMobile(dataObj.getMobile());
//            dataParty.setMappingKeyid(dataObj.getId().toString());
            dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
            dataParty.setMdType(DataTypeEnum.PAYMENT.getCode());
            dataParty.setSource(dataObj.getSource());
            dataParty.setBatchId(dataObj.getBatchId());
            
			String bitmap = dataObj.getBitmap();
			if (StringUtils.isNotBlank(bitmap)) {
				try {
					// 获取keyid
					List<String> strlist = super.getAvailableKeyid(bitmap);

					dataParty = (DataParty) super.primaryKeyCopy(dataObj, dataParty, strlist);


				} catch (Exception e) {
					e.printStackTrace();
				}
			}

            dataPartyList.add(dataParty);
            idList.add(dataObj.getId());
        }

        DataPartySyncVO<Integer> dataPartySyncVO = new DataPartySyncVO<>();
        dataPartySyncVO.setDataPartyList(dataPartyList);
        dataPartySyncVO.setExtendDataList(idList);
        return dataPartySyncVO;
    }

    @Override
    public void doSyncAfter(DataPartySyncVO<Integer> dataPartySyncVO) {
        dataPaymentDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(),
                StatusEnum.PROCESSED.getStatusCode());

    }
}
