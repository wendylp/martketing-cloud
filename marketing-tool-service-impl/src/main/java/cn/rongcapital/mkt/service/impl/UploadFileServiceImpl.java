package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ImportDataHistoryDao;
import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.service.UploadFileService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-2.
 */
@Service
public class UploadFileServiceImpl implements UploadFileService{

    private final String UPLOADED_FILE_PATH = ApiConstant.FILE_UPLOAD_URL;

    @Autowired
    private ImportDataHistoryDao importDataHistoryDao;

    @Override
    public Object uploadFile(String source,String fileUnique,int fileType,MultipartFormDataInput fileInput, SecurityContext securityContext) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(),ApiConstant.INT_ZERO,null);
        if( !isFileUniqueValid(fileUnique) ) return Response.ok().entity(baseOutput).build();

        String fileName = "";
        Map<String,List<InputPart>> uploadForm = fileInput.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");
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
                //Todo: 2.如果有效，把上传上来的文件解析，解析后得到的摘要，数据条数，未识别属性
                //Todo: 3.根据文件唯一标识，把数据条数，摘要，未识别属性放到数据库的importHistory表中的相应栏位中。
                writeFile(bytes,fileName);
                System.out.println("DONE");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //Todo: 4.将数据摘要，数据条数，未识别属性返回给前端。
        return Response.status(200).entity(baseOutput).build();
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