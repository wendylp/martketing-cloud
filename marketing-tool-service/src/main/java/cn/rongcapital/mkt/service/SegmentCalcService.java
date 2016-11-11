package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.vo.in.SegmentCreUpdateIn;
import cn.rongcapital.mkt.vo.in.TagGroupsIn;

/**
 * 细分计算
 * @author zhouqi
 * 2016-11-09
 */
public interface SegmentCalcService {
    public void calcSegmentCover(SegmentCreUpdateIn segment);
    public void calcSegmentCoverByGroup(TagGroupsIn tagGroup) ;
}
