package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.ImportTemplTypeEnum;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.service.DataGetMainCountService;
import cn.rongcapital.mkt.vo.out.DataGetMainCountOut;

@Service
public class DataGetMainCountServiceImpl implements DataGetMainCountService {

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private ImportTemplateDao importTemplateDao;

    @Override
    public Object getMainCount(String method, String userToken, String ver) {

        DataGetMainCountOut result = new DataGetMainCountOut(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        Map<String, Object> countRowsMap = dataPartyDao.selectMainCount();
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<ImportTemplate> importTemplateList = importTemplateDao.selectTemplTypePairs();
        for (Map.Entry<String, Object> entry : countRowsMap.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("count_rows", entry.getValue());
            String tagName = ImportTemplTypeEnum.getNameByCountName(entry.getKey());
            map.put("tag_name", tagName);
            for (ImportTemplate importTemplate : importTemplateList) {
                if (importTemplate.getTemplName().equals(tagName)) {
                    map.put("md_type", importTemplate.getTemplType());
                    break;
                }
            }

            resultList.add(map);

        }
        result.getData().addAll(resultList);
        result.setTotal(result.getData().size());

        return Response.ok().entity(result).build();
    }

}
