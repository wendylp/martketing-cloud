package cn.rongcapital.mkt.file;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface FileService {

    BaseOutput uploadFileBatch(MultipartFormDataInput input, String userId);
    
}
