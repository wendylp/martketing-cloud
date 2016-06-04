package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ImportDataHistoryDao;
import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.service.MigrationFileUploadUrlService;
import cn.rongcapital.mkt.vo.BaseInput;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-5-30.
 */
@Service
public class MigrationFileUploadUrlServiceImpl implements MigrationFileUploadUrlService {

    @Autowired
    private ImportDataHistoryDao importDataHistoryDao;

    @Override
    public Object getMigrationFileUploadUrl(BaseInput input) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),1,null);
        String fileUnique = RandomStringUtils.random(5,true,true) + System.currentTimeMillis();
        insertImportFileUnique(fileUnique);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("file_unique",fileUnique);
        map.put("file_url", ApiConstant.FILE_UPLOAD_URL);
        baseOutput.getData().add(map);
        return Response.ok().entity(baseOutput).build();
    }

    private void insertImportFileUnique(String fileUnique) {
        ImportDataHistory importDataHistory = new ImportDataHistory();
        importDataHistory.setFileUnique(fileUnique);
        importDataHistory.setImportStartTime(new Date(System.currentTimeMillis()));
        importDataHistoryDao.insert(importDataHistory);
    }
}