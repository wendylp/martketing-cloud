package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.FileTemplateDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.DownloadFileName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by Yunfeng on 2016-6-24.
 */
@Service
public class FileTemplateDownloadServiceImpl implements FileTemplateDownloadService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object downloadFileTemplate(String templateIdList) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(), ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO, null);
        if(templateIdList.length() <= 0){
            baseOutput.setMsg("参数不合法");
            return baseOutput;
        }
        logger.info("begin downloadFile");
        File[] templateFiles = null;
        String generateFileSimpleName = System.currentTimeMillis() + "template.zip";
        String generateFileName = ApiConstant.DOWNLOAD_BASE_DIR + generateFileSimpleName;  //正式文件
//        String generateFileName = System.currentTimeMillis() + "template.zip";   //测试文件
        String command = "sh /rc/marketcloudsrv/zipTempelete.sh ";
        File file = new File(ApiConstant.DOWNLOAD_TEMPLATE_FILE_DIR);
        templateFiles = getTemplateFiles(baseOutput, templateFiles, file);
        String[] idList = null;
        if(templateIdList.contains(",")){
            idList = templateIdList.split(",");
            command += " '";
            for (String id : idList) {
                //压缩文件有多个
                String templateFileName = templateFiles[Integer.parseInt(id)].getAbsoluteFile().toString() + " ";
                command += templateFileName;
            }
            command += "' ";
            command += "' ";
            command += generateFileSimpleName + " ";
            command += "' ";
            command += "' ";
            for(String id : idList){
                String templateFileName = templateFiles[Integer.parseInt(id)].getName() + " ";
                command += templateFileName;
            }
            command += "' ";
        }else{
            //压缩文件有1个
            command += "' ";
            String templateFileName = templateFiles[Integer.parseInt(templateIdList)].getAbsoluteFile().toString() + "";
            command += templateFileName;
            command += "' ";
            command += "' ";
            command += generateFileSimpleName + " ";
            command += "' ";
            command += "' ";
            templateFileName = templateFiles[Integer.parseInt(templateIdList)].getName() + "";
            command += templateFileName;
            command += "' ";
        }
        logger.info("begin to execute command");

        //测试
        command = "sh /rc/marketcloudsrv/zipTempelete.sh  '/rc/templeteFiles/TYPE2_002_客户标签.csv /rc/templeteFiles/TYPE3_003_埋点统计.csv /rc/templeteFiles/TYPE4_004_会员卡记录.csv ' ' 1467365236119template.zip ' ' TYPE2_002_客户标签.csv TYPE3_003_埋点统计.csv TYPE4_004_会员卡记录.csv '";

        this.executeCommand(command);
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(command);
        DownloadFileName downloadFileName = new DownloadFileName();
        downloadFileName.setDownloadFileName(generateFileSimpleName);
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
            logger.info("zipCommand: " + command);
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
    }
}
