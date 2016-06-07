/*************************************************
 * @功能�?�?: API常量�?
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世�?
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审�?: 
*************************************************/

package cn.rongcapital.mkt.common.constant;

public class ApiConstant {

	public static final String API_METHOD = "method";//api的method参数
	public static final String API_PATH = "/api";//api的路�?
	public static final byte TABLE_DATA_STATUS_VALID = 0;//数据正常
	public static final byte TABLE_DATA_STATUS_INVALID = 1;//数据已被逻辑删除
	
	public static final byte SEGMENT_PUBLISH_STATUS_NOT_PUBLISH = 0;//未发�?
	public static final byte SEGMENT_PUBLISH_STATUS_PUBLISH = 1;//已发�?
	public static final byte SEGMENT_PUBLISH_STATUS_IN_CAMPAIGN = 2;//活动�?
	public static final byte  SEGMENT_PUBLISH_STATUS_ALL = 3;//全部活动
	
	public static final int INT_ZERO = 0;
	public static final int INT_ONE = 1;
	
	public static final int PAGE_START_INDEX_DEFAULT = 0;//默认分页起始index�?
	public static final int PAGE_PAGE_SIZE_DEFAULT = 10;//默认每页记录�?

	public static final String FILE_UPLOAD_URL = "mkt.service.file.upload";   //文件上传地址

	public static final int WECHAT_ASSET_SERVER_NUMBER = 0;
	public static final int WECHAT_ASSET_PERSONAL_NUMBER = 1;
	public static final int WECHAT_ASSET_SUBSCRIPTION_NUMBER=2;
	
	public static final String DATE_FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	
	public static final byte TAG_TYPE_SEGMENT = 0;// 细分
	public static final byte TAG_TYPE_ACTIVITY = 1;// 活动
	public static final byte TAG_TYPE_CONTACT = 2;// 联系�?
}
