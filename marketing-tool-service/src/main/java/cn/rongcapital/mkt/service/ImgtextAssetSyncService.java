package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ImgtextAssetSyncIn;

/**
 * Created by Yunfeng on 2016-6-21.
 */
public interface ImgtextAssetSyncService {
    BaseOutput syncImgtextAsset(ImgtextAssetSyncIn imgtextAssetSyncIn);
}
