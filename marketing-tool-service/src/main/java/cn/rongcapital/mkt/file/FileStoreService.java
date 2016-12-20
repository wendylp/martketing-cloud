package cn.rongcapital.mkt.file;


public interface FileStoreService {

    /**
     * 文件存储
     * @param fileByte
     * @param fileName
     * @param owner
     * @return
     */
    Boolean uploadFile(byte[] fileByte, String fileName, String owner);
    
    /**
     * 文件读取
     * @param fileName
     * @param owner
     * @return
     */
    byte[] getFile(String fileName, String owner);
    
    /**
     * 文件删除
     * @param fileName
     * @param owner
     * @return
     */
    Boolean fileDelete(String fileName, String owner);
}
