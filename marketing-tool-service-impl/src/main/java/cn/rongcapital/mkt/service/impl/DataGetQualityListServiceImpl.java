package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.DatareportColumnsDao;
import cn.rongcapital.mkt.dao.ImportDataHistoryDao;
import cn.rongcapital.mkt.dao.ImportDataModifyLogDao;
import cn.rongcapital.mkt.po.DatareportColumns;
import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.po.ImportDataModifyLog;
import cn.rongcapital.mkt.service.DataGetQualityListService;
import cn.rongcapital.mkt.vo.out.DataGetQualityListOut;

@Service
public class DataGetQualityListServiceImpl implements DataGetQualityListService {

    @Autowired
    private ImportDataHistoryDao importDataHistoryDao;

    @Autowired
    private ImportDataModifyLogDao importDataModifyLogDao;

    @Autowired
    private DatareportColumnsDao datareportColumnsDao;

    @Override
    public Object getQualityList(String method, String userToken, Integer index, Integer size, String ver) {

        DataGetQualityListOut result = new DataGetQualityListOut(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        ImportDataHistory paramImportDataHistory = new ImportDataHistory(index, size);

        List<ImportDataHistory> importDataHistoryList = importDataHistoryDao.selectList(paramImportDataHistory);
        int totalCount = importDataHistoryDao.selectListCount(null);
        List<DatareportColumns> datareportColumnList = datareportColumnsDao.selectListByFieldOrder();
        if (datareportColumnList != null && !datareportColumnList.isEmpty()) {
            for (DatareportColumns datareportColumns : datareportColumnList) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put(datareportColumns.getFieldCode(), datareportColumns.getFieldName());
                result.getColNames().add(map);
            }
        }

        if (importDataHistoryList != null && !importDataHistoryList.isEmpty()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (ImportDataHistory importDataHistory : importDataHistoryList) {
                Map<String, Object> dataMap = new LinkedHashMap<>();
                List<Map<String, Object>> logList = new ArrayList<>();
                dataMap.put("data_id", importDataHistory.getId());
                dataMap.put("start_time", simpleDateFormat.format(importDataHistory.getImportStartTime()));
                dataMap.put("end_time", simpleDateFormat.format(importDataHistory.getImportEndTime()));
                dataMap.put("data_source", importDataHistory.getSource());
                dataMap.put("legal_data_rows_count", importDataHistory.getLegalRows());
                dataMap.put("ilegal_data_rows_count", importDataHistory.getIllegalRows());
                dataMap.put("source_file_name", importDataHistory.getSourceFilename());

                ImportDataModifyLog paramImportDataModifyLog = new ImportDataModifyLog();
                paramImportDataModifyLog.setImportDataId(importDataHistory.getId());
                List<ImportDataModifyLog> importDataModifyLogList =
                                importDataModifyLogDao.selectList(paramImportDataModifyLog);
                if (importDataModifyLogList != null && !importDataModifyLogList.isEmpty()) {
                    for (ImportDataModifyLog importDataModifyLog : importDataModifyLogList) {
                        Map<String, Object> contentMap = new HashMap<>();
                        contentMap.put("modify_file_name", importDataModifyLog.getModifyFilename());
                        contentMap.put("handle_time", simpleDateFormat.format(importDataModifyLog.getHandleTime()));
                        logList.add(contentMap);
                    }
                    dataMap.put("modify_log", logList);
                } else {
                    dataMap.put("modify_log", null);
                }

                result.getData().add(dataMap);
            }
        }

        result.setTotalCount(totalCount);
        result.setTotal(result.getData().size());

        return Response.ok().entity(result).build();
    }

}
