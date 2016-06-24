package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.FileTemplateDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(), ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO, null);
        BufferedInputStream bis = null;
        ZipOutputStream zos = null;
        try {
            File zipFile = new File(ApiConstant.DOWNLOAD_BASE_DIR + System.currentTimeMillis() + "template.zip");

            FileInputStream fis = null;
            FileOutputStream fos = null;

            String[] idList = templateIdList.split(",");
            File file = new File(ApiConstant.DOWNLOAD_TEMPLATE_FILE_DIR);
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(new BufferedOutputStream(fos));
            byte[] bufs = new byte[1024 * 10];
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] templateFiles = file.listFiles();
                    for (String id : idList) {
                        ZipEntry zipEntry = new ZipEntry(templateFiles[Integer.parseInt(id)].getName());
                        zos.putNextEntry(zipEntry);

                        fis = new FileInputStream(templateFiles[Integer.parseInt(id)]);
                        bis = new BufferedInputStream(fis, 1024*10);
                        int read = 0;
                        while((read=bis.read(bufs, 0, 1024*10)) != -1){
                            zos.write(bufs,0,read);
                        }
                    }
                }
            } else {
                baseOutput.setMsg("模板文件目录不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(null != bis) bis.close();
                if(null != zos) zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baseOutput;
    }
}
