package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ImportDataHistoryDao;
import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.service.DataGetQualityListService;
import cn.rongcapital.mkt.vo.BaseOutput;

public class DataGetQualityListServiceImpl implements DataGetQualityListService {

    @Autowired
    private ImportDataHistoryDao importDataHistoryDao;

    @Override
    public Object getQualityList(String method, String userToken, Integer index, Integer size,
                    String ver) {

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        ImportDataHistory paramImportDataHistory = new ImportDataHistory();
        paramImportDataHistory.setStartIndex(index);
        paramImportDataHistory.setPageSize(size);

        List<ImportDataHistory> importDataHistoryList =
                        importDataHistoryDao.selectList(paramImportDataHistory);

        if (importDataHistoryList != null && !importDataHistoryList.isEmpty()) {
            for (ImportDataHistory importDataHistory : importDataHistoryList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", importDataHistory.getId());
                map.put("start_time", importDataHistory.getImportStartTime());
                map.put("end_time", importDataHistory.getImportEndTime());
                map.put("data_source", importDataHistory.getSource());
                map.put("legal_data_rows_count", importDataHistory.getLegalRows());
                map.put("ilegal_data_rows_count", importDataHistory.getIllegalRows());
                
                result.getData().add(map);
            }
        }

        result.setTotal(result.getData().size());

        return Response.ok().entity(result).build();
    }

}
