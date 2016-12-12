package cn.rongcapital.mkt.material.coupon.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.material.coupon.service.CouponFileUploadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.UploadFileOut;

import com.csvreader.CsvWriter;

@Service
public class CouponFileUploadServiceImpl implements CouponFileUploadService {

    public final static String UPLOADED_FILE_PATH = "/rc/data/uploadFiles/code/";
    public final static String SLASH = "/";
    
    @Override
    public BaseOutput uploadFileBatch(MultipartFormDataInput fileInput,String userId) {
        
        Map<String, List<InputPart>> uploadForm = fileInput.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file_input");
        BaseOutput baseOutput = new BaseOutput();
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        
        CsvWriter csvWriter = null;
        UploadFileOut out = new UploadFileOut();
        
        for (InputPart inputPart : inputParts){
            String fileName = getFileName(inputPart.getHeaders());
            out.setFile_name(fileName);
            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
                baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                baseOutput.setMsg("上传的文件不是预定格式");
                return baseOutput;
            }
            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                InputStream is = new ByteArrayInputStream(bytes);
                Workbook workbook = WorkbookFactory.create(is);
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.rowIterator();
                Long num = 0L;
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Integer rowIndex = row.getRowNum();
                    if (rowIndex == 0) {
                        continue;
                    }
                    Iterator<Cell> dataCellIterator = row.cellIterator();
                    while (dataCellIterator.hasNext()) {
                        Cell dataColumnCell = dataCellIterator.next();
                        num ++;
//                        int cellType = dataColumnCell.getCellType();
//                        cellType == 0  double
//                        cellType == 1  String
                    }
                }
               out.setRecord_count(num);
                //上传文件到服务器
               String fileUrl = UPLOADED_FILE_PATH + userId + SLASH + fileName;
               out.setFile_path(fileUrl);
               String dirUrl = UPLOADED_FILE_PATH + userId;
               writeFile(bytes,fileUrl, dirUrl);
            } catch (Exception e) {
                e.printStackTrace();
                baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                baseOutput.setMsg("系统异常");
                return baseOutput;
            }finally{
                if (csvWriter != null) {
                    csvWriter.close();
                }
            }
        }
        List<Object> objList = new ArrayList<Object>();
        objList.add(out);
        baseOutput.setData(objList);
        return baseOutput;
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
    
    private void writeFile(byte[] content, String fileUrl, String dirUrl) throws IOException{
        File file = new File(fileUrl);
        File dirfile = new File(dirUrl);
        if(!file.exists()){
            boolean success = dirfile.mkdirs(); 
            if(success){
                file.createNewFile();
            }
        }else{
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
    }
}
