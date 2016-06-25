package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.out.UploadFileAccordTemplateOut;

/**
 * Created by Yunfeng on 2016-6-13.
 */
public interface ParseUploadFile {
    UploadFileAccordTemplateOut parseUploadFileByType(String fileUnique,String fileName, byte[] bytes);
}
