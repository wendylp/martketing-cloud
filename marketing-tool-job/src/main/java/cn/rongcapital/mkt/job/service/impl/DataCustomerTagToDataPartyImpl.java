package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.ListSplit;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataCustomerTags;
import cn.rongcapital.mkt.po.DataParty;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataCustomerTagToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

    private static final int THREAD_POOL_FIX_SIZE = 100;
    
    private static final int BATCH_SIZE = 500;
    
	@Autowired
	private DataCustomerTagsDao dataCustomerTagsDao;
	
	private ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);

	@Override
	public int queryTotalCount() {
		DataCustomerTags dataCustomerTags = new DataCustomerTags();
		dataCustomerTags.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
		return dataCustomerTagsDao.selectListCount(dataCustomerTags);
	}

	@Override
	public void querySyncData(Integer totalSize, Integer pageSize) {

		List<DataCustomerTags> dataCustomerTagsLists = new ArrayList<DataCustomerTags>();
		
		DataCustomerTags dataCustomerTags = new DataCustomerTags();
		dataCustomerTags.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
		
		int totalPages = (totalSize + pageSize - 1) / pageSize;
		
		for (int i = 0; i < totalPages; i++) {
			
			dataCustomerTags.setPageSize(pageSize);
			dataCustomerTags.setStartIndex(Integer.valueOf(i * pageSize));
			List<DataCustomerTags> dataCustomerTagsList = dataCustomerTagsDao.selectList(dataCustomerTags);
			
			dataCustomerTagsLists.addAll(dataCustomerTagsList);
			
		}

		List<List<DataCustomerTags>> dataCustomerTagssList = ListSplit.getListSplit(dataCustomerTagsLists, BATCH_SIZE);
	    
	    for(List<DataCustomerTags> dataCustomerTagss :dataCustomerTagssList){
	    	
			List<Integer> idList = new ArrayList<>(dataCustomerTagsLists.size());
			for (DataCustomerTags dataObj : dataCustomerTagss) {

	            executor.submit(new Callable<Void>() {

	    			@Override
	    			public Void call() throws Exception {
	    				
	    				createParty(dataObj);
	    				
	    				return null;
	    			}
	    			
	            });
				
				idList.add(dataObj.getId());
			}
	    	
			DataPartySyncVO<Integer> dataPartySyncVO = new DataPartySyncVO<>();
			dataPartySyncVO.setExtendDataList(idList);
			doSyncAfter(dataPartySyncVO);
	    }
		
	}

	@Override
	public void doSyncAfter(DataPartySyncVO<Integer> dataPartySyncVO) {
		dataCustomerTagsDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(),
				StatusEnum.PROCESSED.getStatusCode());

	}
	
	public void updateKeyidByid(Integer keyid, Integer id) {
		DataCustomerTags keyidObj = new DataCustomerTags();
		keyidObj.setId(id);
		keyidObj.setKeyid(keyid);
		dataCustomerTagsDao.updateById(keyidObj);
	}
	
	private void createParty(DataCustomerTags dataObj){
		String bitmap = dataObj.getBitmap();

		Integer keyid = super.getDataParyPrimaryKey(dataObj, bitmap);
		if (keyid != null) {
			this.updateKeyidByid(keyid, dataObj.getId());
		} else {

			DataParty dataParty = new DataParty();
			dataParty.setMdType(DataTypeEnum.CUSTOMER_TAGS.getCode());
			dataParty.setSource(dataObj.getSource());
			dataParty.setBatchId(dataObj.getBatchId());
			dataParty.setMobile(dataObj.getMobile());

			dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);

			dataPartyDao.insert(dataParty);
			this.updateKeyidByid(dataParty.getId(), dataObj.getId());

		}
		
	}
}
