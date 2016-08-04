package cn.rongcapital.mkt.dao;

import java.util.List;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.KeyidMapBlock;

public interface KeyidMapBlockDao extends BaseDao<KeyidMapBlock>{
	/**
	 * @功能简述: 查询对象list,只要不为NULL与空则为条件,属性值之间and连接
	 * 
	 * @return: List<T>
	 */
	List<KeyidMapBlock> selectKeyidMapBlockList();

	/**
	 * @功能简述: 根据seq获取相应的field字段
	 * @Param:keyidMapBlock
	 * @return: KeyidMapBlock
	 */
	KeyidMapBlock selectKeyIdMapBolckBySeq(KeyidMapBlock keyidMapBlock);
}