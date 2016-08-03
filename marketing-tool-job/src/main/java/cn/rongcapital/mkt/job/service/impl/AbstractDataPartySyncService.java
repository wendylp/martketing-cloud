package cn.rongcapital.mkt.job.service.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.KeyidMapBlockDao;
import cn.rongcapital.mkt.job.service.DataPartySyncService;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.KeyidMapBlock;

/**
 * Created by ethan on 16/6/30.
 */
public abstract class AbstractDataPartySyncService<T> implements DataPartySyncService<T> {

	protected int BATCH_SIZE = 500;

	protected static Integer MD_TYPE = Integer.valueOf(0);

	@Autowired
	private DataPartyDao dataPartyDao;

	@Autowired
	private KeyidMapBlockDao keyidMapBlockDao;

	@Override
	public void doSync() {
		int totalCount = this.queryTotalCount();
		if (totalCount < 1) {
			return;
		}

		int totalPages = (totalCount + BATCH_SIZE - 1) / BATCH_SIZE;
		for (int i = 0; i < totalPages; i++) {
			DataPartySyncVO<T> dataPartySyncVO = this.querySyncData(Integer.valueOf(i * BATCH_SIZE),
					Integer.valueOf(BATCH_SIZE));
			if (dataPartySyncVO == null) {
				return;
			}
			List<DataParty> dataPartyList = dataPartySyncVO.getDataPartyList();
			if (CollectionUtils.isEmpty(dataPartyList)) {
				return;
			}
			dataPartyDao.batchInsert(dataPartyList);
			this.doSyncAfter(dataPartySyncVO);
		}

	}

	public List<String> getAvailableKeyid(String bitmap) {
		List<KeyidMapBlock> list = keyidMapBlockDao.selectKeyidMapBlockList();
				
		int length = bitmap.length();
		List<String> strlist = new ArrayList<String>();
		if (length == list.size()) {
			char[] stringArr = bitmap.toCharArray();

			for (int i = 0; i < stringArr.length; i++) {
				if (stringArr[i] == '1') {
					strlist.add(list.get(i).getField());
				}
			}
		}
		return strlist;

	}

	public Object primaryKeyCopy(Object from, Object to, List<String> primaryKeyList) throws Exception {
		for (int i = 0; i < primaryKeyList.size(); i++) {
			String strField = primaryKeyList.get(i);

			PropertyDescriptor pd = new PropertyDescriptor(strField, from.getClass());

			Method m = pd.getReadMethod();

			Object value = (String) m.invoke(from);

			if (value != null) {
				PropertyDescriptor pdto = new PropertyDescriptor(strField, to.getClass());

				Method mto = pdto.getWriteMethod();
				mto.invoke(to, value);
			}
		}

		return to;
	}
}
