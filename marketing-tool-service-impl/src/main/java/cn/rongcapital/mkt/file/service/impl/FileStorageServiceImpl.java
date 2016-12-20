/*************************************************
 * @功能简述: 实现FileStorageService
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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import cn.rongcapital.mkt.file.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    @Value("${uploaded.file.path}")
    private String filePath;
    
    public final static String SLASH = File.separator;
    
    private final static Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);
    
    @Override
    public void save(byte[] fileByte, String fileName, String key) {

        if (fileByte == null) {
            logger.error("save the file, file is not exist");
        }
        if (StringUtils.isBlank(fileName)) {
            logger.error("save the file, fileName is null");
        }
        if (StringUtils.isBlank(key)) {
            logger.error("save the file, key is null");
        }

        String fileUrl = filePath + key + SLASH + fileName;
        String dirUrl = filePath + key;
        writeFile(fileByte, fileUrl, dirUrl);
    }
    
    private void writeFile(byte[] content, String fileUrl, String dirUrl) {
        File file = new File(fileUrl);
        File dirfile = new File(dirUrl);
        FileOutputStream fop = null;
        try {
            if (!file.exists()) {
                boolean success = dirfile.mkdirs();
                if (success) {
                    file.createNewFile();
                }
            } else {
                file.createNewFile();
            }
            fop = new FileOutputStream(file);
            fop.write(content);
            fop.flush();
        } catch (IOException e) {
            logger.error("parse the file failed, fileUrl: {}, error: {}", fileUrl, e.getMessage(), e);
        } finally {
            try {
                fop.close();
            } catch (IOException e) {
                logger.error("parse the file, IO close failed, fileUrl: {}, error: {}", fileUrl, e.getMessage(), e);
            }
        }
    }

    @Override
    public byte[] get(String fileName, String key) {

        if (StringUtils.isBlank(fileName)) {
            logger.error("get the file, fileName is null");
        }
        if (StringUtils.isBlank(key)) {
            logger.error("get the file, key is null");
        }

        String filesUrl = filePath + key + SLASH + fileName;
        File file = new File(filesUrl);
        byte[] bytes = null;
        BufferedInputStream in = null;
        if (file.exists()) {
            try {
                in = new BufferedInputStream(new FileInputStream(file));
                bytes = IOUtils.toByteArray(in);
            } catch (FileNotFoundException e) {
                logger.error("get the file failed, fileName: {}, error: {}", fileName, e.getMessage(), e);
            } catch (IOException e) {
                logger.error("get the file IO failed, fileName: {}, error: {}", fileName, e.getMessage(), e);
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("get the file IO close failed, fileName: {}, error: {}", fileName, e.getMessage(), e);
                }
            }
        } else {
            logger.error("the file is not exist, fileName: {}", fileName);
        }
        return bytes;
    }

    @Override
    public boolean delete(String fileName, String key) {

        if (StringUtils.isBlank(fileName)) {
            logger.error("delete the file, fileName is null");
        }
        if (StringUtils.isBlank(key)) {
            logger.error("delete the file, key is null");
        }
        String filesUrl = filePath + key + SLASH + fileName;
        File file = new File(filesUrl);
        boolean flag = false;
        if (file.exists()) {
            flag = file.delete();
        } else {
            logger.error("delete the file, the file is not exist, fileName: {}", fileName);
        }
        return flag;
    }
}
