package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataArchPoint;
import cn.rongcapital.mkt.po.DataParty;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataArchPointToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

	@Autowired
	private DataArchPointDao dataArchPointDao;

	@Override
	public int queryTotalCount() {
		DataArchPoint dataArchPoint = new DataArchPoint();
		dataArchPoint.setStatus(StatusEnum.ACTIVE.getStatusCode());
		return dataArchPointDao.selectListCount(dataArchPoint);
	}
 
	@Override
	public DataPartySyncVO<Integer> querySyncData(Integer startIndex, Integer pageSize) {
		DataArchPoint dataArchPoint = new DataArchPoint();
		dataArchPoint.setStatus(StatusEnum.ACTIVE.getStatusCode());
		dataArchPoint.setPageSize(pageSize);
		dataArchPoint.setStartIndex(startIndex);
		List<DataArchPoint> dataArchPointList = dataArchPointDao.selectList(dataArchPoint);
		if (CollectionUtils.isEmpty(dataArchPointList)) {
			return null;
		}
		List<DataParty> dataPartyList = new ArrayList<>(dataArchPointList.size());
		List<Integer> idList = new ArrayList<>(dataArchPointList.size());
		for (DataArchPoint dataObj : dataArchPointList) {

			DataParty dataParty = new DataParty();
//			dataParty.setMappingKeyid(dataObj.getId().toString());
//			dataParty.setMobile(dataObj.getMobile());
			dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
			dataParty.setMdType(DataTypeEnum.ARCH_POINT.getCode());
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
		dataArchPointDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(), StatusEnum.PROCESSED.getStatusCode());

	}

}
