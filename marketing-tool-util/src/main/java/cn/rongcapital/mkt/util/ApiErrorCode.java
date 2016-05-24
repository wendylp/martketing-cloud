package cn.rongcapital.mkt.util;

public enum ApiErrorCode {
	
	/**
	 * error code and msg
	 */
	PARAMETER_ERROR(101,"parameter error");
	
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
