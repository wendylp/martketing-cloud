package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataMember;
import cn.rongcapital.mkt.po.DataParty;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataMemberToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

    @Autowired
    private DataMemberDao dataMemberDao;

    @Override
    public int queryTotalCount() {
        DataMember dataMember = new DataMember();
        dataMember.setStatus(StatusEnum.ACTIVE.getStatusCode());
        return dataMemberDao.selectListCount(dataMember);
    }

    @Override
    public DataPartySyncVO<Integer> querySyncData(Integer startIndex, Integer pageSize) {

        DataMember dataMember = new DataMember();
        dataMember.setStatus(StatusEnum.ACTIVE.getStatusCode());
        dataMember.setPageSize(pageSize);
        dataMember.setStartIndex(startIndex);
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
//            dataParty.setMobile(dataObj.getMobile());
//            dataParty.setMappingKeyid(dataObj.getId().toString());
            dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
            dataParty.setMdType(DataTypeEnum.MEMBER.getCode());
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
        dataMemberDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(),
                StatusEnum.PROCESSED.getStatusCode());

    }
}
