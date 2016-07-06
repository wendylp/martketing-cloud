package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.ImportDataHistoryDao;
import cn.rongcapital.mkt.dao.ImportDataModifyLogDao;
import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.po.ImportDataModifyLog;
import cn.rongcapital.mkt.service.UploadFileService;
import cn.rongcapital.mkt.service.impl.vo.UploadFileProcessVO;
import cn.rongcapital.mkt.service.impl.vo.UploadFileVO;
import cn.rongcapital.mkt.vo.BaseOutput;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
    private ParseUploadFileImpl parseUploadFile;

    @Override
    public Object uploadFile(String fileUnique, MultipartFormDataInput fileInput) {
        UploadFileVO uploadFileVO = processEachUploadFile(fileUnique, fileInput, false);
        UploadFileProcessVO processVO = uploadFileVO.getProcessVO();

        if(processVO.getTotalRows() == -1){
            return new BaseOutput(ApiErrorCode.BIZ_ERROR.getCode(),"文件格式非UTF-8编码",ApiConstant.INT_ZERO,null);
        }

        ImportDataHistory importDataHistory = uploadFileVO.getImportDataHistory();
        importDataHistory.setTotalRows(processVO.getTotalRows());
        importDataHistory.setLegalRows(processVO.getLegalRows());
        importDataHistory.setIllegalRows(processVO.getTotalRows().intValue() - processVO.getLegalRows().intValue());
        importDataHistory.setNoRecognizeProperty(processVO.getUnrecognizeFields());
        importDataHistory.setFileType(Integer.valueOf(processVO.getFileType()));
        importDataHistory.setSourceFilename(uploadFileVO.getFileName());
        importDataHistoryDao.updateById(importDataHistory);

        BaseOutput baseOutput = uploadFileVO.getOutput();
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

        ImportDataModifyLog importDataModifyLog = new ImportDataModifyLog();
        importDataModifyLog.setImportDataId(importDataHistory.getId());
        importDataModifyLog.setHandleTime(new Date());
        importDataModifyLog.setTotalRows(importDataHistory.getTotalRows());
        importDataModifyLog.setModifyRows(processVO.getLegalRows());
        importDataModifyLog.setIllegalRows(importDataHistory.getIllegalRows());
        importDataModifyLog.setModifyFilename(uploadFileVO.getFileName());
        importDataModifyLog.setSuccess(Byte.valueOf(StatusEnum.ACTIVE.getStatusCode().toString()));
        importDataModifyLogDao.insert(importDataModifyLog);

        BaseOutput baseOut = uploadFileVO.getOutput();
        baseOut.getData().add(processVO);
        return Response.ok().entity(baseOut).build();
    }


    private UploadFileVO processEachUploadFile(String fileUnique, MultipartFormDataInput fileInput, boolean isRepair) {
        UploadFileVO uploadFileVO = new UploadFileVO();
        BaseOutput baseOutput = new BaseOutput();
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());

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
                InputStream inputStream = inputPart.getBody(InputStream.class,null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                processVO = parseUploadFile.parseAndInsertUploadFileByType(fileUnique,fileName,bytes);
                if (processVO == null) {
                    baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                    baseOutput.setMsg("上传的文件名称不是预定格式");
                    return uploadFileVO;
                }
                writeFile(bytes, directory + fileName);
                uploadFileVO.setFileName(fileName);
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

    private void writeFile(byte[] content, String fileName) {
        FileOutputStream fop = null;
        try {
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            fop = new FileOutputStream(file);
            fop.write(content);
            fop.flush();
        } catch (Exception e) {

        } finally {
            if (fop != null) {
                try {
                    fop.close();
                } catch (Exception e) {

                }
            }
        }
    }
}
