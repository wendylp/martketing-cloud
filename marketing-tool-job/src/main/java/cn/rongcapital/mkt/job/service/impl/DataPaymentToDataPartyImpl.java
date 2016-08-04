package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPayment;

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
		// List<DataParty> dataPartyList = new
		// ArrayList<>(dataPaymentList.size());
		List<Integer> idList = new ArrayList<>(dataPaymentList.size());
		for (DataPayment dataObj : dataPaymentList) {
			String bitmap = dataObj.getBitmap();

			Integer keyid = super.getDataParyPrimaryKey(dataObj, bitmap);
			if (keyid != null) {
				this.updateKeyidByid(keyid, dataObj.getId());
			} else {

				DataParty dataParty = new DataParty();
				// dataParty.setMobile(dataObj.getMobile());
				// dataParty.setMappingKeyid(dataObj.getId().toString());
				dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
				dataParty.setMdType(DataTypeEnum.PAYMENT.getCode());
				dataParty.setSource(dataObj.getSource());
				dataParty.setBatchId(dataObj.getBatchId());

				dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);

				dataPartyDao.insert(dataParty);
				this.updateKeyidByid(dataParty.getId(), dataObj.getId());

				// dataPartyList.add(dataParty);
			}
			idList.add(dataObj.getId());
		}

		DataPartySyncVO<Integer> dataPartySyncVO = new DataPartySyncVO<>();
		// dataPartySyncVO.setDataPartyList(dataPartyList);
		dataPartySyncVO.setExtendDataList(idList);
		return dataPartySyncVO;
	}

	@Override
	public void doSyncAfter(DataPartySyncVO<Integer> dataPartySyncVO) {
		dataPaymentDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(), StatusEnum.PROCESSED.getStatusCode());

	}

	public void updateKeyidByid(Integer keyid, Integer id) {
		DataPayment keyidObj = new DataPayment();
		keyidObj.setId(id);
		keyidObj.setKeyid(keyid);
		dataPaymentDao.updateById(keyidObj);
	}
}
