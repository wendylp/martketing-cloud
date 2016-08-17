package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactTemplateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactTempDIn;

@Service
public class ContactTemplateServiceImpl implements ContactTemplateService {

	@Autowired
	ContactTemplateDao contactTemplateDao;

	@Override
	public BaseOutput copyContactTemplate(ContactTempDIn body, SecurityContext securityContext) {
		ContactTemplate contactTemplate = new ContactTemplate();
		contactTemplate.setContactId(body.getContact_id());
		List<ContactTemplate> contactTemplates = contactTemplateDao.selectList(contactTemplate);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		if (CollectionUtils.isNotEmpty(contactTemplates) && contactTemplates.size() > 0) {
			for (ContactTemplate w : contactTemplates) {
				ContactTemplate copyContactTemplate = new ContactTemplate();
				copyContactTemplate.setContactName("[幅本]" + w.getContactName());
				copyContactTemplate.setContactTitle(w.getContactTitle());
				copyContactTemplate.setContactDescript(w.getContactDescript());
				copyContactTemplate.setFieldName(w.getFieldName());
				copyContactTemplate.setFieldCode(w.getFieldCode());
				copyContactTemplate.setSelected(w.getSelected());
				copyContactTemplate.setQrcodeUrl(w.getQrcodeUrl());
				copyContactTemplate.setPageViews(w.getPageViews());
				copyContactTemplate.setKeyList(w.getKeyList());
				contactTemplateDao.insert(copyContactTemplate);
				List<ContactTemplate> copyContactTemplates = contactTemplateDao.selectList(copyContactTemplate);
				if (CollectionUtils.isNotEmpty(copyContactTemplates) && copyContactTemplates.size() > 0) {
					Map<String, Object> map = new HashMap<>();
					map.put("id", copyContactTemplates.get(0).getId());
					map.put("updatetime", DateUtil.getStringFromDate(copyContactTemplates.get(0).getUpdateTime(),
							"yyyy-MM-dd HH:mm:ss"));
					result.getData().add(map);
				}
			}
		}
		return result;
	}
}
