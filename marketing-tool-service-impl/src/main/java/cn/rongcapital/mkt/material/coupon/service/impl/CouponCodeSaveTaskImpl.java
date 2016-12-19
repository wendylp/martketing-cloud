package cn.rongcapital.mkt.material.coupon.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.enums.CouponCodeType;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponReadyStatusType;
import cn.rongcapital.mkt.common.enums.MaterialCouponSourceCodeTypeEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@Service
public class CouponCodeSaveTaskImpl implements TaskService{

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private static final String[] DATABASE_LETTER = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    
    private static final String[] DATABASE_NUMBER = {"0","1","2","3","4","5","6","7","8","9"};
    
    private static final String[] DATABASE_BLEND = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};
    
    public final static String UPLOADED_FILE_PATH = "\\rc\\data\\uploadFiles\\code\\";
    
    public final static String SLASH = File.separator;
    
    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;
    
    @Autowired
    private MaterialCouponDao materialCouponDao;
    
    @Override
    public void task(Integer taskId) {
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void task(String taskHeadIdStr) {
        try {
            logger.info("MQ消费，起始时间" + System.currentTimeMillis());
            JSONUtils.parse(taskHeadIdStr);
            Date now = new Date();
            JSONObject jsonObject = JSONObject.parseObject(taskHeadIdStr);
            String SourceCode = jsonObject.getString("source_code");
            String user_token = jsonObject.getString("user_token");
            String rule = jsonObject.getString("rule");
            Integer stock_total = jsonObject.getInteger("stock_total");
            Long couponId = jsonObject.getLong("coupon_id");
            List<MaterialCouponCode> list = new ArrayList<MaterialCouponCode>();
            Boolean editFlag = jsonObject.getBoolean("edit");
            if (editFlag) {
                // 编辑
                materialCouponCodeDao.deleteCodeByCouponId(couponId);
            }
            if (SourceCode.equals(CouponCodeType.UNIVERSALCODE.getCode())) {
                // 通用码
                JSONObject ruleObject = JSONObject.parseObject(rule);
                getUniversalCode(ruleObject, stock_total, couponId, list, now);
            } else if (SourceCode.equals(CouponCodeType.GENERATIONCODE.getCode())) {
                JSONObject ruleObject = JSONObject.parseObject(rule);
                getGenerationCode(ruleObject, stock_total, couponId, list, now);
            } else {
                // //自有码
                JSONArray rules = JSONArray.parseArray(rule);
                getOwnCode(getNameList(rules), user_token, couponId, list, now);
            }
            int totleSize = list.size();
            if (totleSize > 0) {
                int pageSize = 100000;
                int num = totleSize / pageSize;
                int surplus = totleSize % pageSize;
                boolean surplusFlag = false;
                if (surplus > 0) {
                    num = num + 1;
                    surplusFlag = true;
                }
                for (int i = 0; i < num; i++) {
                    List<MaterialCouponCode> codeList = null;
                    if (surplusFlag && (i == num - 1)) {
                        codeList = list.subList(i * pageSize, (i * pageSize + surplus));
                    } else {
                        codeList = list.subList(i * pageSize, (i + 1) * pageSize);
                    }
                    materialCouponCodeDao.batchInsert(codeList);
                }
                MaterialCoupon coupon = materialCouponDao.selectOneCoupon(couponId);
                coupon.setReadyStatus(MaterialCouponReadyStatusType.READY.getCode());
                materialCouponDao.updateById(coupon);
                logger.info("MQ消费，结束时间" + System.currentTimeMillis());
            }
        } catch (Exception e) {
            logger.error("不是正确的JSON");
        }

    }
    
    /**
     * 通用码
     * @param ruleObject
     * @param stock_total
     * @param couponId
     * @param list
     * @param now
     */
    private void getUniversalCode(JSONObject ruleObject, Integer stock_total, Long couponId, List<MaterialCouponCode> list, Date now){
        String unCode = ruleObject.getString("coupon_code");
        for(int i = 0; i< stock_total; i ++){
            MaterialCouponCode code = new MaterialCouponCode();
            code.setCode(unCode);
            code.setCouponId(couponId);
            code.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.UNRELEASED.getCode());
            code.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
            code.setStatus((byte) 0);
            code.setCreateTime(now);
            code.setUpdateTime(now);
            list.add(code);
        }
    }
    
    /**
     * 平台生成码
     * @param ruleObject
     * @param stock_total
     * @param couponId
     * @param list
     * @param now
     */
    private void getGenerationCode(JSONObject ruleObject, Integer stock_total, Long couponId,
            List<MaterialCouponCode> list, Date now) {
        String codeType = ruleObject.getString("type_code");
        int length = ruleObject.getIntValue("length");
        List<String> codes = null;
        if (MaterialCouponSourceCodeTypeEnum.ALPHA.getCode().equals(codeType)) {
            // 字母组合
            codes = getAllLists(DATABASE_LETTER, length, stock_total);
        } else if (MaterialCouponSourceCodeTypeEnum.NUMBER.getCode().equals(codeType)) {
            // 数字组合
            codes = getAllLists(DATABASE_NUMBER, length, stock_total);
        } else {
            // 混合组合
            codes = getAllLists(DATABASE_BLEND, length, stock_total);
        }
        if (codes.size() > 0) {
            for (String code : codes) {
                String couponCode = code;
                MaterialCouponCode materialCouponCode = new MaterialCouponCode();
                materialCouponCode.setCode(couponCode);
                materialCouponCode.setCouponId(couponId);
                materialCouponCode.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.UNRELEASED.getCode());
                materialCouponCode.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
                materialCouponCode.setStatus((byte) 0);
                materialCouponCode.setCreateTime(now);
                materialCouponCode.setUpdateTime(now);
                list.add(materialCouponCode);
            }
        }
    }
    
    /**
     * 生成码
     * @param elements
     * @param lengthOfList
     * @return
     */
    public List<String> getAllLists(String[] elements, int lengthOfList, int size) {
        
        List<String> codes = new ArrayList<String>();
        Set<String> set = new HashSet<String>();
        while(set.size() < size){
            Random rand = new Random();
            String str = "";
            int index;
            boolean[] flags = new boolean[elements.length];// 默认为false
            for (int j = 0; j < lengthOfList; j++) {
                do {
                    index = rand.nextInt(elements.length);
                } while (flags[index] == true);
                String c = elements[index];
                str += c;
                flags[index] = true;
            }
            if (!set.contains(str)) {
                set.add(str);
            }
        }
        codes.addAll(set);
        return codes;
    }
    
    /**
     * 获取文件名称
     * @param rules
     * @return
     */
    private List<String> getNameList(JSONArray rules){
        List<String> fileNames = new ArrayList<String>();
        int size = rules.size();
        if(size > 0){
            for(int i = 0; i < size ; i++){
               String fileName = rules.getJSONObject(i).getString("file_name");
               fileNames.add(fileName);
            }
        }
        return fileNames;
    }
    
    /**
     * 自有码
     * @param user_token
     * @param baseOutput
     * @param couponId
     * @param list
     * @param now
     */
    private void getOwnCode(List<String> fileNames, String user_token, Long couponId, List<MaterialCouponCode> list, Date now){
        
        String filesUrl = UPLOADED_FILE_PATH + user_token + SLASH;
        List<String> codeList = filesGetCode(fileNames, filesUrl);
        for(String code : codeList){
            String couponCode = code;
            MaterialCouponCode materialCouponCode = new MaterialCouponCode();
            materialCouponCode.setCode(couponCode);
            materialCouponCode.setCouponId(couponId);
            materialCouponCode.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.UNRELEASED.getCode());
            materialCouponCode.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
            materialCouponCode.setStatus((byte) 0);
            materialCouponCode.setCreateTime(now);
            materialCouponCode.setUpdateTime(now);
            list.add(materialCouponCode);
        }
    }
    
    /**
     * 获取服务器文件优惠码
     * @param filesUrl
     * @param baseOutput
     * @return
     */
    private List<String> filesGetCode(List<String> fileNames, String filesUrl) {

        File file = new File(filesUrl);
        File[] tempList = file.listFiles();
        List<String> codeList = new ArrayList<String>();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()
                    && (tempList[i].getName().endsWith(".xls") || tempList[i].getName().endsWith(".xlsx"))
                    && (fileNames.contains(tempList[i].getName()))) {
                File fileNew = tempList[i];
                InputStream is = null;
                try {
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileNew));
                    byte[] bytes = IOUtils.toByteArray(in);
                    is = new ByteArrayInputStream(bytes);
                    Workbook workbook = WorkbookFactory.create(is);
                    Sheet sheet = workbook.getSheetAt(0);
                    Iterator<Row> rowIterator = sheet.rowIterator();
                    while (rowIterator.hasNext()) {
                        Row row = rowIterator.next();
                        Integer rowIndex = row.getRowNum();
                        if (rowIndex == 0) {
                            continue;
                        }
                        Iterator<Cell> dataCellIterator = row.cellIterator();
                        while (dataCellIterator.hasNext()) {
                            Cell dataColumnCell = dataCellIterator.next();
                            int cellType = dataColumnCell.getCellType();
                            if (cellType == 1) {
                                if (!codeList.contains(dataColumnCell.getStringCellValue())) {
                                    codeList.add(dataColumnCell.getStringCellValue());
                                }
                            }
                        }
                    }
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        }
        return codeList;
    }
}
