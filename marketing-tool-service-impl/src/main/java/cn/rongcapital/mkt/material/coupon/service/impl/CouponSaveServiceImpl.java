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
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.CouponSaveService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CouponInfoIn;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;

@Service
public class CouponSaveServiceImpl implements CouponSaveService{

    @Autowired
    private MaterialCouponDao materialCouponDao;
    
    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;
    
    public static final String PATTERN_STANDARD19H = "yyyy-MM-dd HH:mm:ss";

    private static final String[] DATABASE_LETTER = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    
    private static final String[] DATABASE_NUMBER = {"0","1","2","3","4","5","6","7","8","9"};
    
    private static final String[] DATABASE_BLEND = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};
    
    public final static String UPLOADED_FILE_PATH = "\\rc\\data\\uploadFiles\\code\\";
    
    public final static String SLASH = File.separator;
    
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseOutput save(CouponInfoIn couponInfo,String user_token) {
        
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
        JSONObject ruleObject = JSONObject.parseObject(rule);
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
                getUniversalCode(ruleObject, stock_total, coupon.getId(), list, now);
            }else if(SourceCode.equals(CouponCodeType.GENERATIONCODE.getCode())){
                //平台生成码
                getGenerationCode(ruleObject, stock_total, coupon.getId(), list, now);
            }else{
                //自有码
                getOwnCode(user_token, baseOutput, coupon.getId(), list, now);
            }
        }else{
            //更新操作
            MaterialCoupon coupon = materialCouponDao.selectOneCoupon(id);
            if(coupon == null){
                baseOutput.setCode(ApiErrorCode.DB_ERROR.getCode());
                baseOutput.setMsg(ApiErrorCode.DB_ERROR.getMsg());
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
                getUniversalCode(ruleObject, stock_total, coupon.getId(), list, now);
            }else if(SourceCode.equals(CouponCodeType.GENERATIONCODE.getCode())){
                //平台生成码
                getGenerationCode(ruleObject, stock_total, coupon.getId(), list, now);
            }else{
                //自有码
                getOwnCode(user_token, baseOutput, coupon.getId(), list, now);
            }
        }
        //保存优惠码
        if(list.size() > 0){
            materialCouponCodeDao.batchInsert(list);
        };
        return baseOutput;
    }
    
    /**
     * 生成码
     * @param elements
     * @param lengthOfList
     * @return
     */
    public List<String> getAllLists(String[] elements, int lengthOfList, int size) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
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
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        return list;
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
        Date afterDate = new Date(dateStr.getTime()+(1000*minute));
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
    private List<String> filesGetCode(String filesUrl, BaseOutput baseOutput){

        File file = new File(filesUrl);
        File[] tempList = file.listFiles();
        List<String> codeList = new ArrayList<String>();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile() && (tempList[i].getName().endsWith(".xls")||tempList[i].getName().endsWith(".xlsx"))) {
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
                            if(cellType == 1){
                                if(!codeList.contains(dataColumnCell.getStringCellValue())){
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
    private void getOwnCode(String user_token, BaseOutput baseOutput, Long couponId, List<MaterialCouponCode> list, Date now){
        String filesUrl = UPLOADED_FILE_PATH + user_token + SLASH;
        List<String> codeList = filesGetCode(filesUrl, baseOutput);
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
//    public static void main(String[] args) {
//        Date now = new Date();
//        SimpleDateFormat startFormatter = new SimpleDateFormat("yyyy-MM-dd");
//        String a1 = startFormatter.format(now);
//        System.out.println(a1);
//        SimpleDateFormat startFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//           Date date = startFormatter1.parse(a1);
//           System.out.println(date);
//           System.out.println(dateStartString(date, null));
//           System.out.println(dateStartString(dateEnd(dateStart(date, null), 23*60*60+59*60+59), null));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}
