package cn.rongcapital.mkt.usersource.service;

import cn.rongcapital.mkt.usersource.vo.in.UsersourceClassificationIn;
import cn.rongcapital.mkt.vo.BaseOutput;

/*************************************************
 * @功能及特点的描述简述: 用户来源分类Service
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 单璟琦
 * @version: 版本v1.8
 * @date(创建、开发日期)：2017-03-01 
 * @date(最后修改日期)：2017-03-01 
 * @复审人：
 *************************************************/
public interface UsersourceClassificationService {

	BaseOutput saveUsersourceClassification(UsersourceClassificationIn in);
	
	BaseOutput classificationList();
	
	
}
