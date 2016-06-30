package cn.rongcapital.mkt.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.FileNameEnum;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.IllegalDataDao;
import cn.rongcapital.mkt.po.IllegalData;
import cn.rongcapital.mkt.service.DataDownloadQualityIllegalDataService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class DataDownloadQualityIllegalDataServiceImpl implements DataDownloadQualityIllegalDataService {

    @Autowired
    private IllegalDataDao illegalDataDao;

    @Override
    public BaseOutput downloadIllegalData(Long batchId) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);

        IllegalData paramIllegalData = new IllegalData();
        paramIllegalData.setBatchId(batchId);
        List<IllegalData> illegalDatas = illegalDataDao.selectList(paramIllegalData);
        List<String> columnNames = illegalDataDao.selectColumns();

        File file = FileUtil.generateFileforDownload(FileUtil.transferNameListtoMap(columnNames), illegalDatas,
                        FileNameEnum.ILLEGAL_DATA.getDetailName());
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("download_file_name", file.getName());
        result.getData().add(resultMap);

        return result;
    }

}
