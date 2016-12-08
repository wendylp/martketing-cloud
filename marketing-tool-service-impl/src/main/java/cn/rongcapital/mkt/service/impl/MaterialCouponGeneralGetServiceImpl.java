/*************************************************
 * @功能简述: MaterialCouponGeneralGetService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.MaterialCouponDao;
import cn.rongcapital.mkt.service.MaterialCouponGeneralGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class MaterialCouponGeneralGetServiceImpl implements MaterialCouponGeneralGetService {

    @Autowired
    private MaterialCouponDao materialCouponDao;

    @Override
    public BaseOutput getMaterialCouponGeneral(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

 

}
