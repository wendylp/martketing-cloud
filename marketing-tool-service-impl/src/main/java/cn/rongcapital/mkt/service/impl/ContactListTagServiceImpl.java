package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.service.ContactListTagService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactListTagIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class ContactListTagServiceImpl implements ContactListTagService {

	@Autowired
	CustomTagDao tagDao;

	@Autowired
	CustomTagMapDao tagMapDao;

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput contactListTag(ContactListTagIn body) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		
		//获取标签名称
		String[] tag_names = body.getTag_names();
		
		CustomTag tag = new CustomTag();
		int temp = 0;
		
		for (String tagName : tag_names) {
			tag.setName(tagName);
			int count = tagDao.selectListCount(tag);
			if(count > 0){
				continue;
			}
			tagDao.insert(tag);
			temp++;
		}
		//当temp为0时，表示传入的所有标签名称已经存在。
		if (temp == 0) {
			result = new BaseOutput();
			result.setCode(10001);
			result.setMsg("标签名称已存在");
			return result;
		}
		CustomTagMap tagMap = new CustomTagMap();
		tagMap.setTagId(tag.getId());
		tagMap.setMapId(body.getContact_id());
		tagMapDao.insert(tagMap);
		
//		tag.setName(body.getTag_name());

//		int count = tagDao.selectListCount(tag);
		//tagDao.insert(tag);

//		CustomTagMap tagMap = new CustomTagMap();
//		tagMap.setTagId(tag.getId());
//		tagMap.setMapId(body.getContact_id());
//		tagMapDao.insert(tagMap);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", body.getContact_id());
		map.put("updatetime", DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		result.getData().add(map);
		result.setTotal(1);

		return result;
	}
}
