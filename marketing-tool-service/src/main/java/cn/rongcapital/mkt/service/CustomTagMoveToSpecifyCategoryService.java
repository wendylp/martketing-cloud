package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CtMoveToSpeCategoryIn;

/**
 * Created by hiro on 17/2/7.
 */
public interface CustomTagMoveToSpecifyCategoryService {
    BaseOutput moveCustomTagToSpecifyCategory(CtMoveToSpeCategoryIn ctMoveToSpeCategoryIn);
}
