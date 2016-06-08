/*************************************************
 * @功能简述: 编辑某条主数据详细信息的业务类实现
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.service.DataMainBasicInfoUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.DataMainBaseInfoUpdateIn;

@Service
public class DataMainBasicInfoUpdateServiceImpl implements DataMainBasicInfoUpdateService{
	@Autowired
	DataPartyDao dataPartyDao;
	
	@Override
	@ReadWrite(type = ReadWriteType.WRITE)
	public BaseOutput updateBaseInfoByContactId(DataMainBaseInfoUpdateIn body) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		
		DataParty po = new DataParty();
		po.setId(body.getContactId());
		po.setName(body.getName());
		po.setGender(body.getGender());
		po.setAge(body.getAge());
		if(!StringUtils.isEmpty(body.getMobile())){
			po.setMobile(Long.parseLong(body.getMobile()));
		}
		po.setEmail(body.getEmail());
		po.setHomeAddress(body.getHomeAddress());
		po.setWorkAddress(body.getWorkAddress());
		if(!StringUtils.isEmpty(body.getQq())){
		    po.setQq(Integer.parseInt(body.getQq()));
		}
		po.setWechat(body.getWechat());
		po.setWeibo(body.getWeibo());
		if(!StringUtils.isEmpty(body.getChildAmount())){
			po.setChildAmount(Byte.parseByte(body.getChildAmount()));
		}
		if(!StringUtils.isEmpty(body.getSalary())){
			po.setSalary(Long.parseLong(body.getSalary()));
		}
		
		int res = dataPartyDao.updateById(po);
		if(res == 0){
			result.setCode(ApiErrorCode.DB_ERROR.getCode());
			result.setMsg(ApiErrorCode.DB_ERROR.getMsg());
		}
		return result;
	}
}
