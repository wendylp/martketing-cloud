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
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Yunfeng on 2016-6-24.
 */
@Service
public class FileTemplateDownloadServiceImpl implements FileTemplateDownloadService {

    private Logger logger = LoggerFactory.getLogger(getClass());

//    sudo tar -zcvPf /rc/downloads/1467362267553template.tar.zip /rc/templeteFiles/TYPE2_002_客户标签.csv /rc/templeteFiles/TYPE3_003_埋点统计.csv /rc/templeteFiles/TYPE4_004_会员卡记录.csv
//    Todo:1修改文件压缩的方式，直接将文件压缩为zip文件。
    @Override
    public Object downloadFileTemplate(String templateIdList) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(), ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO, null);
        if(templateIdList.length() <= 0){
            baseOutput.setMsg("参数不合法");
            return baseOutput;
        }
        logger.info("begin downloadFile");
        File[] templateFiles = null;
        String generateFileSimpleName = System.currentTimeMillis() + "template.zip ";
        String generateFileName = ApiConstant.DOWNLOAD_BASE_DIR + generateFileSimpleName;  //正式文件
        String command = "tar -zcvPf "+ generateFileName;
        File file = new File(ApiConstant.DOWNLOAD_TEMPLATE_FILE_DIR);
        templateFiles = getTemplateFiles(baseOutput, templateFiles, file);
        String[] idList = null;
        File[] files = new File[7];
        if(templateIdList.contains(",")){
            Integer fileIndex = 0;
            idList = templateIdList.split(",");
            for (String id : idList) {
                //压缩文件有多个
                String templateFileName = templateFiles[Integer.parseInt(id)].getAbsoluteFile().toString() + " ";
                command += templateFileName;

                //构造文件数组
                files[fileIndex] = templateFiles[Integer.parseInt(id)];
                fileIndex++;
            }
        }else{
            //压缩文件有1个
            String templateFileName = templateFiles[Integer.parseInt(templateIdList)].getAbsoluteFile().toString() + "";
            command += templateFileName;
        }
//        logger.info("begin to execute command");
//        this.executeCommand(command);

        zipTemplateFiles(files,generateFileName);
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(generateFileName);
        DownloadFileName downloadFileName = new DownloadFileName();
        downloadFileName.setDownloadFileName(generateFileSimpleName);
        baseOutput.getData().add(downloadFileName);
        return baseOutput;
    }

    private void zipTemplateFiles(File[] files, String generateFileName) {
        try {
            logger.info("begin to execute zip action.");
            File downloadFile = new File(generateFileName);
            if(!downloadFile.exists()) downloadFile.createNewFile();
            OutputStream os = new BufferedOutputStream(new FileOutputStream(downloadFile));
            ZipOutputStream zos = new ZipOutputStream(os);
            byte[] buf = new byte[8192];
            int len;
            for(int i = 0; i<files.length; i++){
                logger.info("enter loop to zip file");
                File file = files[i];
                if(!file.isFile()) continue;
                logger.info("begin to read :" + files[i]);
                ZipEntry ze = new ZipEntry(file.getName());
                zos.putNextEntry(ze);
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                while((len = bis.read(buf)) > 0 ){
                    zos.write(buf,0,len);
                }
                zos.closeEntry();
                logger.info("read " + files[i] + "close");
            }
            zos.closeEntry();
            zos.close();
        } catch (FileNotFoundException e) {
            logger.debug("download exception:" + e.getMessage());
        } catch (IOException e) {
            logger.debug("download exception" + e.getMessage());
        }
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
