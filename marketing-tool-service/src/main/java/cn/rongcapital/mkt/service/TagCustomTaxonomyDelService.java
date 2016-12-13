package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomyDelIn;

public interface TagCustomTaxonomyDelService {
    public BaseOutput tagCustomTaxonomyDel(TagCustomTaxonomyDelIn body,SecurityContext securityContext);
}
