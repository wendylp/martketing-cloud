package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataArchPoint;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataShopping;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataShoppingToDataPartyImpl extends AbstractDataPartySyncService<Integer> {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataShoppingDao dataShoppingDao;

	@Override
	public int queryTotalCount() {
		DataShopping dataShopping = new DataShopping();
		dataShopping.setStatus(StatusEnum.ACTIVE.getStatusCode());
		return dataShoppingDao.selectListCount(dataShopping);
	}

	@Override
	public DataPartySyncVO<Integer> querySyncData(Integer startIndex, Integer pageSize) {
		DataShopping dataShopping = new DataShopping();
		dataShopping.setStatus(StatusEnum.ACTIVE.getStatusCode());
		dataShopping.setPageSize(pageSize);
		dataShopping.setStartIndex(startIndex);
		List<DataShopping> dataShoppingList = dataShoppingDao.selectList(dataShopping);
		if (CollectionUtils.isEmpty(dataShoppingList)) {
			return null;
		}
		// List<DataParty> dataPartyList = new
		// ArrayList<>(dataShoppingList.size());
		List<Integer> idList = new ArrayList<>(dataShoppingList.size());
		for (DataShopping dataObj : dataShoppingList) {

			String bitmap = dataObj.getBitmap();

			Integer keyid = super.getDataParyPrimaryKey(dataObj, bitmap);
			if (keyid != null) {
				this.updateKeyidByid(keyid, dataObj.getId());
			} else {
				DataParty dataParty = new DataParty();
				// dataParty.setMobile(dataObj.getMobile());
				// dataParty.setMappingKeyid(dataObj.getId().toString());
				// dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
				dataParty.setMdType(DataTypeEnum.SHOPPING.getCode());
				dataParty.setSource(dataObj.getSource());
				dataParty.setBatchId(dataObj.getBatchId());

				dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);

				dataPartyDao.insert(dataParty);
				this.updateKeyidByid(dataParty.getId(), dataObj.getId());

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
		dataShoppingDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(), StatusEnum.PROCESSED.getStatusCode());
	}

	public void updateKeyidByid(Integer keyid, Integer id) {
		DataShopping keyidObj = new DataShopping();
		keyidObj.setId(id);
		keyidObj.setKeyid(keyid);
		dataShoppingDao.updateById(keyidObj);
	}
}
