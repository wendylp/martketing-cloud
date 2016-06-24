package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.FileTemplateDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by Yunfeng on 2016-6-24.
 */
@Service
public class FileTemplateDownloadServiceImpl implements FileTemplateDownloadService {

//   String filePath = this.getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "");
//   File file = new File(filePath+"/file/"+fileName);
//   Response.ResponseBuilder response = Response.ok((Object) file);
//   response.header("Content-Disposition", "attachment; filename=\""+fileName+"\"");
//   return response.build();

    @Override
    public BaseOutput downloadFileTemplate(String templateIdList) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(),ApiConstant.INT_ZERO,null);
        File file = new File(ApiConstant.DOWNLOAD_TEMPLATE_FILE_DIR);
        if(file.exists()){
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for(File templateFile : files){
                    baseOutput.getData().add(templateFile.toString());
                }
            }
        }else{
            baseOutput.setMsg("模板文件目录不存在");
        }
        return baseOutput;
    }
}
