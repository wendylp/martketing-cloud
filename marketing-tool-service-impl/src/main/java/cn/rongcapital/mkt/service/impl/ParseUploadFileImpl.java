package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.service.ParseUploadFile;
import cn.rongcapital.mkt.vo.out.UploadFileAccordTemplateOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * Created by Yunfeng on 2016-6-13.
 * 把上传上来的文件解析，解析后得到的摘要，数据条数，未识别属性
 */
@Service
public class ParseUploadFileImpl implements ParseUploadFile {
    @Autowired
    private ImportTemplateDao importTemplateDao;
    @Autowired
    private OriginalDataPopulationDao originalDataPopulationDao;
    @Autowired
    private OriginalDataArchPointDao originalDataArchPointDao;
    @Autowired
    private OriginalDataCustomerTagsDao originalDataCustomerTagsDao;
    @Autowired
    private OriginalDataLoginDao originalDataLoginDao;
    @Autowired
    private OriginalDataMemberDao originalDataMemberDao;
    @Autowired
    private OriginalDataPaymentDao originalDataPaymentDao;
    @Autowired
    private OriginalDataShoppingDao originalDataShoppingDao;

    @Override
    public UploadFileAccordTemplateOut parseUploadFileByType(String fileName, byte[] bytes) {
        UploadFileAccordTemplateOut uploadFileAccordTemplateOut = new UploadFileAccordTemplateOut();
        StringBuffer illegalColumns = new StringBuffer();  //用于存放文件中不合法的列名
        Map<String,Object> codeIndexMap = new HashMap<String,Object>();  //用于存放数据库表列名与文件中对应列索引的对应关系
        ArrayList<Map<String,Object>> insertList = new ArrayList<Map<String,Object>>();  //用于存放将要被插入数据库的数据

        String[] typeAndBatchId = fileName.split("_");
        int fileType = Integer.parseInt(typeAndBatchId[0].substring(typeAndBatchId[0].length()-1));
        String batchId = typeAndBatchId[1];

        readAndParseFile(bytes, illegalColumns, codeIndexMap, insertList, fileType, batchId);
        int effectRows = insertParsedData(insertList, fileType);

        uploadFileAccordTemplateOut.setDataTopic(importTemplateDao.selectTempleNameByType(fileType));
        uploadFileAccordTemplateOut.setDataRows(effectRows+"");
        uploadFileAccordTemplateOut.setUnrecognizeFields(illegalColumns.deleteCharAt(illegalColumns.length()-1).toString());
        return uploadFileAccordTemplateOut;
    }

    private int insertParsedData(ArrayList<Map<String, Object>> insertList, int fileType) {
        switch(fileType){
            case 0:
                break;
            case 1:
                return originalDataPopulationDao.batchInsertUploadFileData(insertList);
            case 2:
                return originalDataCustomerTagsDao.batchInsertUploadFileData(insertList);
            case 3:
                return originalDataArchPointDao.batchInsertUploadFileData(insertList);
            case 4:
                return originalDataMemberDao.batchInsertUploadFileData(insertList);
            case 5:
                return originalDataLoginDao.batchInsertUploadFileData(insertList);
            case 6:
                return originalDataPaymentDao.batchInsertUploadFileData(insertList);
            case 7:
                return originalDataShoppingDao.batchInsertUploadFileData(insertList);
        }
        return 0;
    }

    /**
     * @功能简述: 读取文件并做相应处理
     * @param: byte[] bytes, ArrayList<String> illegalColumns, Map<String, Object> codeIndexMap, ArrayList<Map<String, Object>> insertList, int fileType
     */
    private void readAndParseFile(byte[] bytes, StringBuffer illegalColumns, Map<String, Object> codeIndexMap, ArrayList<Map<String, Object>> insertList, int fileType, String batchId) {
        Map<String, String> nameCodeMap = getNameCodeRelationByFileType(fileType);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
        try{
            String line = null;
            boolean isFileHeadFlag = true;
            while((line = bufferedReader.readLine()) != null){
                String[] uploadFileColumns = line.split(",");
                if(isFileHeadFlag){
                    generateCodeFileColumnIndexRelationMap(illegalColumns, codeIndexMap, nameCodeMap, uploadFileColumns);
                    isFileHeadFlag = false;
                }else{
                    generateInsertDataList(codeIndexMap, insertList, uploadFileColumns, batchId);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @功能简述: 根据对应关系构造出文件解析后的插入数据数组
     * @param: ArrayList<String> illegalColumns, Map<String, Object> codeIndexMap, Map<String, String> nameCodeMap, String[] uploadFileColumns
     */
    private void generateInsertDataList(Map<String, Object> codeIndexMap, ArrayList<Map<String, Object>> insertList, String[] uploadFileColumns, String batchId) {
        Map<String,Object> insertMap = new HashMap<String,Object>();
        for(String key : codeIndexMap.keySet()){
            insertMap.put(key,uploadFileColumns[(Integer)codeIndexMap.get(key)]);
        }
        insertMap.put("batch_id",batchId);
        insertList.add(insertMap);
    }

    /**
     * @功能简述: 构造出数据库列名与文件中相应列索引的对应关系
     * @param: ArrayList<String> illegalColumns, Map<String, Object> codeIndexMap, Map<String, String> nameCodeMap, String[] uploadFileColumns
     */
    private void generateCodeFileColumnIndexRelationMap(StringBuffer illegalColumns, Map<String, Object> codeIndexMap, Map<String, String> nameCodeMap, String[] uploadFileColumns) {
        int column = 0;
        for(String uploadFileColumn : uploadFileColumns){
            String fieldCode = nameCodeMap.get(uploadFileColumn);
            if(fieldCode != null){
                codeIndexMap.put(fieldCode,column);
            }else{
                illegalColumns.append(uploadFileColumn + ",");
            }
            column ++;
        }
    }

    /**
     * @功能简述: 根据文件类型获取文件中的汉字列与数据库表的列名的对应关系保存在Map中
     * @param: int fileType
     * @return: Map<String,String> relationMap
     */
    private Map<String, String> getNameCodeRelationByFileType(int fileType) {
        Map<String,String> nameCodeMap = new HashMap<String,String>();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("templ_type",fileType);
        List<Map<String,Object>> fileColumn = importTemplateDao.selectFileColumn(paramMap);
        for(Map<String,Object> map : fileColumn){
            nameCodeMap.put((String)map.remove("field_name"),(String)map.remove("field_code"));
        }
        return nameCodeMap;
    }
}
