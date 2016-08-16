package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactTemplateIn;

public interface ContactTemplateService {
	BaseOutput copyContactTemplate(ContactTemplateIn body, SecurityContext securityContext);

}
