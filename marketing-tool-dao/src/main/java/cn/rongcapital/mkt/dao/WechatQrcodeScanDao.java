/*************************************************
 * @功能简述: DAO接口类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao;

import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.WechatQrcodeScan;

public interface WechatQrcodeScanDao extends BaseDao<WechatQrcodeScan>{
	
	//自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展
	 * 查询对象list;
	 * 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListBycustomMap(Map<String,Object> paramMap);
	
	//自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展
	 * 查询对象总数
	 * 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListCountBycustomMap(Map<String,Object> paramMap);
	
	/**
	 * 总扫码次数
	 * @param qrcodeId
	 * @return
	 */
	int getTotalScan(String qrcodeId);
	
	/**
	 * 总扫码人数
	 * @param qrcodeId
	 * @return
	 */
	int getTotalScanUser(String qrcodeId);
	
	/**
	 * 总扫码次数最大
	 * @param list
	 * @return
	 */
	Map<String, Object> getAmountScanMax(List<String> list);
	
	/**
	 * 总扫码人数最大值
	 * @param list
	 * @return
	 */
	Map<String, Object> getamountScanUserMax(List<String> list);

}