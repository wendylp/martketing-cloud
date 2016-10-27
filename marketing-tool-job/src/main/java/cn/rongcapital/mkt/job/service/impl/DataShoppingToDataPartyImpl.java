package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.ListSplit;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataArchPoint;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.DataShopping;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataShoppingToDataPartyImpl extends AbstractDataPartySyncService<Integer> {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final int THREAD_POOL_FIX_SIZE = 100;
	
	private static final int BATCH_SIZE = 500;
	
	@Autowired
	private DataShoppingDao dataShoppingDao;

	private ExecutorService executor = null;
	
	@Override
	public int queryTotalCount() {
		DataShopping dataShopping = new DataShopping();
		dataShopping.setStatus(StatusEnum.ACTIVE.getStatusCode());
		return dataShoppingDao.selectListCount(dataShopping);
	}

	@Override
	public void querySyncData(Integer totalSize, Integer pageSize) {

		executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);
		
    	logger.info("=====================同步购物记录属性到主数据开始");
    	long startTime = System.currentTimeMillis();
		
		List<DataShopping> dataShoppingLists = new ArrayList<DataShopping>();
		
		DataShopping dataShopping = new DataShopping();
		dataShopping.setStatus(StatusEnum.ACTIVE.getStatusCode());
		
		int totalPages = (totalSize + pageSize - 1) / pageSize;
		
		for (int i = 0; i < totalPages; i++) {
			
			dataShopping.setPageSize(pageSize);
			dataShopping.setStartIndex(Integer.valueOf(i * pageSize));
			List<DataShopping> dataShoppingList = dataShoppingDao.selectList(dataShopping);
			
			dataShoppingLists.addAll(dataShoppingList);
			
		}

		List<List<DataShopping>> dataShoppingsList = ListSplit.getListSplit(dataShoppingLists, BATCH_SIZE);
	    
	    for(List<DataShopping> dataShoppings :dataShoppingsList){
	    	
			List<Integer> idList = new ArrayList<>(dataShoppingLists.size());
			for (DataShopping dataObj : dataShoppings) {

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
	    
	    
        executor.shutdown();
      try {
    	  //设置最大阻塞时间，所有线程任务执行完成再继续往下执行
    	  executor.awaitTermination(24, TimeUnit.HOURS);
    	  long endTime = System.currentTimeMillis();
    	  
    	  logger.info("=====================同步购物记录到主数据结束,用时"+ (endTime-startTime) + "毫秒" );
    	  
      } catch (InterruptedException e) {
    	  logger.info("======================同步购物记录到主数据超时" );
      }
	}	

	@Override
	public void doSyncAfter(DataPartySyncVO<Integer> dataPartySyncVO) {
		dataShoppingDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(), StatusEnum.PROCESSED.getStatusCode());
	}

	public void updateKeyidByid(Integer keyid, Integer id) {
		DataShopping keyidObj = new DataShopping();
		keyidObj.setId(id);
		keyidObj.setKeyid(keyid);
		dataShoppingDao.updateById(keyidObj);
	}

	private void createParty(DataShopping dataObj){
		
		String bitmap = dataObj.getBitmap();

		Integer keyid = super.getDataParyPrimaryKey(dataObj, bitmap);
		if (keyid != null) {
			this.updateKeyidByid(keyid, dataObj.getId());
		} else {
			DataParty dataParty = new DataParty();

			dataParty.setMdType(DataTypeEnum.SHOPPING.getCode());
			dataParty.setSource(dataObj.getSource());
			dataParty.setBatchId(dataObj.getBatchId());

			dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);

			dataPartyDao.insert(dataParty);
			this.updateKeyidByid(dataParty.getId(), dataObj.getId());

		}
	}
}
