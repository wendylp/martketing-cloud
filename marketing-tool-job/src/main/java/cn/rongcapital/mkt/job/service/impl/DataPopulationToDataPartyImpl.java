package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPopulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataPopulationToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

    private static String MD_TYPE = "1";

    @Autowired
    private DataPopulationDao dataPopulationDao;

    @Override
    public int queryTotalCount() {
        DataPopulation dataPopulation = new DataPopulation();
        dataPopulation.setStatus(StatusEnum.ACTIVE.getStatusCode());
        return dataPopulationDao.selectListCount(dataPopulation);
    }

    @Override
    public DataPartySyncVO<Integer> querySyncData(Integer startIndex, Integer pageSize) {

        DataPopulation dataPopulation = new DataPopulation();
        dataPopulation.setStatus(StatusEnum.ACTIVE.getStatusCode());
        dataPopulation.setPageSize(pageSize);
        dataPopulation.setStartIndex(startIndex);
        List<DataPopulation> dataPopulationList = dataPopulationDao.selectList(dataPopulation);
        if (CollectionUtils.isEmpty(dataPopulationList)) {
            return null;
        }
        List<DataParty> dataPartyList = new ArrayList<>(dataPopulationList.size());
        List<Integer> idList = new ArrayList<>(dataPopulationList.size());
        for(DataPopulation dataObj : dataPopulationList){
            DataParty dataParty=new DataParty();
            dataParty.setMappingKeyid(dataObj.getMobile());
            dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
            dataParty.setMdType(Integer.parseInt(MD_TYPE));
            dataParty.setMobile(dataObj.getMobile());
            dataParty.setName(dataObj.getName());
            dataParty.setGender(dataObj.getGender());
            dataParty.setBirthday(dataObj.getBirthday());
            dataParty.setCitizenship(dataObj.getCitizenship());
            dataParty.setProvice(dataObj.getProvice());
            dataParty.setCity(dataObj.getCity());
            dataParty.setJob(dataObj.getJob());
            dataParty.setMonthlyIncome(dataObj.getMonthlyIncome());
            dataParty.setMonthlyConsume(dataObj.getMonthlyConsume());
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
        dataPopulationDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(),
                StatusEnum.PROCESSED.getStatusCode());

    }
}
