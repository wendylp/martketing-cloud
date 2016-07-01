package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.FileTemplateDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.DownloadFileName;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Yunfeng on 2016-6-24.
 */
@Service
public class FileTemplateDownloadServiceImpl implements FileTemplateDownloadService {

    @Override
    public Object downloadFileTemplate(String templateIdList) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(), ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO, null);
        if(templateIdList.length() <= 0){
            baseOutput.setMsg("参数不合法");
            return baseOutput;
        }
        File[] templateFiles = null;
        String generateFileName = ApiConstant.DOWNLOAD_BASE_DIR + System.currentTimeMillis() + "template.zip";  //正式文件
//        String generateFileName = System.currentTimeMillis() + "template.zip";   //测试文件
        String command = "tar -zcvPf" + generateFileName + " ";
        File file = new File(ApiConstant.DOWNLOAD_TEMPLATE_FILE_DIR);
        templateFiles = getTemplateFiles(baseOutput, templateFiles, file);
        String[] idList = null;
        if(templateIdList.contains(",")){
            idList = templateIdList.split(",");
            for (String id : idList) {
                //压缩文件有多个
                String templateFileName = templateFiles[Integer.parseInt(id)].getAbsoluteFile().toString() + "";
                command += templateFileName;
            }
        }else{
            //压缩文件有1个
            String templateFileName = templateFiles[Integer.parseInt(templateIdList)].getAbsoluteFile().toString() + "";
            command += templateFileName;
        }
        this.executeCommand(command);
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        DownloadFileName downloadFileName = new DownloadFileName();
        downloadFileName.setDownloadFileName(generateFileName);
        baseOutput.getData().add(downloadFileName);
        return baseOutput;
    }

    private File[] getTemplateFiles(BaseOutput baseOutput, File[] templateFiles, File file) {
        if (file.exists() && file.isDirectory()) {
            templateFiles = file.listFiles();
        } else {
            baseOutput.setMsg("模板文件目录不存在");
        }
        return templateFiles;
    }

    private void executeCommand(String command) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
