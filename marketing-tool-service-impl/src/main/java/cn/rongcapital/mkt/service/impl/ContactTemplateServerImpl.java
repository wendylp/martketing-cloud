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

import com.mysql.fabric.xmlrpc.base.Data;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactTemplateServer;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactTemplateIn;
import cn.rongcapital.mkt.vo.in.Field_list;

@Service
public class ContactTemplateServerImpl implements ContactTemplateServer {

	@Autowired
	ContactTemplateDao contactTemplateDao;

	@Override
	public BaseOutput ContactListCreate(ContactTemplateIn ctIn) {

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		// 计数器
		int chang_count = 0;
		// 新增
		if (ctIn.getContact_id() == 0) {
			long cont_id = new Date().getTime();
			if (CollectionUtils.isNotEmpty(ctIn.getField_list())) {

				for (Field_list field_list : ctIn.getField_list()) {
					if (field_list.getSelected().equals("1")) {
						ContactTemplate param = new ContactTemplate();
						param.setContactName(ctIn.getContact_name());
						param.setContactDescript(ctIn.getContact_descript());
						param.setContactTitle(ctIn.getContact_title());

						param.setContactId(cont_id);
						param.setFieldName(field_list.getField_name());
						param.setFieldCode(field_list.getField_code());
						param.setSelected(field_list.getSelected());
						param.setRequired(field_list.getRequired());
						param.setIschecked(field_list.getIschecked());
						param.setPageViews(0);

						// 插入
						contactTemplateDao.insert(param);
						chang_count++;
					}
				}
				        // 影响行数
						result.setTotal(chang_count);
						Map<String, Object> map_list = new HashMap<String, Object>();
						map_list.put("contact_id", cont_id);
						map_list.put("createtime", DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
						result.getData().add(map_list);

			} else {
				result.setTotal(0);
			}

		}// 修改 
		else{
				if (CollectionUtils.isNotEmpty(ctIn.getField_list())) {
					// 先删除
					ContactTemplate parm_up = new ContactTemplate();
					parm_up.setContactId(ctIn.getContact_id());
					contactTemplateDao.deleteByCId(parm_up);
//					List<ContactTemplate> piew_list =contactTemplateDao.selectList(parm_up) ;
//					//浏览次数
//					int page_view_count = piew_list.get(0).getPageSize();

					// 在新增
					for (Field_list field_list : ctIn.getField_list()) {
						if (field_list.getSelected().equals("1")) {
							ContactTemplate param = new ContactTemplate();
							param.setContactName(ctIn.getContact_name());
							param.setContactDescript(ctIn.getContact_descript());
							param.setContactTitle(ctIn.getContact_title());

							param.setContactId(ctIn.getContact_id());
							param.setFieldName(field_list.getField_name());
							param.setFieldCode(field_list.getField_code());
							param.setSelected(field_list.getSelected());
							param.setRequired(field_list.getRequired());
							param.setIschecked(field_list.getIschecked());
							param.setPageViews(0);
							param.setUpdateTime(new Date());
							
							// 插入
							contactTemplateDao.insert(param);
							chang_count++;
						}
					}
					        // 影响行数
							result.setTotal(chang_count);
							Map<String, Object> map_list = new HashMap<String, Object>();
							map_list.put("contact_id", ctIn.getContact_id());
							map_list.put("updatetime", DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
							result.getData().add(map_list);
				}
			}

		return result;
	}

	@Override
	public BaseOutput updateContextTempById(Long id) {
		ContactTemplate param = new ContactTemplate();
		param.setContactId(id);
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
