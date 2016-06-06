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
import cn.rongcapital.mkt.po.ImportTemplate;

public interface ImportTemplateDao extends BaseDao<ImportTemplate> {

    // 自定义扩展
    /**
     * 父类方法无法满足需求时使用,需在mapper.xml中扩展 查询对象list; 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
     * 
     * @param paramMap
     * @return list
     */
    // List<T> selectListBycustomMap(Map<String,Object> paramMap);

    // 自定义扩展
    /**
     * 父类方法无法满足需求时使用,需在mapper.xml中扩展 查询对象总数 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
     * 
     * @param paramMap
     * @return list
     */
    // List<T> selectListCountBycustomMap(Map<String,Object> paramMap);

    /**
     * 获取文件模板下载列表
     * 
     * @return list
     */
    List<Map<String, Object>> selectTemplateList();

    /**
     * mkt.data.view.list.get
     * 
     * @功能简述 : 查询自定义视图字段列表
     * @author nianjun
     * @return List
     */
    List<ImportTemplate> selectViewListByTemplType(ImportTemplate paramImportTemplate);

    /**
     * 保存自定义视图字段
     * 
     * @功能简述 : 查询自定义视图字段列表
     * @author nianjun
     * @return Integer
     */
    Integer updateByTemplNameandFieldName(ImportTemplate paramImportTemplate);

}
