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
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPayment;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataPaymentToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

	public static final int THREAD_POOL_FIX_SIZE = 100;
	
	private static final int BATCH_SIZE = 500;
	
	@Autowired
	private DataPaymentDao dataPaymentDao;
	
	private ExecutorService executor = null;

	@Override
	public int queryTotalCount() {
		DataPayment dataPayment = new DataPayment();
		dataPayment.setStatus(StatusEnum.ACTIVE.getStatusCode());
		return dataPaymentDao.selectListCount(dataPayment);
	}

	@Override
	public void querySyncData(Integer totalSize, Integer pageSize) {

		executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);
		
		List<DataPayment> dataPaymentLists = new ArrayList<DataPayment>();
		
		DataPayment dataPayment = new DataPayment();
		dataPayment.setStatus(StatusEnum.ACTIVE.getStatusCode());
		
		int totalPages = (totalSize + pageSize - 1) / pageSize;
		
		for (int i = 0; i < totalPages; i++) {
			
			dataPayment.setPageSize(pageSize);
			dataPayment.setStartIndex(Integer.valueOf(i * pageSize));
			List<DataPayment> dataPaymentList = dataPaymentDao.selectList(dataPayment);
			
			dataPaymentLists.addAll(dataPaymentList);
			
		}

		List<List<DataPayment>> dataPaymentsList = ListSplit.getListSplit(dataPaymentLists, BATCH_SIZE);
	    
	    for(List<DataPayment> dataPayments :dataPaymentsList){
	    	
			List<Integer> idList = new ArrayList<>(dataPaymentLists.size());
			for (DataPayment dataObj : dataPayments) {

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
		dataPaymentDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(), StatusEnum.PROCESSED.getStatusCode());

	}

	public void updateKeyidByid(Integer keyid, Integer id) {
		DataPayment keyidObj = new DataPayment();
		keyidObj.setId(id);
		keyidObj.setKeyid(keyid);
		dataPaymentDao.updateById(keyidObj);
	}
	
	private void createParty(DataPayment dataObj){
		
		String bitmap = dataObj.getBitmap();

		Integer keyid = super.getDataParyPrimaryKey(dataObj, bitmap);
		if (keyid != null) {
			this.updateKeyidByid(keyid, dataObj.getId());
		} else {

			DataParty dataParty = new DataParty();
			dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
			dataParty.setMdType(DataTypeEnum.PAYMENT.getCode());
			dataParty.setSource(dataObj.getSource());
			dataParty.setBatchId(dataObj.getBatchId());

			dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);

			dataPartyDao.insert(dataParty);
			this.updateKeyidByid(dataParty.getId(), dataObj.getId());

		}
		
	}
}
