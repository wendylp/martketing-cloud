package cn.rongcapital.mkt.service;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.core.SecurityContext;

/**
 * Created by Yunfeng on 2016-6-2.
 */
public interface UploadFileService {
    Object uploadFile(String source,String fileUnique, int fileType, MultipartFormDataInput fileInput, SecurityContext securityContext);
}
