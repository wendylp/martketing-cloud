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
import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataArchPoint;
import cn.rongcapital.mkt.po.DataParty;


/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataArchPointToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

	private static final int THREAD_POOL_FIX_SIZE = 100;
	
	private static final int BATCH_SIZE = 500;
	
	@Autowired
	private DataArchPointDao dataArchPointDao;

	private ExecutorService executor = null;
	
	@Override
	public int queryTotalCount() {
		DataArchPoint dataArchPoint = new DataArchPoint();
		dataArchPoint.setStatus(StatusEnum.ACTIVE.getStatusCode());
		return dataArchPointDao.selectListCount(dataArchPoint);
	}

	@Override
	public void querySyncData(Integer totalSize, Integer pageSize) {
		
		executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);
		
		List<DataArchPoint> dataArchPointLists = new ArrayList<DataArchPoint>();
		
		DataArchPoint dataArchPoint = new DataArchPoint();
		dataArchPoint.setStatus(StatusEnum.ACTIVE.getStatusCode());
		
		int totalPages = (totalSize + pageSize - 1) / pageSize;
		
		for (int i = 0; i < totalPages; i++) {
			
			dataArchPoint.setPageSize(pageSize);
			dataArchPoint.setStartIndex(Integer.valueOf(i * pageSize));
			List<DataArchPoint> dataArchPointList = dataArchPointDao.selectList(dataArchPoint);
			
			dataArchPointLists.addAll(dataArchPointList);
			
		}

		List<List<DataArchPoint>> dataArchPointsList = ListSplit.getListSplit(dataArchPointLists, BATCH_SIZE);
	    
	    for(List<DataArchPoint> dataArchPoints :dataArchPointsList){
	    	
			List<Integer> idList = new ArrayList<>(dataArchPointLists.size());
			for (DataArchPoint dataObj : dataArchPoints) {

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
		dataArchPointDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(), StatusEnum.PROCESSED.getStatusCode());
	}

	public void updateKeyidByid(Integer keyid, Integer id) {
		DataArchPoint keyidObj = new DataArchPoint();
		keyidObj.setId(id);
		keyidObj.setKeyid(keyid);
		dataArchPointDao.updateById(keyidObj);
	}
	
	private void createParty(DataArchPoint dataObj){
		
		String bitmap = dataObj.getBitmap();

		Integer keyid = super.getDataParyPrimaryKey(dataObj, bitmap);
		if (keyid != null) {
			this.updateKeyidByid(keyid, dataObj.getId());
		} else {

			DataParty dataParty = new DataParty();
			dataParty.setMdType(DataTypeEnum.ARCH_POINT.getCode());
			dataParty.setSource(dataObj.getSource());
			dataParty.setBatchId(dataObj.getBatchId());

			dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);

			dataPartyDao.insert(dataParty);

			this.updateKeyidByid(dataParty.getId(), dataObj.getId());

		}
	}
}
