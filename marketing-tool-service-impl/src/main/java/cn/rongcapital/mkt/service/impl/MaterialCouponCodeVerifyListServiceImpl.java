/*************************************************
 * @功能简述: MaterialCouponCodeVerifyListService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.MaterialCouponCodeDao;
import cn.rongcapital.mkt.service.MaterialCouponCodeVerifyListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class MaterialCouponCodeVerifyListServiceImpl implements MaterialCouponCodeVerifyListService {

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    @Override
    public BaseOutput listMaterialCouponCodeVerfy(Long id, String blurSearch, String receiveStatus,
                    String verifyStatus, String expireStatus, Integer index, Integer size) {
        // TODO Auto-generated method stub
        return null;
    }

}
