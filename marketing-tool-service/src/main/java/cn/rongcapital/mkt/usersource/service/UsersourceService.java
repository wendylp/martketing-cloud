package cn.rongcapital.mkt.usersource.service;

import cn.rongcapital.mkt.usersource.vo.in.UsersourceAvailableIn;
import cn.rongcapital.mkt.usersource.vo.in.UsersourceIn;
import cn.rongcapital.mkt.vo.BaseOutput;

/*************************************************
 * @功能及特点的描述简述: 用户来源Service
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 单璟琦
 * @version: 版本v1.8
 * @date(创建、开发日期)：2017-03-01 
 * @date(最后修改日期)：2017-03-03 
 * @复审人：
 *************************************************/
public interface UsersourceService {

	/**
     * 添加用户来源
     * @param UsersourceIn
     */
	BaseOutput saveUsersource(UsersourceIn in);
	
	/**
     * 用户来源列表
     * @param Long id
     * @param Integer index
     * @param Integer size
     */
	BaseOutput getUsersourceList(Long id, Integer index, Integer size);
	
	/**
     * 禁用/启用来源
     * @param Long id
     * @param Long available
     */
	BaseOutput usersourceAvailable(UsersourceAvailableIn in);

}
