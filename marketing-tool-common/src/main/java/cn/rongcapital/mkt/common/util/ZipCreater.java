/**
 * 功能描述：创建zip文件包
 */
package cn.rongcapital.mkt.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 
 * @Data 2016.09.26
 */
public class ZipCreater {
	
	static private Logger logger = LoggerFactory.getLogger(ZipCreater.class);
	
	/**
	 * 功能描述：创建zip文件包
	 * @param files 需要被压缩的文件名数组(需要绝对路径)
	 * @param fileName 压缩后的文件名(需要绝对路径)
	 * @throws IOException
	 * @Data 2016.09.26 
	 */
	static public void generateZip(File[] files, String fileName) throws IOException {
		
		// 去除文件不存在的文件名
		List<File> fileLists = new ArrayList<File>();
		for(int i = 0; i < files.length; i++) {
			if(files[i] != null && files[i].isFile() && files[i].exists()) {
				fileLists.add(files[i]);
			}
		}
		
		if(fileLists == null || fileLists.size() < 1) {
			return;
		}
		
		OutputStream os = null;
		ZipOutputStream zos = null;
		BufferedInputStream bis = null;
		
        try {
            logger.debug("begin to execute zip action.");
            os = new BufferedOutputStream(new FileOutputStream(fileName));
            zos = new ZipOutputStream(os);
            byte[] buf = new byte[8192];
            int len;
            for(int i = 0; i<fileLists.size(); i++){
                logger.debug("enter loop to zip file");
                File file = fileLists.get(i);
//                if(file == null || !file.isFile()) continue;
                ZipEntry ze = new ZipEntry(file.getName());
                zos.putNextEntry(ze);
                bis = new BufferedInputStream(new FileInputStream(file));
                while((len = bis.read(buf)) > 0 ){
                    zos.write(buf,0,len);
                }
                bis.close();
                zos.closeEntry();
            }
            zos.closeEntry();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			if (os != null) {
				os.close();
			}
			if (zos != null) {
				zos.close();
			}
			if (bis != null) {
				bis.close();
			}
		}
    }

}
