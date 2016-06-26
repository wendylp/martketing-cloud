package cn.rongcapital.mkt.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
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
    private static String FILE_SUFFIX = ".csv";

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
     * @param poList
     * @param fileName
     * @return
     */

    // 如果调用该方法生成文件 , 也得用反射的方式把数据存入对象中并update数据库
    public static <E extends BaseQuery> File generateFileforDownload(List<E> poList, String fileName) {
        StringBuilder pathNameBuilder = new StringBuilder();
        // 当前日期
        DateTime today = DateTime.now();
        pathNameBuilder.append(ApiConstant.DOWNLOAD_BASE_DIR).append(fileName)
                        .append(today.toString(ApiConstant.DATE_FORMAT_yyyy_MM_dd)).append(FILE_SUFFIX);
        File file = new File(pathNameBuilder.toString());
        return generateFileforDownload(poList, file);
    }

    public static <E extends BaseQuery> File generateFileforDownload(List<E> poList, File file) {

        // 没数据的时候生成空文件
        if (CollectionUtils.isEmpty(poList)) {
            return file;
        }

        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            int rowCount = poList.size();
            for (int i = 0; i < rowCount; i++) {
                E entity = poList.get(i);
                StringBuilder stringBuilder = new StringBuilder();
                Field[] fields = entity.getClass().getDeclaredFields();
                int fieldsCount = fields.length;

                for (int j = 0; j < fieldsCount; j++) {
                    Field field = fields[j];
                    // 这是个日期类型,需要转换
                    if (field != null && field.getClass().getSimpleName().equals(Date.class.getSimpleName())) {
                        DateTime dateTime = new DateTime(field.get(entity));
                        stringBuilder.append(dateTime.toString(ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
                        stringBuilder.append(FILE_SEPERATOR);

                        continue;
                    }

                    stringBuilder.append(field.get(entity));
                    if (j == fieldsCount - 1) {
                        break;
                    } else {
                        stringBuilder.append(FILE_SEPERATOR);
                    }
                }

                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();
        } catch (IOException | IllegalArgumentException | IllegalAccessException e) {
            logger.error("生成下载文件时出错", e);
        } finally {
            try {
                fileWriter.close();
                bufferedWriter.close();
            } catch (IOException e) {
                logger.error("生成下载文件后关闭资源时出错", e);
            }
        }

        return file;
    }
}
