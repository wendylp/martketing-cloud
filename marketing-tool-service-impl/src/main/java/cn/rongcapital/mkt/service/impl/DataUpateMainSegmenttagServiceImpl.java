package cn.rongcapital.mkt.service.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.service.DataUpateMainSegmenttagService;

public class DataUpateMainSegmenttagServiceImpl implements DataUpateMainSegmenttagService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean updateMainSegmenttag(String method, String userToken, Integer index, Integer size, String ver,
                    String tagName, Integer contactId) {
        
        
        
        return false;
    }

}
