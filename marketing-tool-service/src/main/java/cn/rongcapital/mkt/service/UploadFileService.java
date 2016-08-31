package cn.rongcapital.mkt.service;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 * Created by Yunfeng on 2016-6-2.
 */
public interface UploadFileService {

    Object uploadFile(String fileUnique, MultipartFormDataInput fileInput);

    Object uploadRepairFile(String fileUnique, MultipartFormDataInput fileInput);
    
    Object uploadFileBatch(String fileUnique, MultipartFormDataInput fileInput);

}
