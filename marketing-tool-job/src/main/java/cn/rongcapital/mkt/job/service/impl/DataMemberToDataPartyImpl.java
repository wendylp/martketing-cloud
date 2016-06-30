package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataCustomerTags;
import cn.rongcapital.mkt.po.DataMember;
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
public class DataMemberToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

    private static String MD_TYPE = "4";

    @Autowired
    private DataMemberDao dataMemberDao;

    @Override
    public DataPartySyncVO<Integer> querySyncData() {

        DataMember dataMember = new DataMember();
        dataMember.setStatus(StatusEnum.ACTIVE.getStatusCode());
        List<DataMember> dataMemberList = dataMemberDao.selectList(dataMember);
        if (CollectionUtils.isEmpty(dataMemberList)) {
            return null;
        }
        List<DataParty> dataPartyList = new ArrayList<>(dataMemberList.size());
        List<Integer> idList = new ArrayList<>(dataMemberList.size());
        for(DataMember dataObj : dataMemberList){
            DataParty dataParty=new DataParty();
            dataParty.setMemberLevel(dataObj.getMemberLevel());
            dataParty.setMemberPoints(dataObj.getMemberPoints());
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
        dataMemberDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(),
                StatusEnum.PROCESSED.getStatusCode());

    }
}
