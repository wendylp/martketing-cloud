package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomyShowSetIn;

public interface TagCustomTaxonomyShowSetService {
    public BaseOutput tagCustomTaxonomyShowSet(TagCustomTaxonomyShowSetIn body,SecurityContext securityContext);
}
