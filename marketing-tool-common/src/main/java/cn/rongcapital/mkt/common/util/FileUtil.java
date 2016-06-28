package cn.rongcapital.mkt.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.mkt.common.constant.ApiConstant;

/**
 * Created by Yunfeng on 2016-6-25.
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static String FILE_SEPERATOR = ",";
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
     * 根据PO类型,文件名生成下载的文件 Map的key为数据库字段值,value为对应的字段名
     * 
     * @author nianjun
     * @param poList
     * @param fileName
     * @return
     */

    // 如果调用该方法生成文件 , 也得用反射的方式把数据存入对象中并update数据库
    public static <E> File generateFileforDownload(List<Map<String, String>> columnNames, List<E> poList,
                    String fileName) {
        StringBuilder pathNameBuilder = new StringBuilder();
        // 当前日期
        DateTime today = DateTime.now();
        // pathNameBuilder.append(ApiConstant.DOWNLOAD_BASE_DIR).append(fileName).append("_")
        pathNameBuilder.append("/Users/nianjun/Work/logs/").append(fileName).append("_")
                        .append(today.toString(ApiConstant.DATE_FORMAT_yyyy_MM_dd)).append(FILE_SUFFIX);
        File file = new File(pathNameBuilder.toString());
        return generateFile(columnNames, poList, file);
    }

    private static <E> File generateFile(List<Map<String, String>> columnNames, List<E> poList, File file) {

        if (CollectionUtils.isEmpty(columnNames)) {
            throw new IllegalArgumentException("columnNames为空,需要提供cvs文件的字段名");
        }

        // 没数据的时候生成空文件
        if (CollectionUtils.isEmpty(poList)) {
            return file;
        }

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));
            int rowCount = poList.size();

            // 按数据量取出每一行的数据
            for (int i = 0; i < rowCount; i++) {
                E entity = poList.get(i);
                StringBuilder stringBuilder = new StringBuilder();
                StringBuilder columnTransferNameBuilder = new StringBuilder();
                Field[] fields = entity.getClass().getDeclaredFields();

                // 根据字段名取出每一个字段的值
                for (int j = 0; j < columnNames.size(); j++) {
                    // 先取出数据库中的字段名
                    Map<String, String> columnMap = columnNames.get(j);
                    String columnName = columnMap.keySet().iterator().next();

                    if (i == 0) {
                        // 如果像import_template表一样,字段名有对应的中文名,则取出中文名
                        String transferName = columnMap.get(columnName);
                        if (!StringUtils.isEmpty(transferName)) {
                            columnTransferNameBuilder.append(transferName);
                        } else {
                            columnTransferNameBuilder.append(ReflectionUtil.recoverFieldName(columnName));
                        }
                    }

                    // 根据字段名获取对象对应的字段
                    Field field = getFieldByName(fields, ReflectionUtil.recoverFieldName(columnName));
                    field.setAccessible(true);

                    // 这是个日期类型,需要转换
                    if (field != null && field.getClass().getSimpleName().equals(Date.class.getSimpleName())) {
                        // joda time的转换比较方便
                        DateTime dateTime = new DateTime(field.get(entity));
                        stringBuilder.append(dateTime.toString(ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
                        stringBuilder.append(FILE_SEPERATOR);

                        continue;
                    }

                    stringBuilder.append(field.get(entity));
                    if (j == columnNames.size() - 1) {
                        break;
                    } else {
                        stringBuilder.append(FILE_SEPERATOR);
                        columnTransferNameBuilder.append(FILE_SEPERATOR);
                    }
                }

                if (i == 0) {
                    bufferedWriter.write(columnTransferNameBuilder.toString());
                    bufferedWriter.newLine();
                }

                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();
        } catch (IOException | IllegalArgumentException | IllegalAccessException e) {
            logger.error("生成下载文件时出错", e);
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                logger.error("生成下载文件后关闭资源时出错", e);
            }
        }

        return file;
    }

    // 根据字段名称,获取对象中对应的Field对象
    private static Field getFieldByName(Field[] fields, String name) {
        if (fields == null || fields.length < 1) {
            throw new IllegalArgumentException("fields对象为空");
        }

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field != null && field.getName().equalsIgnoreCase(name)) {
                return field;
            }
        }

        return null;
    }

    /**
     * 用于转换列名参数,将list转换为map,转换后map的key为list<String>的string,value为""
     * 
     * @author nianjun
     * @param columnNames
     * @return
     */
    public static List<Map<String, String>> transferNameListtoMap(List<String> columnNames) {
        List<Map<String, String>> columnNamesMap = new ArrayList<>();
        if (CollectionUtils.isEmpty(columnNames)) {
            Map<String, String> emptyMap = new HashMap<>();
            emptyMap.put("", "");
            columnNamesMap.add(emptyMap);
        } else {
            for (String columnName : columnNames) {
                Map<String, String> map = new HashMap<>();
                map.put(columnName, "");
                columnNamesMap.add(map);
            }
        }

        return columnNamesMap;
    }
}
