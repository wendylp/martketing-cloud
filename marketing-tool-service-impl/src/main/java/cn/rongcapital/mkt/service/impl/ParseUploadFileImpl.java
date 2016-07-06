package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.GenderEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.job.service.base.TaskManager;
import cn.rongcapital.mkt.service.impl.vo.UploadFileProcessVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yunfeng on 2016-6-13.
 * 把上传上来的文件解析，解析后得到的摘要，数据条数，未识别属性
 */
@Service
public class ParseUploadFileImpl {
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

    private Logger logger = LoggerFactory.getLogger(getClass());


    public UploadFileProcessVO parseAndInsertUploadFileByType(String fileUnique, String fileName, byte[] bytes) {
        String[] typeAndBatchId = fileName.split("_");
        if (typeAndBatchId.length < 2) {
            return null;
        }
        int fileType = Integer.parseInt(typeAndBatchId[0].substring(typeAndBatchId[0].length()-1));
        String batchId = typeAndBatchId[1];

        StringBuffer illegalColumns = new StringBuffer();
        ArrayList<Map<String,Object>> rowDataList = new ArrayList<>();
        UploadFileProcessVO uploadFileProcessVO = new UploadFileProcessVO();
        int totalRows = parseFile(bytes, illegalColumns, rowDataList, fileType, batchId, fileUnique);
        int effectRows = 0;
        if(totalRows > 0){
            effectRows = insertParsedData(rowDataList, fileType);
        }
        uploadFileProcessVO.setTotalRows(Integer.valueOf(totalRows));
        uploadFileProcessVO.setLegalRows(effectRows);
        uploadFileProcessVO.setIllegalRows(totalRows - effectRows);
        uploadFileProcessVO.setDataTopic(importTemplateDao.selectTempleNameByType(fileType));
        uploadFileProcessVO.setFileType(fileType + "");
        if(illegalColumns.length() > 0){
            uploadFileProcessVO.setUnrecognizeFields(illegalColumns.substring(0, illegalColumns.length()-1));
        }else{
            uploadFileProcessVO.setUnrecognizeFields(illegalColumns.toString());
        }
        return uploadFileProcessVO;
    }

    private int insertParsedData(ArrayList<Map<String, Object>> insertList, int fileType) {
        int effectRows = 0;
        if (fileType == 0) {
            return effectRows;
        }
        switch(fileType){
            case ImportConstant.POPULATION_FILE:
                return originalDataPopulationDao.batchInsertUploadFileData(insertList);
            case ImportConstant.CUSTOMER_TAG_FILE:
                return originalDataCustomerTagsDao.batchInsertUploadFileData(insertList);
            case ImportConstant.ARCH_POINT_FILE:
                return originalDataArchPointDao.batchInsertUploadFileData(insertList);
            case ImportConstant.MEMBER_FILE:
                return originalDataMemberDao.batchInsertUploadFileData(insertList);
            case ImportConstant.LOGIN_FILE:
                return originalDataLoginDao.batchInsertUploadFileData(insertList);
            case ImportConstant.PAYMENT_FILE:
                return originalDataPaymentDao.batchInsertUploadFileData(insertList);
            case ImportConstant.SHOPPING_FILE:
                return originalDataShoppingDao.batchInsertUploadFileData(insertList);
            default:
                return effectRows;
        }
    }

    /**
     * parse file
     * @param bytes
     * @param illegalColumns
     * @param rowDataList
     * @param fileType
     * @param batchId
     * @param fileUnique
     * @return data rows ,not include header
     */
    private int parseFile(byte[] bytes, StringBuffer illegalColumns, ArrayList<Map<String, Object>> rowDataList,
            int fileType, String batchId, String fileUnique) {
        Map<String, String> nameCodeMappingMap = getNameCodeRelationByFileType(fileType);
        Map<String, Integer> codeIndexMap = new HashMap<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes), StandardCharsets.UTF_8));
        int totalRows = 0;
        try{
            String line;
            boolean isFileHeadFlag = true;
            while((line = bufferedReader.readLine()) != null){
                String[] uploadFileColumns = line.replace(" ","").split(",");
                if (uploadFileColumns.length < 1) {
                    continue;
                }
                if(isFileHeadFlag){
                    String firstColumn = uploadFileColumns[0];
                    String firstColumnUTH8Str = new String(firstColumn.getBytes(), "UTF-8");
                    if (!firstColumn.equals(firstColumnUTH8Str)) {
                        return -1;
                    }
                    parseHeader(illegalColumns, codeIndexMap, nameCodeMappingMap, uploadFileColumns);
                    isFileHeadFlag = false;
                }else{
                    parseRowDataList(codeIndexMap, rowDataList, uploadFileColumns, batchId, fileUnique,fileType);
                    totalRows++;
                }

            }
        }catch (Exception e){

        }

        return totalRows;
    }

    /**
     * @功能简述: 根据对应关系构造出文件解析后的插入数据数组
     * @param: ArrayList<String> illegalColumns, Map<String, Object> codeIndexMap, Map<String, String> nameCodeMap, String[] uploadFileColumns
     */
    private void parseRowDataList(Map<String, Integer> codeIndexMap,
            ArrayList<Map<String, Object>> rowDataList, String[] uploadFileColumns, String batchId, String fileUnique, int fileType) {
        Map<String,Object> insertMap = new HashMap<>();
        for(Map.Entry<String, Integer> entry : codeIndexMap.entrySet()){
            String cloumnCode = entry.getKey();
            int cloumnIndex = entry.getValue().intValue();
            if(uploadFileColumns.length  > cloumnIndex && !("".equals(uploadFileColumns[cloumnIndex]))){
                if(cloumnCode.endsWith("time") || cloumnCode.endsWith("birthday")){
                    Date date = DateUtil.parseTimeInUploadFile(uploadFileColumns[cloumnIndex]);
                    insertMap.put(cloumnCode, date);
                    continue;
                }
                insertMap.put(cloumnCode, uploadFileColumns[cloumnIndex]);
            }else{
                insertMap.put(cloumnCode, null);
            }
        }

        if(validateData(insertMap, fileType)){
            convertData(insertMap, fileType);
            insertMap.put("file_unique",fileUnique);
            insertMap.put("batch_id",batchId);
            rowDataList.add(insertMap);
        }
    }

    /**
     * @功能简述: 验证数据解析后是否合法
     * @param: ArrayList<String> illegalColumns, Map<String, Object> codeIndexMap, Map<String, String> nameCodeMap, String[] uploadFileColumns
     */
    private boolean validateData(Map<String, Object> insertMap,int fileType) {
        if(fileType == ImportConstant.POPULATION_FILE){
            String mobile = (String) insertMap.get(ImportConstant.MOBILE_FIELD);
            if(!StringUtils.hasLength(mobile) || mobile.trim().length() != ImportConstant.MOBILE_LENGTH){
                return false;
            }

            String maritalStatus = (String) insertMap.get(ImportConstant.MARITAL_STATUS_FIELD);
            if (!ImportConstant.MARITAL_STATUS_MARRIED.equals(maritalStatus) &&
                    !ImportConstant.MARITAL_STATUS_SINGLE.equals(maritalStatus) &&
                    !ImportConstant.MARITAL_STATUS_UNKNOWN.equals(maritalStatus)) {
                return false;
            }
            String monthlyIncome = (String) insertMap.get(ImportConstant.MONTHLY_INCOME_FIELD);
            String monthlyConsume = (String) insertMap.get(ImportConstant.MONTHLY_CONSUME_FIELD);
            if (!isNumber(monthlyIncome) || !isNumber(monthlyConsume)) {
                return false;
            }

            return true;

        }else if(fileType == ImportConstant.SHOPPING_FILE){
            String orderNo = (String) insertMap.get(ImportConstant.ORDER_NO_FIELD);
            String transSerial = (String) insertMap.get(ImportConstant.TRANS_SERIAL_FIELD);
            String discount = (String) insertMap.get(ImportConstant.DISCOUNT_AMT_FIELD);
            String price = (String) insertMap.get(ImportConstant.PRICE_FIELD);

            if(!StringUtils.hasText(orderNo) || !StringUtils.hasText(transSerial)){
                return false;
            }

            if(!isNumber(discount) || !isNumber(price)){
                return false;
            }
            return true;
        }else if(fileType == ImportConstant.PAYMENT_FILE){
            String orderNo = (String) insertMap.get(ImportConstant.ORDER_NO_FIELD);
            String transSerial = (String) insertMap.get(ImportConstant.TRANS_SERIAL_FIELD);
            String mobile = (String) insertMap.get(ImportConstant.MOBILE_FIELD);
            String incomeAmt = (String) insertMap.get(ImportConstant.INCOME_AMT_FIELD);
            String paidAmt = (String) insertMap.get(ImportConstant.PAID_AMT_FIELD);
            String acctAmt = (String) insertMap.get(ImportConstant.ACCT_AMT_FIELD);
            if(StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(transSerial) ||
                    StringUtils.isEmpty(mobile)){
                return false;
            }
            if (!isNumber(incomeAmt) || !isNumber(paidAmt) || !isNumber(acctAmt)) {
                return false;
            }
            return true;
        } else {

            if (fileType == ImportConstant.MEMBER_FILE) {
                String cardAmt = (String) insertMap.get(ImportConstant.CARD_AMT_FIELD);
                if (!isNumber(cardAmt)) {
                    return false;
                }
            } else if (fileType == ImportConstant.CUSTOMER_TAG_FILE) {
                String tagType = (String) insertMap.get("tag_type");
                if(StringUtils.hasText(tagType) && !ImportConstant.TAG_TYPE_DATE.equals(tagType) &&
                           !ImportConstant.TAG_TYPE_TEXT.equals(tagType)){
                    return false;
                }

            }

            String mobile = (String) insertMap.get(ImportConstant.MOBILE_FIELD);
            if(!StringUtils.hasLength(mobile) || mobile.trim().length() != ImportConstant.MOBILE_LENGTH){
                return false;
            }
            return true;
        }
    }

    /**
     * @功能简述: 构造出数据库列名与文件中相应列索引的对应关系
     * @param: ArrayList<String> illegalColumns, Map<String, Object> codeIndexMap, Map<String, String> nameCodeMap, String[] uploadFileColumns
     */
    private void parseHeader(StringBuffer illegalColumns, Map<String, Integer> codeIndexMap, Map<String, String> nameCodeMap, String[] uploadFileColumns) {
        int column = 0;
        for(String uploadFileColumn : uploadFileColumns){
            String fieldCode = nameCodeMap.get(uploadFileColumn);
            if(fieldCode != null){
                codeIndexMap.put(fieldCode, Integer.valueOf(column));
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

    private void convertData(Map<String, Object> insertMap, int fileType) {
        Object gender =  insertMap.get(ImportConstant.GENDER_FIELD);
        if (gender != null) {
            if (GenderEnum.MALE.getDescription().equals(gender)){
                insertMap.put(ImportConstant.GENDER_FIELD, GenderEnum.MALE.getStatusCode());
            } else if (GenderEnum.FEMALE.getDescription().equals(gender)) {
                insertMap.put(ImportConstant.GENDER_FIELD, GenderEnum.FEMALE.getStatusCode());
            } else if (GenderEnum.OTHER.getDescription().equals(gender)) {
                insertMap.put(ImportConstant.GENDER_FIELD, GenderEnum.OTHER.getStatusCode());
            }
        }

        if (fileType == ImportConstant.CUSTOMER_TAG_FILE) {
            String tagType = (String) insertMap.get("tag_type");
            if(tagType != null && tagType.length() > 0){
                if(ImportConstant.TAG_TYPE_DATE.equals(tagType)){
                    insertMap.put("tag_type", Integer.valueOf(1));
                }else if(ImportConstant.TAG_TYPE_TEXT.equals(tagType)){
                    insertMap.put("tag_type",Integer.valueOf(0));
                }
            }
        }

        insertMap.put(ImportConstant.STATUS_FIELD, StatusEnum.DELETED.getStatusCode());

    }

    private boolean isNumber(String valStr) {
        if (!StringUtils.hasText(valStr)) {
            return true;
        }

        Pattern pattern = Pattern.compile("^((\\d*)|(\\d+\\.\\d+))?$");
        Matcher match = pattern.matcher(valStr);
        return match.matches();
    }

    interface ImportConstant {

        int POPULATION_FILE = 1;
        int CUSTOMER_TAG_FILE = 2;
        int ARCH_POINT_FILE = 3;
        int MEMBER_FILE = 4;
        int LOGIN_FILE = 5;
        int PAYMENT_FILE = 6;
        int SHOPPING_FILE = 7;

        String GENDER_FIELD = "gender";
        String ORDER_NO_FIELD = "order_no";
        String TRANS_SERIAL_FIELD = "trans_serial";
        String MOBILE_FIELD = "mobile";
        String MARITAL_STATUS_FIELD = "marital_status";
        String CARD_AMT_FIELD = "card_amt";
        String INCOME_AMT_FIELD = "income_amt";
        String PAID_AMT_FIELD = "paid_amt";
        String ACCT_AMT_FIELD = "acct_amt";
        String MONTHLY_INCOME_FIELD = "monthly_income";
        String MONTHLY_CONSUME_FIELD = "monthly_consume";
        String DISCOUNT_AMT_FIELD = "discount_amt";
        String PRICE_FIELD = "price";
        String STATUS_FIELD = "status";

        int MOBILE_LENGTH = 11;
        String MARITAL_STATUS_SINGLE = "未婚";
        String MARITAL_STATUS_MARRIED = "已婚";
        String MARITAL_STATUS_UNKNOWN = "未知";

        String TAG_TYPE_DATE = "日期型标签";
        String TAG_TYPE_TEXT = "文本型标签";

    }

}
