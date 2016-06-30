package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataMember;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataShopping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataShoppingToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

    private static String MD_TYPE = "7";

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Override
    public DataPartySyncVO<Integer> querySyncData() {

        DataShopping dataShopping = new DataShopping();
        dataShopping.setStatus(StatusEnum.ACTIVE.getStatusCode());
        List<DataShopping> dataShoppingList = dataShoppingDao.selectList(dataShopping);
        if (CollectionUtils.isEmpty(dataShoppingList)) {
            return null;
        }
        List<DataParty> dataPartyList = new ArrayList<>(dataShoppingList.size());
        List<Integer> idList = new ArrayList<>(dataShoppingList.size());
        for(DataShopping dataObj : dataShoppingList){
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
        dataShoppingDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(),
                StatusEnum.PROCESSED.getStatusCode());

    }
}
