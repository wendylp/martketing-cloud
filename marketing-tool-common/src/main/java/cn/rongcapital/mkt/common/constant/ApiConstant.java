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
	public static final byte SEGMENT_PUBLISH_STATUS_ALL = 3;//全部细分
	
	public static final byte CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH = 0;//未发布
	public static final byte CAMPAIGN_PUBLISH_STATUS_PUBLISH = 1;//已发布
	public static final byte CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS = 2;//活动中
	public static final byte CAMPAIGN_PUBLISH_STATUS_FINISH = 3;//已结束
	public static final byte CAMPAIGN_PUBLISH_STATUS_ALL = 4;//全部活动
	
	public static final int INT_ZERO = 0;
	public static final int INT_ONE = 1;
	
	public static final int PAGE_START_INDEX_DEFAULT = 0;//默认分页起始index值
	public static final int PAGE_PAGE_SIZE_DEFAULT = 10;//默认每页记录数

	public static final String FILE_UPLOAD_URL = "mkt.service.file.upload";   //文件上传地址

	public static final int WECHAT_ASSET_SERVER_NUMBER = 0;
	public static final int WECHAT_ASSET_PERSONAL_NUMBER = 1;
	public static final int WECHAT_ASSET_SUBSCRIPTION_NUMBER=2;
	
    public static final String DATE_FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
	
	public static final byte TAG_TYPE_SEGMENT = 0;// 细分
	public static final byte TAG_TYPE_ACTIVITY = 1;// 活动
	public static final byte TAG_TYPE_CONTACT = 2;// 联系人

	public static final byte CAMPAIGN_SWITCH_SWITCH_YES = 0;//是分支
	public static final byte CAMPAIGN_SWITCH_SWITCH_NO = 1;//非分支
	public static final byte CAMPAIGN_SWITCH_ENDS = 2;//多分支类型
	
	public static final byte CAMPAIGN_PARENT_NODE_PTYPE = -1;//父节点的ptype列值

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
	
	public static final byte TASK_STATUS_VALID = 0;//任务可运行
	public static final byte TASK_STATUS_INVALID = 1;//任务不可运行
	
	public static final byte CAMPAIGN_DECISION_TAG_RULE_MATCH_ALL = 0;//全部匹配
	public static final byte CAMPAIGN_DECISION_TAG_RULE_MATCH_ONE = 1;//匹配其一
	public static final byte CAMPAIGN_DECISION_TAG_RULE_MATCH_TWO_MORE = 2;//匹配二个及以上
	
	public static final long TASK_SCAN_INTERVAL_MILLS = 1000*3; //扫描任务表的时间间隔:毫秒
	public static final long TASK_DO_INTERVAL_MILLS = 1000*3; //扫描执行列表的时间间隔:毫秒

	//时间触发节点
	public static final String TASK_NAME_CAMPAIGN_TRUGGER_TIME = "campaignTriggerTimeTask";
	//目标人群节点
	public static final String TASK_NAME_CAMPAIGN_AUDIENCE_TARGET = "campaignAudienceTargetTask";
	//微信是否发送节点
	public static final String TASK_NAME_CAMPAIGN_DECISION_WECHAT_SENT = "campaignDecisionWechatSentTask";
	//微信是否阅读节点
	public static final String TASK_NAME_CAMPAIGN_DECISION_WECHAT_READ = "campaignDecisionWechatReadTask";
	//微信是否转发节点
	public static final String TASK_NAME_CAMPAIGN_DECISION_WECHAT_FORWARD = "campaignDecisionWechatForwardTask";
	//是否关注公众号
	public static final String TASK_NAME_CAMPAIGN_DECISION_WECHAT_SUBSCRIBE = "campaignDecisionWechatSubscribeTask";
	//是否个人号好友
	public static final String TASK_NAME_CAMPAIGN_DECISION_WECHAT_PRV_FRIEND = "campaignDecisionWechatPrivFriendTask";
	//标签判断
	public static final String TASK_NAME_CAMPAIGN_DECISION_TAG = "campaignDecisionTagTask";
	//公众号发送微信图文
	public static final String TASK_NAME_CAMPAIGN_ACTION_PUBWECHAT_SEND_H5 = "campaignActionPubWechatSendH5Task";
	
	
	public static final Integer DATA_PARTY_MD_TYPE_WECHAT = 8;//data_party表:微信类型数据

	public static final String DL_PUB_GRANT_API = "ruixue.hfive.mkt.pub.grant";
	public static final String DL_PUB_LIST_API = "ruixue.hfive.mkt.pub.list";
	public static final String DL_PUB_FANSLIST_API = "ruixue.hfive.mkt.pub.fanslist";
    public static final String DL_PERSONAL_LIST = "ruixue.hfive.mkt.personal.list";
	public static final String DL_PERSONAL_CONTACTLIST = "ruixue.hfive.mkt.personal.contactlist";
	public static final String DL_PERSONAL_GROUPLIST = "ruixue.hfive.mkt.personal.grouplist";
    public static final String DL_WUWEN_SYNC = "ruixue.hfive.mkt.wtuwen.sync";
	public static final String DL_WUWEN_HOST = "ruixue.hfive.mkt.wtuwen.convert";
	public static final String DL_PERSONAL_ISONLINE = "ruixue.hfive.mkt.personal.isonline";
	public static final String DL_IS_PUBLIC_GRANTED = "ruixue.hfive.mkt.grant.status";
	public static final String DL_API_PARAM_METHOD = "method";
	public static final String DL_API_PARAM_PUB_GRANT_CALLBACK_KEY = "callback";
	public static final String DL_API_PARAM_PUB_GRANT_CALLBACK_VALUE = "mkt.data.inbound.wechat.public.auth.callback";
	
	public static final String DL_PUB_SEND_API_PATH = "/auth-template/api/?method=ruixue.hfive.mkt.pub.send";
	public static final String DL_PUB_ISSENT_API_PATH = "/auth-template/api/?method=ruixue.hfive.mkt.wtuwen.sent";
	public static final String DL_PUB_ISREAD_API_PATH = "/auth-template/api/?method=ruixue.hfive.mkt.wtuwen.viewed";

    public static final String DOWNLOAD_TEMPLATE_FILE_DIR = "//rc//templeteFiles";
	public static final String DOWNLOAD_BASE_DIR = "//rc//downloads/";
}
