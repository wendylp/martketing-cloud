package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.ListSplit;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPopulation;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataPopulationToDataPartyImpl extends AbstractDataPartySyncService<Integer> {
	
    private static final int THREAD_POOL_FIX_SIZE = 100;
    
    private static final int BATCH_SIZE = 500;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataPopulationDao dataPopulationDao;

	private ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);
	
	@Override
	public int queryTotalCount() {
		DataPopulation dataPopulation = new DataPopulation();
		dataPopulation.setStatus(StatusEnum.ACTIVE.getStatusCode());
		return dataPopulationDao.selectListCount(dataPopulation);
	}

	@Override
	public void querySyncData(Integer totalSize, Integer pageSize) {

		List<DataPopulation> dataPopulationLists = new ArrayList<DataPopulation>();
		
		DataPopulation dataPopulation = new DataPopulation();
		dataPopulation.setStatus(StatusEnum.ACTIVE.getStatusCode());
		
		int totalPages = (totalSize + pageSize - 1) / pageSize;
		
		for (int i = 0; i < totalPages; i++) {
			
			dataPopulation.setPageSize(pageSize);
			dataPopulation.setStartIndex(Integer.valueOf(i * pageSize));
			List<DataPopulation> dataPopulationList = dataPopulationDao.selectList(dataPopulation);
			
			dataPopulationLists.addAll(dataPopulationList);
			
		}

		List<List<DataPopulation>> dataPopulationsList = ListSplit.getListSplit(dataPopulationLists, BATCH_SIZE);
	    
	    for(List<DataPopulation> dataPopulations :dataPopulationsList){
	    	
			List<Integer> idList = new ArrayList<>(dataPopulationLists.size());
			for (DataPopulation dataObj : dataPopulations) {

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
		dataPopulationDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(), StatusEnum.PROCESSED.getStatusCode());
	}

	public void updateKeyidByid(Integer keyid, Integer id) {
		DataPopulation keyidObj = new DataPopulation();
		keyidObj.setId(id);
		keyidObj.setKeyid(keyid);
		dataPopulationDao.updateById(keyidObj);
	}
	
	
	private void createParty(DataPopulation dataObj){
		
		String bitmap = dataObj.getBitmap();

		Integer keyid = super.getDataParyPrimaryKey(dataObj, bitmap);
		if (keyid != null) {
			DataParty dataParty = new DataParty();
			dataParty.setMdType(DataTypeEnum.POPULATION.getCode());
			dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
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
			dataParty.setId(keyid);
			dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);
			dataPartyDao.updateById(dataParty);
			this.updateKeyidByid(keyid, dataObj.getId());
		} else {

			DataParty dataParty = new DataParty();
			dataParty.setMappingKeyid(dataObj.getId().toString());
			dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
			dataParty.setMdType(DataTypeEnum.POPULATION.getCode());
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

			dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);

			dataPartyDao.insert(dataParty);
			this.updateKeyidByid(dataParty.getId(), dataObj.getId());

			// dataPartyList.add(dataParty);
		}
		
	}
}
