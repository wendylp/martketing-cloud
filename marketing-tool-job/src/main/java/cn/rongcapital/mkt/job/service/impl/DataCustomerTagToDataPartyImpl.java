package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataCustomerTags;
import cn.rongcapital.mkt.po.DataParty;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataCustomerTagToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

    @Autowired
    private DataCustomerTagsDao dataCustomerTagsDao;

    @Override
    public int queryTotalCount() {
        DataCustomerTags dataCustomerTags = new DataCustomerTags();
        dataCustomerTags.setStatus(StatusEnum.ACTIVE.getStatusCode());
        return dataCustomerTagsDao.selectListCount(dataCustomerTags);
    }

    @Override
    public DataPartySyncVO<Integer> querySyncData(Integer startIndex, Integer pageSize) {

        DataCustomerTags dataCustomerTags = new DataCustomerTags();
        dataCustomerTags.setStatus(StatusEnum.ACTIVE.getStatusCode());
        dataCustomerTags.setPageSize(pageSize);
        dataCustomerTags.setStartIndex(startIndex);
        List<DataCustomerTags> dataCustomerTagsList = dataCustomerTagsDao.selectList(dataCustomerTags);
        if (CollectionUtils.isEmpty(dataCustomerTagsList)) {
            return null;
        }
        List<DataParty> dataPartyList = new ArrayList<>(dataCustomerTagsList.size());
        List<Integer> idList = new ArrayList<>(dataCustomerTagsList.size());
        for(DataCustomerTags dataObj : dataCustomerTagsList){
            DataParty dataParty=new DataParty();
//            dataParty.setMobile(dataObj.getMobile());
//            dataParty.setMappingKeyid(dataObj.getId().toString());
            dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
            dataParty.setMdType(DataTypeEnum.CUSTOMER_TAGS.getCode());
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
        dataCustomerTagsDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(),
                StatusEnum.PROCESSED.getStatusCode());

    }
}
