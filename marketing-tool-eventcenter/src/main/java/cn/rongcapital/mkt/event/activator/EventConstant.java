/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年3月2日 
 * @date(最后修改日期)：2017年3月2日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.activator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EventConstant {

    public static final String EVENT_HEADER="eventCenter.eventType";
    
    public static final String EVENT_SYSTEM="SYSTEM";
    
    public static final String EVENT_MK="MARKETING";
    
    public static final String EVENT_STREAM="STREAM";
    
    //public static final String KAFKA_SRM_TOPIC="streamtomc";
    
    
    public static List<String> formatMessage(final Map message)
    {
        Set<Map.Entry> set = message.entrySet();
        List<String> msinfo=new ArrayList<String>();
        for (Map.Entry entry : set) {
            String topic = (String) entry.getKey();
            ConcurrentHashMap<Integer, List<byte[]>> messages =
                    (ConcurrentHashMap<Integer, List<byte[]>>) entry.getValue();
            Collection<List<byte[]>> values = messages.values();
            for (Iterator<List<byte[]>> iterator = values.iterator(); iterator.hasNext();) {
                List<byte[]> list = iterator.next();
                for (byte[] object : list) {
                    msinfo.add(new String(object));
                }
            }
        }

        return msinfo;

    }
    
    
    /**
     * 获取某个字符串中某部分
     * @param string
     * @param leftFlag
     * @param rightFlag
     * @return Param
     */
    public static String getParam(String string,String leftFlag,String rightFlag)
    {
        String param = "";
        while (string.indexOf(rightFlag) < string.indexOf(leftFlag))
            string = string.substring(string.indexOf(rightFlag) + 1);

        if (string.indexOf(leftFlag) >= 0 && string.indexOf(rightFlag) >= 0)
        {
            int pos1 = string.indexOf(leftFlag);
            int pos2 = string.indexOf(rightFlag);
            param = string.substring(pos1 + leftFlag.length(), pos2);
        }

        return param;
    }
    
    
}


