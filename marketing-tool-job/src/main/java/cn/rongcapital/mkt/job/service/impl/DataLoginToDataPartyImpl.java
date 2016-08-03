package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataLogin;
import cn.rongcapital.mkt.po.DataParty;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataLoginToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

    @Autowired
    private DataLoginDao dataLoginDao;

    @Override
    public int queryTotalCount() {
        DataLogin dataLogin = new DataLogin();
        dataLogin.setStatus(StatusEnum.ACTIVE.getStatusCode());
        return dataLoginDao.selectListCount(dataLogin);
    }

    @Override
    public DataPartySyncVO<Integer> querySyncData(Integer startIndex, Integer pageSize) {
        DataLogin dataLogin = new DataLogin();
        dataLogin.setStatus(StatusEnum.ACTIVE.getStatusCode());
        dataLogin.setPageSize(pageSize);
        dataLogin.setStartIndex(startIndex);
        List<DataLogin> dataLoginList = dataLoginDao.selectList(dataLogin);
        if (CollectionUtils.isEmpty(dataLoginList)) {
            return null;
        }
        List<DataParty> dataPartyList = new ArrayList<>(dataLoginList.size());
        List<Integer> idList = new ArrayList<>(dataLoginList.size());
        for(DataLogin dataObj : dataLoginList){
            DataParty dataParty=new DataParty();
            dataParty.setLastLogin(dataObj.getLoginTime());
//            dataParty.setMobile(dataObj.getMobile());
//            dataParty.setMappingKeyid(dataObj.getId().toString());
            dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
            dataParty.setMdType(DataTypeEnum.LOGIN.getCode());
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
        dataLoginDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(),
                StatusEnum.PROCESSED.getStatusCode());

    }
}
