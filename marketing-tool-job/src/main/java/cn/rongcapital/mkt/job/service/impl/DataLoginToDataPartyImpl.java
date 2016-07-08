package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataArchPoint;
import cn.rongcapital.mkt.po.DataLogin;
import cn.rongcapital.mkt.po.DataParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataLoginToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

    private static String MD_TYPE = "5";

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
            dataParty.setMappingKeyid(dataObj.getMobile());
            dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
            dataParty.setMdType(Integer.parseInt(MD_TYPE));
            dataParty.setSource(dataObj.getSource());
            dataParty.setBatchId(dataObj.getBatchId());

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
