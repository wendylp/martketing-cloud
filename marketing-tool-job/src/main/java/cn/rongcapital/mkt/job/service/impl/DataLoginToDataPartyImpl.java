package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.ListSplit;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataLogin;
import cn.rongcapital.mkt.po.DataParty;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataLoginToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

	private static final int THREAD_POOL_FIX_SIZE = 100;
	
	private static final int BATCH_SIZE = 500;
	
	@Autowired
	private DataLoginDao dataLoginDao;

	private ExecutorService executor = null;
	
	@Override
	public int queryTotalCount() {
		DataLogin dataLogin = new DataLogin();
		dataLogin.setStatus(StatusEnum.ACTIVE.getStatusCode());
		return dataLoginDao.selectListCount(dataLogin);
	}

	@Override
	public void querySyncData(Integer totalSize, Integer pageSize) {
		
		executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);
		
		List<DataLogin> dataLoginLists = new ArrayList<DataLogin>();
		
		DataLogin dataLogin = new DataLogin();
		dataLogin.setStatus(StatusEnum.ACTIVE.getStatusCode());
		
		int totalPages = (totalSize + pageSize - 1) / pageSize;
		
		for (int i = 0; i < totalPages; i++) {
			
			dataLogin.setPageSize(pageSize);
			dataLogin.setStartIndex(Integer.valueOf(i * pageSize));
			List<DataLogin> dataLoginList = dataLoginDao.selectList(dataLogin);
			
			dataLoginLists.addAll(dataLoginList);
			
		}

		List<List<DataLogin>> dataLoginsList = ListSplit.getListSplit(dataLoginLists, BATCH_SIZE);
	    
	    for(List<DataLogin> dataLogins :dataLoginsList){
	    	
			List<Integer> idList = new ArrayList<>(dataLoginLists.size());
			for (DataLogin dataObj : dataLogins) {

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
		dataLoginDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(), StatusEnum.PROCESSED.getStatusCode());

	}
	
	public void updateKeyidByid(Integer keyid, Integer id) {
		DataLogin keyidObj = new DataLogin();
		keyidObj.setId(id);
		keyidObj.setKeyid(keyid);
		dataLoginDao.updateById(keyidObj);
	}
	
	private void createParty(DataLogin dataObj){
		
		String bitmap = dataObj.getBitmap();

		Integer keyid = super.getDataParyPrimaryKey(dataObj, bitmap);
		if (keyid != null) {
			this.updateKeyidByid(keyid, dataObj.getId());
		} else {

			DataParty dataParty = new DataParty();
			dataParty.setLastLogin(dataObj.getLoginTime());
			dataParty.setMdType(DataTypeEnum.LOGIN.getCode());
			dataParty.setSource(dataObj.getSource());
			dataParty.setBatchId(dataObj.getBatchId());

			dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);

			dataPartyDao.insert(dataParty);
			this.updateKeyidByid(dataParty.getId(), dataObj.getId());

		}
		
	}
}
