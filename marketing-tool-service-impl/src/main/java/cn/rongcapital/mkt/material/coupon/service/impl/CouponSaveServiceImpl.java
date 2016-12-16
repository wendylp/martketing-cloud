/*************************************************
 * @功能简述: CouponSaveService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: guozhenchao
 * @version: 1.0
 * @date: 2016/12/13
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.CouponCodeType;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponSourceCodeTypeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponTypeEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.CouponSaveService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CouponInfoIn;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class CouponSaveServiceImpl implements CouponSaveService, TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MaterialCouponDao materialCouponDao;
    
    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;
    
    public static final String PATTERN_STANDARD19H = "yyyy-MM-dd 00:00:00";

    private static final String[] DATABASE_LETTER = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    
    private static final String[] DATABASE_NUMBER = {"0","1","2","3","4","5","6","7","8","9"};
    
    private static final String[] DATABASE_BLEND = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};
    
    public final static String UPLOADED_FILE_PATH = "\\rc\\data\\uploadFiles\\code\\";
    
    public final static String SLASH = File.separator;
    
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseOutput save(CouponInfoIn couponInfo,String user_token) {
        Date startday = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("save 接口 start 起始时间：" + df.format(startday));
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
        Long id = couponInfo.getId();
        String title = couponInfo.getTitle();
        String SourceCode = couponInfo.getSource_code();
        String rule = couponInfo.getRule();
        Integer stock_total = couponInfo.getStock_total();
        BigDecimal amount = couponInfo.getAmount();
        String channel_code = couponInfo.getChannel_code();
        Date startTime = couponInfo.getStart_time();
        Date startTimeNew = dateStart(startTime, null);
        Date endTime = couponInfo.getEnd_time();
        Date endTimeNew = dateEnd(endTime, 23*60*60+59*60+59);
        Date now = new Date();
        List<MaterialCouponCode> list = new ArrayList<MaterialCouponCode>();
        try {
            JSONUtils.parse(rule);
        } catch (Exception e) {
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.VALIDATE_ERROR.getMsg());
            return baseOutput;
        }
        
        if(id == null){
            //保存操作
            MaterialCoupon coupon = new MaterialCoupon();
            coupon.setTitle(title);
            coupon.setType(MaterialCouponTypeEnum.VOUCHER.getCode());
            coupon.setRule(rule);
            coupon.setStockTotal(stock_total);
            coupon.setStockRest(stock_total);
            coupon.setAmount(amount);
            coupon.setSourceCode(SourceCode);
            coupon.setChannelCode(channel_code);
            coupon.setCouponStatus(MaterialCouponStatusEnum.UNUSED.getCode());
            coupon.setCreateTime(now);
            coupon.setUpdateTime(now);
            coupon.setStartTime(startTimeNew);
            coupon.setEndTime(endTimeNew);
            materialCouponDao.insert(coupon);
            if(SourceCode.equals(CouponCodeType.UNIVERSALCODE.getCode())){
               //通用码
                JSONObject ruleObject = JSONObject.parseObject(rule);
                getUniversalCode(ruleObject, stock_total, coupon.getId(), list, now);
            }else if(SourceCode.equals(CouponCodeType.GENERATIONCODE.getCode())){
                //平台生成码
                Date day1 = new Date();
                logger.info("save 接口 平台生成码 起始时间：" + df.format(day1));
                JSONObject ruleObject = JSONObject.parseObject(rule);
                getGenerationCode(ruleObject, stock_total, coupon.getId(), list, now);
                Date day2 = new Date();
                logger.info("save 接口 平台生成码 结束时间：" + df.format(day2));
            }else{
                //自有码
                JSONArray rules =  JSONArray.parseArray(rule);
                getOwnCode(getNameList(rules), user_token, baseOutput, coupon.getId(), list, now);
            }
        }else{
            //更新操作
            MaterialCoupon coupon = materialCouponDao.selectOneCoupon(id);
            if(coupon == null){
                baseOutput.setCode(ApiErrorCode.DB_ERROR.getCode());
                baseOutput.setMsg(ApiErrorCode.DB_ERROR.getMsg());
                return baseOutput;
            }
            //只有未投放才能编辑
            if(!MaterialCouponStatusEnum.UNUSED.getCode().equals(coupon.getCouponStatus())){
                baseOutput.setCode(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_UPDATE_ERROR.getCode());
                baseOutput.setMsg(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_UPDATE_ERROR.getMsg());
                return baseOutput;
            }
            
            coupon.setTitle(title);
            coupon.setType(MaterialCouponTypeEnum.VOUCHER.getCode());
            coupon.setRule(rule);
            coupon.setStockTotal(stock_total);
            coupon.setStockRest(stock_total);
            coupon.setAmount(amount);
            coupon.setSourceCode(SourceCode);
            coupon.setChannelCode(channel_code);
            coupon.setCouponStatus(MaterialCouponStatusEnum.UNUSED.getCode());
            coupon.setCreateTime(now);
            coupon.setUpdateTime(now);
            coupon.setStartTime(startTimeNew);
            coupon.setEndTime(endTimeNew);
            materialCouponDao.updateById(coupon);
            
          //干掉以前的code
            materialCouponCodeDao.deleteCodeByCouponId(id);
            if(SourceCode.equals(CouponCodeType.UNIVERSALCODE.getCode())){
              //通用码
                JSONObject ruleObject = JSONObject.parseObject(rule);
                getUniversalCode(ruleObject, stock_total, coupon.getId(), list, now);
            }else if(SourceCode.equals(CouponCodeType.GENERATIONCODE.getCode())){
                //平台生成码
                Date day1 = new Date();
                logger.info("save 接口 平台生成码 起始时间：" + df.format(day1));
                JSONObject ruleObject = JSONObject.parseObject(rule);
                getGenerationCode(ruleObject, stock_total, coupon.getId(), list, now);
                Date day2 = new Date();
                logger.info("save 接口 平台生成码 结束时间：" + df.format(day2));
            }else{
                //自有码
                JSONArray rules =  JSONArray.parseArray(rule);
                getOwnCode(getNameList(rules), user_token, baseOutput, coupon.getId(), list, now);
            }
        }
//        保存优惠码
//        方法1：直接插入
//        if(list.size() > 0){
//            materialCouponCodeDao.batchInsert(list);
//        };
        
//        方法2：分批插入，平均一次插入100000条
          int totleSize = list.size();
          if(totleSize > 0){
              int pageSize = 100000;
              int num = totleSize / pageSize;
              int surplus = totleSize % pageSize;
              boolean surplusFlag = false;
              if(surplus > 0){
                   num = num +1;
                   surplusFlag = true;
              }
              for(int i = 0; i < num; i++){
                  List<MaterialCouponCode> codeList = null;
                  if(surplusFlag && (i == num -1)){
                      codeList = list.subList(i*pageSize, (i*pageSize + surplus));
                  }else{
                      codeList = list.subList(i*pageSize, (i+1)*pageSize);
                  }
                  materialCouponCodeDao.batchInsert(codeList);
              }
          }
        
        Date endday = new Date();
        logger.info("save 接口 end 结束时间：" + df.format(endday));
        return baseOutput;
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
     * 时间格式转换 yyyy-MM-dd 转化成yyyy-MM-dd 00:00:00
     * @param date
     * @param pattern
     * @return
     */
    private Date dateStart(Date date, String pattern) {
        if (date == null) {
            throw new java.lang.IllegalArgumentException("timestamp null illegal");
        }
        pattern = (pattern == null || pattern.equals(""))?PATTERN_STANDARD19H:pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateNew = sdf.format(date);
        Date d = new Date();
        try {
            d = sdf.parse(dateNew);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
    
    /**
     * 时间格式转换 yyyy-MM-dd 转化成yyyy-MM-dd 23:59:59
     * @param dateStr
     * @param minute
     * @return
     */
    private Date dateEnd(Date dateStr,int minute) {
        Date afterDate = new Date(dateStart(dateStr, null).getTime()+(1000*minute));
        return afterDate;
    }
    
    /**
     * 时间格式转换 yyyy-MM-dd 转化成yyyy-MM-dd 00:00:00
     * @param date
     * @param pattern
     * @return
     */
    private String dateStartString(Date date, String pattern) {
        if (date == null) {
            throw new java.lang.IllegalArgumentException("timestamp null illegal");
        }
        pattern = (pattern == null || pattern.equals(""))?PATTERN_STANDARD19H:pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
    /**
     * 获取服务器文件优惠码
     * @param filesUrl
     * @param baseOutput
     * @return
     */
    private List<String> filesGetCode(List<String> fileNames, String filesUrl, BaseOutput baseOutput) {

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
                    baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                    baseOutput.setMsg("系统异常");
                }
            }
        }
        return codeList;
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
    private void getGenerationCode(JSONObject ruleObject, Integer stock_total, Long couponId, List<MaterialCouponCode> list, Date now){
        String codeType = ruleObject.getString("type_code");
        int length = ruleObject.getIntValue("length");
        List<String> codes = null;
        if(MaterialCouponSourceCodeTypeEnum.ALPHA.getCode().equals(codeType)){
            //字母组合
            codes = getAllLists(DATABASE_LETTER, length, stock_total);
        }else if(MaterialCouponSourceCodeTypeEnum.NUMBER.getCode().equals(codeType)){
            //数字组合    
            codes = getAllLists(DATABASE_NUMBER, length, stock_total);
        }else{
            //混合组合
            codes = getAllLists(DATABASE_BLEND, length, stock_total);
        }
        if(codes.size() > 0){
            for(String code : codes){
                String couponCode =  code;
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
     * 自有码
     * @param user_token
     * @param baseOutput
     * @param couponId
     * @param list
     * @param now
     */
    private void getOwnCode(List<String> fileNames, String user_token, BaseOutput baseOutput, Long couponId, List<MaterialCouponCode> list, Date now){
        
        String filesUrl = UPLOADED_FILE_PATH + user_token + SLASH;
        List<String> codeList = filesGetCode(fileNames, filesUrl, baseOutput);
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


    @Override
    public void task(Integer taskId) {
        
    }
}
