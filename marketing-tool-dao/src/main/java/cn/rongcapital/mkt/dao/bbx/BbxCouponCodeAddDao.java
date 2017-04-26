/*************************************************
 * @功能及特点的描述简述: 贝贝熊优惠码同步添加Service Implement
 *
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: xie.xiaoliang
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-04-11
 * @date(最后修改日期)：2017-04-11
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.dao.bbx;

import cn.rongcapital.mkt.bbx.po.BbxCouponCodeAdd;
import cn.rongcapital.mkt.dao.base.BaseDao;

import java.util.List;
import java.util.Map;

public interface BbxCouponCodeAddDao extends BaseDao<BbxCouponCodeAdd>{
	
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
	 * 高性能的分页查询，需要制定limit的其实ID
	 * @param param
	 * @return
	 */
	List<BbxCouponCodeAdd> selectListByMinId(BbxCouponCodeAdd param);

	/**
	 * 批量插入
	 * @param list
	 */
	int batchInsert(List<BbxCouponCodeAdd> list);
	
	Map<String,Object> selectCampaignSmsItemByCouponId(long couponCodeId);
}