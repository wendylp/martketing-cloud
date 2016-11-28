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

import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.TagValueCount;

import java.util.List;

public interface TagValueCountDao extends BaseDao<TagValueCount>{
	
	//清空存量数据
	int clearStockData();
	
	//获取标签值id
	String selectTagValueId(@Param("tagId")String tagId,@Param("tagValue")String tagValue);

	//模糊搜索标签值和标签值Id
    List<TagValueCount> selectTagValueCountListByKeyWord(TagValueCount paramTagValueCount);
    
    /**
     * 模糊搜索标签值和标签名
     * @param tagValue
     * @return
     * @Date 2016-11-10
     * @author shuiyangyang
     */
    List<TagValueCount> selectFuzzyTagValue(TagValueCount tagValueCount);
    
    /**
     * 模糊搜索标签值和标签名    统计总数
     * @param tagValue
     * @return
     * @Date 2016-11-11
     * @author shuiyangyang
     */
    int selectFuzzyTagValueCount(TagValueCount tagValueCount);
}