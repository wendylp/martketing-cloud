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
	BIZ_ERROR_SEGMENTATION_IN_CAMPAIGN(3003,"can not update,segmentation is in campaign"),//细分处在活动中，无法编辑
	BIZ_ERROR_CANPAIGN_IN_PROGRESS(3004,"can not update,campaign is in progress"),//活动进行中,无法编辑
	BIZ_ERROR_CANPAIGN_FINISH(3005,"can not update,campaign is finish"),//活动已结束,无法编辑
	
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
