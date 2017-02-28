package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagSaveToCategoryIn;

/**
 * Created by byf on 1/17/17.
 */
public interface CreateCustomTagToCategoryService {

    BaseOutput createCustomTagToCategory(CustomTagSaveToCategoryIn customTagSaveToCategoryIn);

}
