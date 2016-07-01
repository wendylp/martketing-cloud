package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataArchPoint;
import cn.rongcapital.mkt.po.DataCustomerTags;
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
public class DataArchPointToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

    private static String MD_TYPE = "3";

    @Autowired
    private DataArchPointDao dataArchPointDao;

    @Override
    public DataPartySyncVO<Integer> querySyncData() {
        DataArchPoint dataArchPoint = new DataArchPoint();
        dataArchPoint.setStatus(StatusEnum.ACTIVE.getStatusCode());
        List<DataArchPoint> dataArchPointList = dataArchPointDao.selectList(dataArchPoint);
        if (CollectionUtils.isEmpty(dataArchPointList)) {
            return null;
        }
        List<DataParty> dataPartyList = new ArrayList<>(dataArchPointList.size());
        List<Integer> idList = new ArrayList<>(dataArchPointList.size());
        for(DataArchPoint dataObj : dataArchPointList){
            DataParty dataParty=new DataParty();
            dataParty.setMappingKeyid(dataObj.getId().toString());
            dataParty.setMdType(Integer.parseInt(MD_TYPE));
            dataParty.setSource(MD_TYPE);
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
        dataArchPointDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(),
                StatusEnum.PROCESSED.getStatusCode());

    }
}
