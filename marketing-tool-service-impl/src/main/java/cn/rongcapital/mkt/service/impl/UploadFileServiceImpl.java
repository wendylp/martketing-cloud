package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.IllegalDataHeadTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.IllegalDataDao;
import cn.rongcapital.mkt.dao.ImportDataHistoryDao;
import cn.rongcapital.mkt.dao.ImportDataModifyLogDao;
import cn.rongcapital.mkt.po.IllegalData;
import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.po.ImportDataModifyLog;
import cn.rongcapital.mkt.service.UploadFileService;
import cn.rongcapital.mkt.service.impl.vo.UploadFileProcessVO;
import cn.rongcapital.mkt.service.impl.vo.UploadFileVO;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.IllegalDataUploadModifyLogOut;
import cn.rongcapital.mkt.vo.out.IllegalDataUploadOut;
import cn.rongcapital.mkt.vo.out.UploadFileAccordTemplateOut;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-2.
 */
@Service
public class UploadFileServiceImpl implements UploadFileService{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String directory = "//rc//";

    @Autowired
    private ImportDataHistoryDao importDataHistoryDao;

    @Autowired
    private ImportDataModifyLogDao importDataModifyLogDao;

    @Autowired
    private IllegalDataDao illegalDataDao;

    @Autowired
    private ParseUploadFileImpl parseUploadFile;

    @Override
    public Object uploadFile(String fileUnique, MultipartFormDataInput fileInput) {
        UploadFileVO uploadFileVO = processEachUploadFile(fileUnique, fileInput, false);
        BaseOutput baseOutput = uploadFileVO.getOutput();
        if(baseOutput.getCode() != ApiErrorCode.SUCCESS.getCode()){
            return baseOutput;
        }

        UploadFileProcessVO processVO = uploadFileVO.getProcessVO();
        ImportDataHistory importDataHistory = uploadFileVO.getImportDataHistory();
        importDataHistory.setTotalRows(processVO.getTotalRows());
        importDataHistory.setLegalRows(processVO.getLegalRows());
        importDataHistory.setIllegalRows(processVO.getTotalRows().intValue() - processVO.getLegalRows().intValue());
        importDataHistory.setEmailRows(processVO.getEmailRows());
        importDataHistory.setMobileRows(processVO.getMobileRows());
        importDataHistory.setEmailRows(processVO.getEmailRows());
        importDataHistory.setDuplicateRows(processVO.getDuplicateRows());
        importDataHistory.setNoRecognizeProperty(processVO.getUnrecognizeFields());
        importDataHistory.setFileType(Integer.valueOf(processVO.getFileType()));
        importDataHistory.setSourceFilename(uploadFileVO.getFileName());
        importDataHistory.setDownloadFilename(uploadFileVO.getDownloadFileName());
        importDataHistoryDao.updateById(importDataHistory);

        saveIllegalData(processVO, importDataHistory.getId());


        UploadFileAccordTemplateOut out = new UploadFileAccordTemplateOut();
        out.setLegalRows(processVO.getLegalRows());
        out.setDataTopic(processVO.getDataTopic());
        out.setFileType(processVO.getFileType());
        out.setUnrecognizeFields(processVO.getUnrecognizeFields());
        baseOutput.getData().add(out);
        return Response.ok().entity(baseOutput).build();
    }

    @Override
    public Object uploadRepairFile(String fileUnique, MultipartFormDataInput fileInput) {
        // parse file, insert original
        UploadFileVO uploadFileVO = processEachUploadFile(fileUnique, fileInput, true);
        BaseOutput baseOutput = uploadFileVO.getOutput();
        if(baseOutput.getCode() != ApiErrorCode.SUCCESS.getCode()){
            return baseOutput;
        }

        UploadFileProcessVO processVO = uploadFileVO.getProcessVO();
        if(processVO.getTotalRows() == -1){
            return new BaseOutput(ApiErrorCode.BIZ_ERROR.getCode(),"文件格式非UTF-8编码",ApiConstant.INT_ZERO,null);
        }

        // update import history,insert import log
        ImportDataHistory importDataHistory = uploadFileVO.getImportDataHistory();
        int repairRows = processVO.getLegalRows().intValue();
        if (repairRows > 0) {
            if (importDataHistory.getIllegalRows() != null) {
                importDataHistory.setIllegalRows(importDataHistory.getIllegalRows().intValue() - repairRows);
            } else {
                importDataHistory.setIllegalRows(Integer.valueOf(0));
            }
            if (importDataHistory.getLegalRows() != null) {
                importDataHistory.setLegalRows(importDataHistory.getLegalRows() + repairRows);
            } else {
                importDataHistory.setLegalRows(repairRows);
            }
            importDataHistory.setNoRecognizeProperty(processVO.getUnrecognizeFields());
            importDataHistory.setFileType(Integer.valueOf(processVO.getFileType()));
            importDataHistoryDao.updateById(importDataHistory);
        }

        // update and insert illegal data
        illegalDataDao.updateStatusByBatchIdAndType(importDataHistory.getId(),
                importDataHistory.getFileType().toString(), StatusEnum.DELETED.getStatusCode());
        saveIllegalData(processVO, importDataHistory.getId());

        ImportDataModifyLog importDataModifyLog = new ImportDataModifyLog();
        importDataModifyLog.setImportDataId(importDataHistory.getId());
        importDataModifyLog.setHandleTime(new Date());
        importDataModifyLog.setTotalRows(importDataHistory.getTotalRows());
        importDataModifyLog.setModifyRows(processVO.getLegalRows());
        importDataModifyLog.setIllegalRows(importDataHistory.getIllegalRows());
        importDataModifyLog.setModifyFilename(uploadFileVO.getFileName());
        importDataModifyLog.setModifyDownloadFilename(uploadFileVO.getDownloadFileName());
        importDataModifyLog.setSuccess(Byte.valueOf(StatusEnum.ACTIVE.getStatusCode().toString()));
        importDataModifyLogDao.insert(importDataModifyLog);

        return Response.ok().entity(processOut(uploadFileVO, importDataHistory.getId())).build();
    }

    private IllegalDataUploadOut processOut(UploadFileVO uploadFileVO, Long importDataHistoryId) {
        UploadFileProcessVO processVO = uploadFileVO.getProcessVO();
        ImportDataModifyLog queryImportDataModifyLog = new ImportDataModifyLog();
        queryImportDataModifyLog.setImportDataId(importDataHistoryId);
        queryImportDataModifyLog.setPageSize(null);
        queryImportDataModifyLog.setStartIndex(null);
        queryImportDataModifyLog.setOrderField("handle_time");
        queryImportDataModifyLog.setOrderFieldType("desc");
        List<ImportDataModifyLog> allImportDataModifyLogList =
                importDataModifyLogDao.selectList(queryImportDataModifyLog);
        List<IllegalDataUploadModifyLogOut> modifyLogOutList = new ArrayList<>(allImportDataModifyLogList.size());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ImportDataModifyLog tempImportDataModifyLog : allImportDataModifyLogList) {
            IllegalDataUploadModifyLogOut modifyLogOut = new IllegalDataUploadModifyLogOut();
            modifyLogOut.setModifyFilename(tempImportDataModifyLog.getModifyFilename());
            modifyLogOut.setModifyDownloadFilename(tempImportDataModifyLog.getModifyDownloadFilename());
            modifyLogOut.setHandleTime(simpleDateFormat.format(tempImportDataModifyLog.getHandleTime()));
            modifyLogOutList.add(modifyLogOut);
        }

        BaseOutput baseOut = uploadFileVO.getOutput();
        baseOut.getData().add(processVO);

        IllegalDataUploadOut illegalDataUploadOut = new IllegalDataUploadOut();
        illegalDataUploadOut.setCode(baseOut.getCode());
        illegalDataUploadOut.setMsg(baseOut.getMsg());
        illegalDataUploadOut.setTotal(baseOut.getTotal());
        illegalDataUploadOut.getData().add(processVO);
        illegalDataUploadOut.setModifyLog(modifyLogOutList);

        return illegalDataUploadOut;
    }


    private UploadFileVO processEachUploadFile(String fileUnique, MultipartFormDataInput fileInput, boolean isRepair) {
        UploadFileVO uploadFileVO = new UploadFileVO();
        BaseOutput baseOutput = new BaseOutput();
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        uploadFileVO.setOutput(baseOutput);

        ImportDataHistory importDataHistory = queryFileUnique(fileUnique);
        uploadFileVO.setImportDataHistory(importDataHistory);
        if(importDataHistory == null){
            baseOutput.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
            baseOutput.setMsg("文件唯一性标识不合法。");
            return uploadFileVO;
        } else if (isRepair) {
            Integer totalRows = importDataHistory.getTotalRows();
            Integer legalRows = importDataHistory.getLegalRows();
            if (totalRows != null && legalRows != null && legalRows.intValue() == totalRows.intValue()) {
                baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                baseOutput.setMsg("没有需要修正的记录。");
                return uploadFileVO;
            }
        }
        UploadFileProcessVO processVO = null;
        Map<String,List<InputPart>> uploadForm = fileInput.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");
        for(InputPart inputPart : inputParts){
            try {
                String fileName = getFileName(inputPart.getHeaders());
                if(!fileName.endsWith(".csv")){
                    baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                    baseOutput.setMsg("上传的文件不是预定格式");
                    return uploadFileVO;
                }

                String[] typeAndBatchId = fileName.split("_");
                int fileType = 0;
                if (!isRepair) {
                    if (typeAndBatchId.length < 1) {
                        baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                        baseOutput.setMsg("上传的文件名不是预定格式");
                        return uploadFileVO;
                    }
                    fileType = Integer.parseInt(typeAndBatchId[0].substring(typeAndBatchId[0].length()-1));
                    if (fileType == 0) {
                        baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                        baseOutput.setMsg("上传的文件名不是预定格式");
                        return uploadFileVO;
                    }

                } else {
                    fileType = importDataHistory.getFileType();
                }

                String batchId = null;
                if (typeAndBatchId.length > 1) {
                    batchId = typeAndBatchId[1];
                }
                InputStream inputStream = inputPart.getBody(InputStream.class,null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                if (!isUTF8(bytes)) {
                    baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                    baseOutput.setMsg("上传的文件不是UTF-8编码");
                    return uploadFileVO;
                }
                processVO = parseUploadFile.parseAndInsertUploadFileByType(fileUnique,fileType, batchId, bytes);
                String downloadFileName = FileUtil.generateFileforDownload(bytes);
                uploadFileVO.setFileName(fileName);
                uploadFileVO.setDownloadFileName(downloadFileName);
            }catch (Exception e){
                logger.error("parse file failed", e);
                baseOutput.setCode(ApiErrorCode.SYSTEM_ERROR.getCode());
                baseOutput.setMsg(ApiErrorCode.SYSTEM_ERROR.getMsg());
            }
        }
        uploadFileVO.setOutput(baseOutput);
        uploadFileVO.setProcessVO(processVO);
        return uploadFileVO;
    }

    public boolean isUTF8(final byte[] dataBytes) {
        int expectedLength = 0;
        int testLength = 9;
        if (dataBytes.length <= testLength) {
            testLength =  dataBytes.length;
        }

        for (int i = 0; i < testLength; i++) {
            if ((dataBytes[i] & 0b10000000) == 0b00000000) {
                expectedLength = 1;
            } else if ((dataBytes[i] & 0b11100000) == 0b11000000) {
                expectedLength = 2;
            } else if ((dataBytes[i] & 0b11110000) == 0b11100000) {
                expectedLength = 3;
            } else if ((dataBytes[i] & 0b11111000) == 0b11110000) {
                expectedLength = 4;
            } else if ((dataBytes[i] & 0b11111100) == 0b11111000) {
                expectedLength = 5;
            } else if ((dataBytes[i] & 0b11111110) == 0b11111100) {
                expectedLength = 6;
            } else {
                return false;
            }
            while (--expectedLength > 0) {
                if (++i >= dataBytes.length) {
                    return false;
                }
                if ((dataBytes[i] & 0b11000000) != 0b10000000) {
                    return false;
                }
            }
        }
        return true;
    }

    private void saveIllegalData(UploadFileProcessVO processVO, Long importDataHistoryId) {
        List<String> illegalRawDataList = processVO.getIllegalRawData();
        if (illegalRawDataList.size() < 1) {
            return;
        }
        List<IllegalData> illegalDataList = new ArrayList<>(illegalRawDataList.size() + 1);
        for (String originData : illegalRawDataList) {
            IllegalData illegalData = new IllegalData();
            illegalData.setBatchId(importDataHistoryId);
            illegalData.setType(processVO.getFileType());
            illegalData.setOriginData(originData);
            illegalData.setHeadType(IllegalDataHeadTypeEnum.DATA.getCode());
            illegalDataList.add(illegalData);
        }
        IllegalData illegalData = new IllegalData();
        illegalData.setBatchId(importDataHistoryId);
        illegalData.setType(processVO.getFileType());
        illegalData.setOriginData(processVO.getFileHeader());
        illegalData.setHeadType(IllegalDataHeadTypeEnum.HEADER.getCode());
        illegalDataList.add(illegalData);
        illegalDataDao.batchInsert(illegalDataList);
    }

    private ImportDataHistory queryFileUnique(String fileUnique) {
        if(fileUnique == null) {
            return null;
        }
        ImportDataHistory importDataHistory = new ImportDataHistory();  //判断fileUnique的有效性
        importDataHistory.setFileUnique(fileUnique);
        List<ImportDataHistory> importDataHistoryList = importDataHistoryDao.selectList(importDataHistory);
        if (CollectionUtils.isEmpty(importDataHistoryList)) {
            return null;
        }
        return importDataHistoryList.get(0);
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for(String filename : contentDisposition){
            if((filename.trim().startsWith("filename"))){
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"","");
                return finalFileName;
            }
        }
        return "unknown";
    }


}
