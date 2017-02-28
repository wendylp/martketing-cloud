package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagCategoryDeleteIn;

/**
 * Created by hiro on 17/2/8.
 */
public interface CustomTagCategoryDeleteService {

    BaseOutput deleteCustomTagCategory(CustomTagCategoryDeleteIn customTagCategoryDeleteIn);

}
