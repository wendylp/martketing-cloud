package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataMember;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataShopping;

import org.apache.commons.lang3.StringUtils;
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
        List<DataParty> dataPartyList = new ArrayList<>(dataShoppingList.size());
        List<Integer> idList = new ArrayList<>(dataShoppingList.size());
        for(DataShopping dataObj : dataShoppingList){
            DataParty dataParty=new DataParty();
//            dataParty.setMobile(dataObj.getMobile());
//            dataParty.setMappingKeyid(dataObj.getId().toString());
            dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
            dataParty.setMdType(DataTypeEnum.SHOPPING.getCode());
            dataParty.setSource(dataObj.getSource());
            dataParty.setBatchId(dataObj.getBatchId());
            
			String bitmap = dataObj.getBitmap();
			dataParty.setBitmap(bitmap);
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
        dataShoppingDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(),
                StatusEnum.PROCESSED.getStatusCode());

    }
}
