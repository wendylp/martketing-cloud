/*************************************************
 * @功能简述: 修改类接口返回通用VO类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.vo;

public class UpdateResult extends BaseOutput {
	
	UpdateResult(){}
	
    public UpdateResult(int code, String msg) {
	    super(code,msg,0,null);
	}
  
}

