/*************************************************
 * @功能简述: 获取某条主数据详细信息
 * @see MktApi：
 * @author: 朱学龙
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.service.MainBasicInfoGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.MainBasicInfoGetOut;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class MainBasicInfoGetServiceImpl implements MainBasicInfoGetService {

	@Autowired
	DataPartyDao dataPartyDao;

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput getMainBasicInfo(String contactId, String userToken) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		Integer id = Integer.valueOf(contactId);
		DataParty party = dataPartyDao.getDataById(id);
		if (party != null) {
			List<Object> data = new ArrayList<Object>();
			MainBasicInfoGetOut dataVo = new MainBasicInfoGetOut();
			dataVo.setContactId(id);
			dataVo.setName(party.getName());
			if(party.getGender()!=null){
			    dataVo.setGender(Integer.valueOf(party.getGender()));
			}
			if(party.getMobile() != null){
			    dataVo.setMobile(Long.valueOf(party.getMobile()));
			}
			data.add(dataVo);
			result.setData(data);
			result.setTotal(data.size());
		}
		return result;
	}

}
