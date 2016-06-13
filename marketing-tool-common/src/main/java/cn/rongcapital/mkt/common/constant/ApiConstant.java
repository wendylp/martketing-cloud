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
	public static final byte SEGMENT_PUBLISH_STATUS_ALL = 3;//全部活动
	
	public static final int INT_ZERO = 0;
	public static final int INT_ONE = 1;
	
	public static final int PAGE_START_INDEX_DEFAULT = 0;//默认分页起始index值
	public static final int PAGE_PAGE_SIZE_DEFAULT = 10;//默认每页记录数

	public static final String FILE_UPLOAD_URL = "mkt.service.file.upload";   //文件上传地址

	public static final int WECHAT_ASSET_SERVER_NUMBER = 0;
	public static final int WECHAT_ASSET_PERSONAL_NUMBER = 1;
	public static final int WECHAT_ASSET_SUBSCRIPTION_NUMBER=2;
	
	public static final String DATE_FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	
	public static final byte TAG_TYPE_SEGMENT = 0;// 细分
	public static final byte TAG_TYPE_ACTIVITY = 1;// 活动
	public static final byte TAG_TYPE_CONTACT = 2;// 联系人

	public static final byte CAMPAIGN_SWITCH_SWITCH = 0;//判断类型
	public static final byte CAMPAIGN_SWITCH_ENDS = 1;//多分支类型
	
	public static final byte CAMPAIGN_NODE_TRIGGER = 0;//触发
	public static final byte CAMPAIGN_NODE_AUDIENCE = 1;//受众
	public static final byte CAMPAIGN_NODE_DECISION = 2;//决策
	public static final byte CAMPAIGN_NODE_ACTION = 3;//行动
	
	public static final byte CAMPAIGN_ITEM_TRIGGER_TIMMER = 0;//时间触发
	public static final byte CAMPAIGN_ITEM_TRIGGER_EVENT = 1;//事件触发
	public static final byte CAMPAIGN_ITEM_TRIGGER_MANUAL = 2;//手动触发
	
	public static final byte CAMPAIGN_ITEM_AUDIENCE_TARGET = 0;//目标人群
	public static final byte CAMPAIGN_ITEM_AUDIENCE_EVENT = 1;//事件人群
	
	public static final byte CAMPAIGN_ITEM_DECISION_PROP_COMPARE = 0;//联系人属性比较
	public static final byte CAMPAIGN_ITEM_DECISION_WECHAT_SENT = 1;//微信图文是否发送
	public static final byte CAMPAIGN_ITEM_DECISION_WECHAT_READ = 2;//微信图文是否阅读
	public static final byte CAMPAIGN_ITEM_DECISION_WECHAT_FORWARD = 3;//微信图文是否转发
	public static final byte CAMPAIGN_ITEM_DECISION_IS_SUBSCRIBE = 4;//是否订阅公众号
	public static final byte CAMPAIGN_ITEM_DECISION_IS_PRIVT_FRIEND = 5;//是否个人号好友
	public static final byte CAMPAIGN_ITEM_DECISION_TAG = 6;//标签判断
	
	public static final byte CAMPAIGN_ITEM_ACTION_WAIT = 0;//等待
	public static final byte CAMPAIGN_ITEM_ACTION_SAVE_AUDIENCE = 1;//保存当前人群
	public static final byte CAMPAIGN_ITEM_ACTION_SET_TAG = 2;//设置标签
	public static final byte CAMPAIGN_ITEM_ACTION_ADD_CAMPAIGN = 3;//添加到其它活动
	public static final byte CAMPAIGN_ITEM_ACTION_MOVE_CAMPAIGN = 4;//转移到其它活动
	public static final byte CAMPAIGN_ITEM_ACTION_SEND_WECHAT_H5 = 5;//发送微信图文
	public static final byte CAMPAIGN_ITEM_ACTION_SEND_H5 = 6;//发送H5活动
	public static final byte CAMPAIGN_ITEM_ACTION_SEND_PRVT_INFO = 7;//发送个人号信息
	
}
