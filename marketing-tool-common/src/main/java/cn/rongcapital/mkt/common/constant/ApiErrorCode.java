/*************************************************
 * @功能简述: API错误码类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.common.constant;

public enum ApiErrorCode {
	
	SUCCESS(0,"success"),
	
	PARAMETER_ERROR(1001,"parameter error"),//1001-1999,参数相关错误码
	VALIDATE_ERROR(1002,"validation failed"),//校验失败
	
	DB_ERROR(2001,"DB error"),//2001-2999,数据库相关的错误码
	DB_ERROR_TABLE_DATA_NOT_EXIST(2002,"table data not exist"),//数据在表里不存在
	
	BIZ_ERROR(3001,"business error"),//3001-3999,业务逻辑相关错误
	BIZ_ERROR_SEGMENTATION_IN_CAMPAIGN(3003,"egmentation is in campaign"),//细分处在活动中
    BIZ_ERROR_SEGMENTATION_NOT_PUBLISH(3008,"egmentation not published"),//细分未发布

    BIZ_ERROR_CANPAIGN_NOT_PUBLISH(3007,"campaign not published"),//活动没有发布
    BIZ_ERROR_CANPAIGN_PUBLISHED(3011,"campaign is published"),//活动已发布
    BIZ_ERROR_CANPAIGN_IN_PROGRESS(3004,"campaign is in progress"),//活动进行中
    BIZ_ERROR_CANPAIGN_FINISH(3005,"ccampaign is finish"),//活动已结束

	BIZ_ERROR_CANPAIGN_CAN_NOT_MANUAL_START(3006,"campaign can not manual start"),//活动无法手动开启
	BIZ_ERROR_CANPAIGN_CAN_NOT_START(3009,"campaign can not start"),//活动无法开启
	BIZ_ERROR_CONTACTINFO_MOBILE(3010,"can not get the mobile"),//

	
	SYSTEM_ERROR(9001,"system error");//9001-9999,系统错误码
	
    private int code;
     
    private String msg;
     
    private ApiErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
     
    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
    
}
