package cn.rongcapital.mkt.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.po.base.BaseQuery;

/**
 * Created by Yunfeng on 2016-6-25.
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static String FILE_SEPERATOR = ",";
    private static String WRAP_TEXT_CHARACTOR = "\n";

    public static File generateDownloadFile(List<Map<String, Object>> dataSource, String prefixFileName) {
        String fileName = ApiConstant.DOWNLOAD_BASE_DIR + prefixFileName + System.currentTimeMillis() + ".csv"; // 线上服务器文件生成目录
        // String fileName = prefixFileName + System.currentTimeMillis() + ".csv"; 本地测试目录
        File file = new File(fileName);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            StringBuffer lineBuffer = new StringBuffer();
            // Todo:1.生成文件头
            if (dataSource != null && dataSource.size() > 0) {
                Map<String, Object> rowMap = dataSource.get(0);
                for (String key : rowMap.keySet()) {
                    lineBuffer.append(key + ",");
                }
                bufferedWriter.write(lineBuffer.deleteCharAt((lineBuffer.length() - 1)).toString());
                bufferedWriter.newLine();
                lineBuffer.setLength(0);
            }
            // Todo:2.将数据根据文件头写入文件
            for (Map<String, Object> map : dataSource) {
                for (String key : map.keySet()) {
                    lineBuffer.append(map.get(key) + ",");
                }
                bufferedWriter.write(lineBuffer.deleteCharAt(lineBuffer.length() - 1).toString());
                bufferedWriter.newLine();
                lineBuffer.setLength(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferedWriter)
                    bufferedWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 根据PO类型,文件名生成下载的文件
     * 
     * @param t
     * @param fileName
     * @return
     */
    public static <E extends BaseQuery> File generateFileforDownload(List<E> poList, String fileName) {
        String pathName = ApiConstant.DOWNLOAD_BASE_DIR + fileName + System.currentTimeMillis() + ".csv";
        File file = new File(pathName);
        return generateFileforDownload(poList, file);
    }

    public static <E extends BaseQuery> File generateFileforDownload(List<E> poList, File file) {
        if (CollectionUtils.isEmpty(poList)) {
            return file;
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            int rowCount = poList.size();
            for (int i = 0; i < rowCount; i++) {
                E entity = poList.get(i);
                StringBuilder stringBuilder = new StringBuilder();
                Field[] fields = entity.getClass().getDeclaredFields();
                int fieldsCount = fields.length;

                for (int j = 0; j < fieldsCount; j++) {
                    Field field = fields[j];
                    stringBuilder.append(field.get(entity));
                    if (j == fieldsCount - 1) {
                        stringBuilder.append(WRAP_TEXT_CHARACTOR);
                    } else {
                        stringBuilder.append(FILE_SEPERATOR);
                    }
                }
            }
        } catch (IOException | IllegalArgumentException | IllegalAccessException e) {
            logger.error("生成下载文件时出错", e);
        }

        return null;
    }
}
