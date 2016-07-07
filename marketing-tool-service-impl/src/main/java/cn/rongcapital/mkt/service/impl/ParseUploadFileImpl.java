package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.enums.GenderEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.service.impl.vo.ParseFileVO;
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

    private static Logger logger = LoggerFactory.getLogger(ParseUploadFileImpl.class);

    public UploadFileProcessVO parseAndInsertUploadFileByType(String fileUnique, int fileType, String batchId, byte[] bytes) {
        ParseFileVO parseFileVO = parseFile(bytes, fileType, batchId, fileUnique);
        ArrayList<Map<String, Object>> legalDataList = parseFileVO.getLegalDataList();
        int effectRows = 0;
        if(legalDataList.size() > 0){
            effectRows = insertParsedData(legalDataList, fileType);
        }

        UploadFileProcessVO uploadFileProcessVO = new UploadFileProcessVO();
        uploadFileProcessVO.setTotalRows(parseFileVO.getTotalRows());
        uploadFileProcessVO.setLegalRows(effectRows);
        uploadFileProcessVO.setIllegalRows(parseFileVO.getTotalRows() - effectRows);
        uploadFileProcessVO.setDataTopic(importTemplateDao.selectTempleNameByType(fileType));
        uploadFileProcessVO.setFileType(fileType + "");
        uploadFileProcessVO.setUnrecognizeFields(parseFileVO.getIllegaColumns());
        uploadFileProcessVO.setFileHeader(parseFileVO.getHeader());
        uploadFileProcessVO.setIllegalRawData(parseFileVO.getIllegaRawDataList());
        uploadFileProcessVO.setEmailRows(parseFileVO.getEmailRows());
        uploadFileProcessVO.setMobileRows(parseFileVO.getMobileRows());
        uploadFileProcessVO.setDuplicateRows(parseFileVO.getDuplicateRows());
        return uploadFileProcessVO;
    }

    private int insertParsedData(ArrayList<Map<String, Object>> insertList, int fileType) {
        int effectRows = 0;
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
     * @param fileType
     * @param batchId
     * @param fileUnique
     * @return data rows ,not include header
     */
    private ParseFileVO parseFile(byte[] bytes, int fileType, String batchId, String fileUnique) {
        ArrayList<Map<String,Object>> legalDataList = new ArrayList<>();
        ArrayList<String> illegaDataList = new ArrayList<>();

        Map<String, String> nameCodeMappingMap = getNameCodeRelationByFileType(fileType);
        Map<String, Integer> codeIndexMap = new HashMap<>();
        Map<String, String> dataCheck = new HashMap<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes), StandardCharsets.UTF_8));
        int totalRows = 0;
        int emailRows = 0;
        int mobileRows = 0;
        int duplicateRows = 0;
        String header = null;
        String illegalColumns = null;
        try{
            String line;
            boolean isFileHeadFlag = true;
            while((line = bufferedReader.readLine()) != null){
                String[] uploadFileColumns = line.replace(" ","").split(",");
                if (uploadFileColumns.length < 1) {
                    continue;
                }
                if(isFileHeadFlag){
                    illegalColumns = parseHeader(codeIndexMap, nameCodeMappingMap, uploadFileColumns);
                    isFileHeadFlag = false;
                    header = line;
                }else{
                    String existsData = dataCheck.get(line);
                    if (existsData == null) {
                        dataCheck.put(line, "");
                    } else {
                        duplicateRows++;
                    }
                    int validateResult = parseRowDataList(codeIndexMap, legalDataList, uploadFileColumns, batchId, fileUnique,fileType);
                    if (ImportConstant.VALIDATE_SUCCESS != validateResult) {
                        illegaDataList.add(line);
                    }
                    switch (validateResult) {
                        case ImportConstant.VALIDATE_EMAIL_FAILED:
                            emailRows++;
                            break;
                        case ImportConstant.VALIDATE_MOBILE_FAILED :
                            mobileRows++;
                            break;
                    }

                    totalRows++;
                }
            }
        }catch (Exception e){
            logger.error("文件解析失败!", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {

                }
            }
        }

        ParseFileVO parseFileVO = new ParseFileVO();
        parseFileVO.setLegalDataList(legalDataList);
        parseFileVO.setIllegaRawDataList(illegaDataList);
        parseFileVO.setHeader(header);
        parseFileVO.setIllegaColumns(illegalColumns);
        parseFileVO.setTotalRows(Integer.valueOf(totalRows));
        parseFileVO.setEmailRows(emailRows);
        parseFileVO.setMobileRows(mobileRows);
        parseFileVO.setDuplicateRows(duplicateRows);
        return parseFileVO;
    }

    private int parseRowDataList(Map<String, Integer> codeIndexMap,
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

        int validateResult = validateData(insertMap, fileType);
        if(validateResult == ImportConstant.VALIDATE_SUCCESS){
            convertData(insertMap, fileType);
            insertMap.put("file_unique",fileUnique);
            insertMap.put("batch_id",batchId);
            rowDataList.add(insertMap);
        }
        return validateResult;
    }

    /**
     * @功能简述: 验证数据解析后是否合法
     * @param: ArrayList<String> illegalColumns, Map<String, Object> codeIndexMap, Map<String, String> nameCodeMap, String[] uploadFileColumns
     */
    private int validateData(Map<String, Object> insertMap,int fileType) {
        String email = (String) insertMap.get(ImportConstant.EMAIL_FIELD);
        if(StringUtils.hasText(email)){
            if (!isEmail(email)) {
                return ImportConstant.VALIDATE_EMAIL_FAILED;
            }
        }
        if(fileType == ImportConstant.POPULATION_FILE){
            String mobile = (String) insertMap.get(ImportConstant.MOBILE_FIELD);
            if(!StringUtils.hasLength(mobile) || mobile.trim().length() != ImportConstant.MOBILE_LENGTH){
                return ImportConstant.VALIDATE_MOBILE_FAILED;
            }

            String maritalStatus = (String) insertMap.get(ImportConstant.MARITAL_STATUS_FIELD);
            if (!ImportConstant.MARITAL_STATUS_MARRIED.equals(maritalStatus) &&
                    !ImportConstant.MARITAL_STATUS_SINGLE.equals(maritalStatus) &&
                    !ImportConstant.MARITAL_STATUS_UNKNOWN.equals(maritalStatus)) {
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }
            String monthlyIncome = (String) insertMap.get(ImportConstant.MONTHLY_INCOME_FIELD);
            String monthlyConsume = (String) insertMap.get(ImportConstant.MONTHLY_CONSUME_FIELD);
            if (!isNumber(monthlyIncome) || !isNumber(monthlyConsume)) {
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }

            return ImportConstant.VALIDATE_SUCCESS;

        }else if(fileType == ImportConstant.SHOPPING_FILE){
            String orderNo = (String) insertMap.get(ImportConstant.ORDER_NO_FIELD);
            String transSerial = (String) insertMap.get(ImportConstant.TRANS_SERIAL_FIELD);
            String discount = (String) insertMap.get(ImportConstant.DISCOUNT_AMT_FIELD);
            String price = (String) insertMap.get(ImportConstant.PRICE_FIELD);

            if(!StringUtils.hasText(orderNo) || !StringUtils.hasText(transSerial)){
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }

            if(!isNumber(discount) || !isNumber(price)){
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }
            return ImportConstant.VALIDATE_SUCCESS;
        }else if(fileType == ImportConstant.PAYMENT_FILE){
            String orderNo = (String) insertMap.get(ImportConstant.ORDER_NO_FIELD);
            String transSerial = (String) insertMap.get(ImportConstant.TRANS_SERIAL_FIELD);
            String mobile = (String) insertMap.get(ImportConstant.MOBILE_FIELD);
            String incomeAmt = (String) insertMap.get(ImportConstant.INCOME_AMT_FIELD);
            String paidAmt = (String) insertMap.get(ImportConstant.PAID_AMT_FIELD);
            String acctAmt = (String) insertMap.get(ImportConstant.ACCT_AMT_FIELD);
            if(StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(transSerial) ||
                    StringUtils.isEmpty(mobile)){
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }
            if (!isNumber(incomeAmt) || !isNumber(paidAmt) || !isNumber(acctAmt)) {
                return ImportConstant.VALIDATE_OTHER_FAILED;
            }
            return ImportConstant.VALIDATE_SUCCESS;
        } else {

            if (fileType == ImportConstant.MEMBER_FILE) {
                String cardAmt = (String) insertMap.get(ImportConstant.CARD_AMT_FIELD);
                if (!isNumber(cardAmt)) {
                    return ImportConstant.VALIDATE_OTHER_FAILED;
                }
            } else if (fileType == ImportConstant.CUSTOMER_TAG_FILE) {
                String tagType = (String) insertMap.get("tag_type");
                if(StringUtils.hasText(tagType) && !ImportConstant.TAG_TYPE_DATE.equals(tagType) &&
                           !ImportConstant.TAG_TYPE_TEXT.equals(tagType)){
                    return ImportConstant.VALIDATE_OTHER_FAILED;
                }

            }

            String mobile = (String) insertMap.get(ImportConstant.MOBILE_FIELD);
            if(!StringUtils.hasLength(mobile) || mobile.trim().length() != ImportConstant.MOBILE_LENGTH){
                return ImportConstant.VALIDATE_MOBILE_FAILED;
            }
            return ImportConstant.VALIDATE_SUCCESS;
        }
    }

    /**
     * @功能简述: 构造出数据库列名与文件中相应列索引的对应关系
     * @param: ArrayList<String> illegalColumns, Map<String, Object> codeIndexMap, Map<String, String> nameCodeMap, String[] uploadFileColumns
     */
    private String parseHeader(Map<String, Integer> codeIndexMap,
                             Map<String, String> nameCodeMap, String[] uploadFileColumns) {
        StringBuffer illegalColumns = new StringBuffer();
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

        if (illegalColumns.length() > 0) {
            return illegalColumns.substring(0, illegalColumns.length() - 1);
        }
        return "";
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

    private boolean isEmail(String valStr) {
        if (!StringUtils.hasText(valStr)) {
            return true;
        }

        Pattern pattern = Pattern.compile("^(\\w|-|_|\\.)+@(\\w|-|_|\\.)+[a-zA-Z]+$");
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
        String EMAIL_FIELD = "email";
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

        int VALIDATE_SUCCESS = 0;
        int VALIDATE_MOBILE_FAILED = 1;
        int VALIDATE_EMAIL_FAILED = 2;
        int VALIDATE_OTHER_FAILED = 3;


    }

}
