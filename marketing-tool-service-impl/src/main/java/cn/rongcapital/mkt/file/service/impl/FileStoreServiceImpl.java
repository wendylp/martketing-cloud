/*************************************************
 * @功能简述: 文件上传、读取、删除
 * @项目名称: marketing cloud
 * @see:
 * @author: guozhenchao
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.file.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.file.FileStoreService;

@Service
public class FileStoreServiceImpl implements FileStoreService{

    @Value("${uploaded.file.path}")
    private String filePath;
    
    public final static String SLASH = File.separator;
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public Boolean uploadFile(byte[] fileByte, String fileName, String owner) {

        Boolean flag = true;
        String fileUrl = ApiConstant.UPLOADED_FILE_PATH + owner + SLASH + fileName;
        String dirUrl = ApiConstant.UPLOADED_FILE_PATH + owner;
        try {
            writeFile(fileByte, fileUrl, dirUrl);
        } catch (IOException e) {
            flag = false;
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return flag;
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

    @Override
    public byte[] getFile(String fileName, String owner) {
        String filesUrl = filePath + owner + SLASH + fileName;
        File file = new File(filesUrl);
        byte[] bytes = null;
        if(file.exists()){
            try {
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                bytes = IOUtils.toByteArray(in);
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }else{
            logger.error("文件不存在");
        }
        return bytes;
    }

    @Override
    public Boolean fileDelete(String fileName, String owner) {
        String filesUrl = filePath + owner + SLASH + fileName;
        File file = new File(filesUrl);
        Boolean flag = false;
        if(file.exists()){
            flag = file.delete();
        }else{
            logger.error("文件不存在");
        }
        return flag;
    }
}
