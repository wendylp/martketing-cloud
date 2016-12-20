package cn.rongcapital.mkt.file;


public interface FileStorageService {

    /**
     * 文件存储
     * @param fileByte 
     * @param fileName 
     * @param key 标识
     * @return
     */
    void save(byte[] fileByte, String fileName, String key);
    
    /**
     * 文件读取
     * @param fileName
     * @param key
     * @return
     */
    byte[] get(String fileName, String key);
    
    /**
     * 文件删除
     * @param fileName
     * @param key
     * @return
     */
    boolean delete(String fileName, String key);
}
