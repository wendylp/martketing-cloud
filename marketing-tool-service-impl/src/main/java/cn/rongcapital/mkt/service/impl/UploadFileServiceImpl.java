package cn.rongcapital.mkt.service.impl;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.service.ParseUploadFile;
import cn.rongcapital.mkt.vo.out.UploadFileAccordTemplateOut;
import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ImportDataHistoryDao;
import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.service.UploadFileService;
import cn.rongcapital.mkt.vo.BaseOutput;

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
    private ParseUploadFile parseUploadFile;

    @Override
    public Object uploadFile(String source,String fileUnique,int fileType,MultipartFormDataInput fileInput, SecurityContext securityContext) {
        JSONObject obj = new JSONObject();
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(),ApiConstant.INT_ZERO,null);
        UploadFileAccordTemplateOut uploadFileAccordTemplateOut = null;
        if(!isFileUniqueValid(fileUnique)){
            baseOutput.setMsg("文件唯一性标识不合法。");
            return Response.ok().entity(baseOutput).build();
        }
        String fileName = "";
        Map<String,List<InputPart>> uploadForm = fileInput.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadFile");
        for(InputPart inputPart : inputParts){
            try {
                MultivaluedMap<String,String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                if(!fileName.endsWith(".csv")){
                    baseOutput.setMsg("上传的文件不是预定格式");
                    return Response.ok().entity(baseOutput).build();
                }
                InputStream inputStream = inputPart.getBody(InputStream.class,null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                uploadFileAccordTemplateOut = parseUploadFile.parseUploadFileByType(fileUnique,fileName,bytes);
                //Todo: 3.根据文件唯一标识，把数据条数，摘要，未识别属性放到数据库的importHistory表中的相应栏位中。
                Map<String,Object> updateMap = new HashMap<String,Object>();
                updateMap.put("total_rows",uploadFileAccordTemplateOut.getDataRows());
                updateMap.put("no_recognize_property",uploadFileAccordTemplateOut.getUnrecognizeFields());
                updateMap.put("file_type",uploadFileAccordTemplateOut.getFileType());
                updateMap.put("file_unique",fileUnique);
                importDataHistoryDao.updateByFileUnique(updateMap);
                //Todo: 4不确定文件是否还需要保存临时文件
                writeFile(bytes,directory + fileName);
//                writeFile(bytes, fileName);  //测试使用路径
                logger.info("文件上传完毕！");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //Todo: 4.将数据摘要，数据条数，未识别属性返回给前端。
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        baseOutput.getData().add(uploadFileAccordTemplateOut);
        return Response.ok().entity(baseOutput).build();
    }

    private boolean isFileUniqueValid(String fileUnique) {
        if(fileUnique == null) return false;
        ImportDataHistory importDataHistory = new ImportDataHistory();  //判断fileUnique的有效性
        importDataHistory.setFileUnique(fileUnique);
        return (importDataHistoryDao.selectListCount(importDataHistory) >0 ? true:false);

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

    private void writeFile(byte[] content, String fileName) throws IOException {
        File file = new File(fileName);
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
    }
}
