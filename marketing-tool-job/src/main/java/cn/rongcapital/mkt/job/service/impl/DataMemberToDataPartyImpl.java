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
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataMember;
import cn.rongcapital.mkt.po.DataParty;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataMemberToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

	private static final int THREAD_POOL_FIX_SIZE = 100;
	
	private static final int BATCH_SIZE = 500;
	
	@Autowired
	private DataMemberDao dataMemberDao;
	
	private ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);

	@Override
	public int queryTotalCount() {
		DataMember dataMember = new DataMember();
		dataMember.setStatus(StatusEnum.ACTIVE.getStatusCode());
		return dataMemberDao.selectListCount(dataMember);
	}

	@Override
	public void querySyncData(Integer totalSize, Integer pageSize) {

		List<DataMember> dataMemberLists = new ArrayList<DataMember>();
		
		DataMember dataMember = new DataMember();
		dataMember.setStatus(StatusEnum.ACTIVE.getStatusCode());
		
		int totalPages = (totalSize + pageSize - 1) / pageSize;
		
		for (int i = 0; i < totalPages; i++) {
			
			dataMember.setPageSize(pageSize);
			dataMember.setStartIndex(Integer.valueOf(i * pageSize));
			List<DataMember> dataMemberList = dataMemberDao.selectList(dataMember);
			
			dataMemberLists.addAll(dataMemberList);
			
		}

		List<List<DataMember>> dataMembersList = ListSplit.getListSplit(dataMemberLists, BATCH_SIZE);
	    
	    for(List<DataMember> dataMembers :dataMembersList){
	    	
			List<Integer> idList = new ArrayList<>(dataMemberLists.size());
			for (DataMember dataObj : dataMembers) {

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
		dataMemberDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(), StatusEnum.PROCESSED.getStatusCode());

	}
	
	public void updateKeyidByid(Integer keyid, Integer id) {
		DataMember keyidObj = new DataMember();
		keyidObj.setId(id);
		keyidObj.setKeyid(keyid);
		dataMemberDao.updateById(keyidObj);
	}
	
	private void createParty(DataMember dataObj){
		
		String bitmap = dataObj.getBitmap();

		Integer keyid = super.getDataParyPrimaryKey(dataObj, bitmap);
		if (keyid != null) {
			this.updateKeyidByid(keyid, dataObj.getId());
		} else {
			DataParty dataParty = new DataParty();
			dataParty.setMemberLevel(dataObj.getMemberLevel());
			dataParty.setMemberPoints(dataObj.getMemberPoints());
			// dataParty.setMobile(dataObj.getMobile());
			// dataParty.setMappingKeyid(dataObj.getId().toString());
			// dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
			dataParty.setMdType(DataTypeEnum.MEMBER.getCode());
			dataParty.setSource(dataObj.getSource());
			dataParty.setBatchId(dataObj.getBatchId());

			dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);

			dataPartyDao.insert(dataParty);
			this.updateKeyidByid(dataParty.getId(), dataObj.getId());

			// dataPartyList.add(dataParty);
		}
		
	}
}
