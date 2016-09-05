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

	public static final String CONTENT_TYPE_JSON = "application/json";
	
	public static final String API_METHOD = "method";//api的method参数
	public static final String API_PATH = "/api";//api的路径
	public static final String API_PATH_APPID = "/api/wx00f7d56d549f82ce";//api的路径
	public static final byte TABLE_DATA_STATUS_VALID = 0;//数据正常
	public static final byte TABLE_DATA_STATUS_INVALID = 1;//数据已被逻辑删除
	
	public static final byte SEGMENT_PUBLISH_STATUS_NOT_PUBLISH = 0;//未发布
	public static final byte SEGMENT_PUBLISH_STATUS_PUBLISH = 1;//已发布
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
	public static final byte CAMPAIGN_ITEM_TRIGGER_MANUAL = 1;//手动触发

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
	
	public static final int SEGMENTATION_GROUP_MEMBER_MOST_COUNT = 3;//细分每个分组内标签的最多数量

	//时间触发节点
	public static final String TASK_NAME_CAMPAIGN_TRUGGER_TIME = "campaignTriggerTimeTask";
	//目标人群节点
	public static final String TASK_NAME_CAMPAIGN_AUDIENCE_TARGET = "campaignAudienceTargetTask";
	//微信是否发送节点(已去掉)
//	public static final String TASK_NAME_CAMPAIGN_DECISION_WECHAT_SENT = "campaignDecisionWechatSentTask";
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
	//发送个人号消息
	public static final String TASK_NAME_CAMPAIGN_ACTION_WECHAT_PRV_SEND_INFO = "campaignActionPrvWechatSendInfoTask";
	//公众号发送H5图文:个人号暂时不支持发送H5图文
	public static final String TASK_NAME_CAMPAIGN_ACTION_WECHAT_SEND_H5 = "campaignActionWechatSendH5Task";
	//等待
	public static final String TASK_NAME_CAMPAIGN_ACTION_WAIT = "campaignActionWaitTask";
	//保存当前人群
    public static final String TASK_NAME_CAMPAIGN_ACTION_SAVE_AUDIENCE = "campaignActionSaveAudienceTask";
	//设置标签
    public static final String TASK_NAME_CAMPAIGN_ACTION_SET_TAG = "campaignActionSetTagTask";
    //联系人属性比较
    public static final String TASK_NAME_CAMPAIGN_DECISION_PROP_COMPARE = "campaignDecisionPropTask";
	
	public static final int DATA_PARTY_MD_TYPE_WECHAT = 8;//data_party表:微信类型数据
	public static final int DATA_PARTY_MD_TYPE_POPULATION = 1; //data_party表:人口属性数据
	/**
	 * campaign_decision_wechat_forward表:forward_times
	 */
	public static final byte CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_NOLIMIT = 0;//不限
	public static final byte CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_ONE = 1;//超1人次
	public static final byte CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_TEN = 2;//超10人次
	public static final byte CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_ONE_HUNDRED = 3;//超100人次
	public static final byte CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_FIVE_HUNDRED = 4;//超500人次


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
	public static final Integer FANS_LIST_SYNC_SIZE = 100;
	public static final Integer IMGTEXT_SYNC_SIZE = 100;

	public static final String DL_PUB_SEND_API_PATH = "/auth-template/api/?method=ruixue.hfive.mkt.pub.send&pid=";
	public static final String DL_PUB_ISSENT_API_PATH = "/auth-template/api/?method=ruixue.hfive.mkt.wtuwen.sent&pid=";
	public static final String DL_PUB_ISREAD_API_PATH = "/auth-template/api/?method=ruixue.hfive.mkt.wtuwen.viewed&pid=";
	public static final String DL_PUB_ISFORWARD_API_PATH = "/auth-template/api/?method=ruixue.hfive.mkt.wtuwen.shared&pid=";
	public static final String DL_PRV_SEND_API_PATH = "/auth-template/api/?method=ruixue.hfive.mkt.personal.send&pid=";
	
	
    public static final String DOWNLOAD_TEMPLATE_FILE_DIR = "//rc/data/templeteFiles";
	public static final String DOWNLOAD_BASE_DIR = "/rc/data/downloads/";
	
	public static final String UPLOAD_BASE_DIR = "//rc/data/uploads/";
	
	public static final String AUDIENCE_SOUCE_NAME_CAMPAIGN = "营销活动";
	public static final Byte WECHAT_IMGTEXT_TYPE = 0;

	public static String IMPORT_FILE_SOURCE = "文件接入";
	
	//调用后只执行1次的任务,该任务不存入task_schedule表，只在task_run_log表里记录任务日志
	public static final int MANUAL_RUN_ONCE_TASK_ID = 0;

	//自定义标签状态标识
	public static final Byte CUSTOM_TAG_INVALIDATE = 1;
	public static final Byte CUSTOM_TAG_VALIDATE = 0;

	//自定义标签元数据状态标识
	public static final Byte CUSTOM_TAG_ORIGINAL_DATA_MAP_VALIDATE = 0;
	public static final Byte CUSTOM_TAG_ORIGINAL_DATA_MAP_INVALIDATE = 1;

	//注册表中的注册类型 0代表微信注册类型 1代表Bas注册类型
	public static final Byte REGISTER_WECHAT_TYPE = 0;
	public static final Byte REGISTER_BAS_TYPE = 1;

	//注册BAS所需要的URL
	public static final String BAS_HOST = "api.bas.ruixuesoft.com";
    public static final String BAS_URL = "/bas/services/usm/company/registerCompany?terminalType=PC";

    public static final int MONGO_NODEAUDIENCE_SENTSTATUS_H5_SENT = 1;

	// 国籍
    public static final String NATIONALITY_CHINA = "中国";
    public static final String NATIONALITY_FOREIGN = "外国";

	public static final String CAMPAIGN_CONTENT_WECHAT = "微信图文";
	public static final String CAMPAIGN_CONTENT_H5 = "H5活动";
	public static final String CAMPAIGN_CONTENT_PRIVATE_TEXT = "个人号消息";

    public static final String WECHAT_TYPE_PUB = "[公众号]";
    public static final String WECHAT_TYPE_SUB = "[订阅号]";
    public static final String WECHAT_TYPE_PRI = "[个人号]";

	public static final Byte DATA_PARTY_TAG_RULE_TYPE_COMMON = 0;
	public static final Byte DATA_PARTY_TAG_RULE_TYPE_JS = 1;

	
	
	/**
	 * 微信公众号ID
	 */
	public static final String APPID = "wx00f7d56d549f82ce";	
//	public static final String APPID = "wxa3f1b781590bb2c5";
	/**
	 * 微信公众号appsecret
	 */

	public static final String SECRET = "4fcc525e82dd03422ea139733d318fc2";
//	public static final String SECRET = "bdf1650396a6c9059835b5777df1b77a";
//	public static final String SECRET = "60ba94dd8d1590c896edbc5f0f3d5e5e";

	public static final String upload_img_path_large = "/rc/data/downloads/large/";
	public static final String upload_img_path_middle = "/rc/data/downloads/middle/";
	public static final String upload_img_path_small = "/rc/data/downloads/small/";
		
	public static final String return_img_path_large = "large/";
	public static final String return_img_path_middle = "middle/";
	public static final String return_img_path_small = "small/";
	
	
	public static final String upload_img_path = "D:\\soft\\";
	
	public static String component_verify_ticket = "ticket@@@Pqd1HooYMLuhKeSIUPh5SjZC43_g7JYLtyVyHN6fDP3WQ9lSRLSZMg_juXv8E9uoE-wKb0LVHiTwqPHVxSYxww";	
	
/*	public static App getComponentAccessToken() {
		App app = new App(ApiConstant.APPID,ApiConstant.SECRET);
		app.setComponentTicket(ApiConstant.component_verify_ticket);
		WxComponentServerApi.accessToken(app);
		return app;
	}*/
	
}