/*************************************************
 * @功能简述: ContactTemplateServer实现类
 * @see: MktApi
 * @author: 杨玉麟
 * @version: 1.0
 * @date: 2016/8/12
*************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactTemplateServer;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactTemplateIn;

@Service
public class ContactTemplateServerImpl implements ContactTemplateServer {

	@Autowired
	ContactTemplateDao contactTemplateDao;

	@Override
	public BaseOutput ContactListCreate(ContactTemplateIn ctIn) {

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		if (CollectionUtils.isNotEmpty(ctIn.getField_list())) {

			if (ctIn.getField_list().get(0).getSelected().equals("1")) {
				ContactTemplate param = new ContactTemplate();
				param.setContactName(ctIn.getContact_name());
				param.setContactDescript(ctIn.getContact_descript());
				param.setContactTitle(ctIn.getContact_title());
			

				int insert_count = contactTemplateDao.insert(param);

				result.setTotal(insert_count);

				List<ContactTemplate> reList = contactTemplateDao.selectList(param);

				if (CollectionUtils.isNotEmpty(reList)) {

					Map<String, Object> map_list = new HashMap<String, Object>();
					for (ContactTemplate rsTemplate : reList) {
						map_list.put("id", rsTemplate.getId());
						map_list.put("createtime", DateUtil.getStringFromDate(rsTemplate.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
						result.getData().add(map_list);
					}
				}

			}
		} else {
			result.setTotal(0);
		}

		return result;
	}
	
	@Override
	public BaseOutput updateContextTempById(Integer id)
	{
		ContactTemplate param = new ContactTemplate();
		param.setId(id);
		param.setUpdateTime(new Date());
		param.setStatus(ApiConstant.TAG_TYPE_CONTACT);
	    int update_count = contactTemplateDao.updateById(param);
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		
		Map<String, Object> map_r = new HashMap<String, Object>();
		map_r.put("id", param.getId());
		map_r.put("updatetime", DateUtil.getStringFromDate(param.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
		
		result.setTotal(update_count);
		result.getData().add(map_r);
		
		return result;
	}

}
