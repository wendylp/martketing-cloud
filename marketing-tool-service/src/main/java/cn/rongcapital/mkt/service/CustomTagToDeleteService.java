package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagToDeleteIn;

/**
 * Created by hiro on 17/2/8.
 */
public interface CustomTagToDeleteService {

    BaseOutput deleteCustomTag(CustomTagToDeleteIn customTagToDeleteIn);

}
