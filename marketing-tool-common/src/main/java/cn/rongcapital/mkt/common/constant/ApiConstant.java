/*************************************************
 * @功能简述: API常量类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.common.constant;

public class ApiConstant {

	public static final String API_METHOD = "method";//api的method参数
	public static final String API_PATH = "/api";//api的路径
	public static final byte TABLE_DATA_STATUS_VALID = 0;//数据正常
	public static final byte TABLE_DATA_STATUS_INVALID = 1;//数据已被逻辑删除
	
	public static final byte SEGMENT_PUBLISH_STATUS_NOT_PUBLISH = 0;//未发布
	public static final byte SEGMENT_PUBLISH_STATUS_PUBLISH = 1;//已发布
	public static final byte SEGMENT_PUBLISH_STATUS_IN_CAMPAIGN = 2;//活动中
	public static final byte  SEGMENT_PUBLISH_STATUS_ALL = 3;//全部活动
	
	public static final int INT_ZERO = 0;
}
