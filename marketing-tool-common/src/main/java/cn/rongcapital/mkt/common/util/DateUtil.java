package cn.rongcapital.mkt.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Yunfeng on 2016-6-1.
 */
public class DateUtil {
	
	/**
     * 将字符串转化为时间
     * 
     * @param dateString
     * @param format
     * @return
     */
    public static Date getDateFromString(String dateString, String format) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        Date result = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            result = sdf.parse(dateString);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}
