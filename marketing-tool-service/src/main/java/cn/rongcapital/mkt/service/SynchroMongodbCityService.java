/**
 * 描述：把mongodb中data_paty表市的值插入到tag_list
 * 
 * @author shuiyangyang
 * @date 2016.09.28
 */
package cn.rongcapital.mkt.service;


public interface SynchroMongodbCityService {
	
	/**
	 * 根据mid把市的值写入到tag_list
	 * 
	 * @param mid 
	 * @param CITY 传入字符串"市"
	 * @author shuiyangyang
	 * @date 2016.09.28
	 */
	public void synchroMongodbCity(Integer mid);
}
