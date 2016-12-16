/*************************************************
 * @功能简述: 文件批量上传、下载、删除
 * @项目名称: marketing cloud
 * @see:
 * @author: guozhenchao
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.file.service.impl;

import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MultivaluedMap;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.stereotype.Service;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.file.FileService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public BaseOutput uploadFileBatch(MultipartFormDataInput fileInput, String userId) {
        Map<String, List<InputPart>> uploadForm = fileInput.getFormDataMap();
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), 0, null);
        List<InputPart> inputParts = uploadForm.get("file_input");
        for(InputPart inputPart : inputParts){
            String fileName = getFileName(inputPart.getHeaders());
            System.out.println(fileName);
        }
        
        return null;
    }

    /**
     * 获取文件名称
     * @param header
     * @return
     */
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
