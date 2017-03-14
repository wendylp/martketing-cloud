package cn.rongcapital.mkt.usersource;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import cn.rongcapital.mkt.vo.BaseOutput;

/*************************************************
 * @功能及特点的描述简述: 用户来源分类Service
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: guozhenchao
 * @version: 版本v1.8
 * @date(创建、开发日期)：2017-03-013
 * @date(最后修改日期)：2017-03-03
 * @复审人：
 *************************************************/
public interface UsersourceFlieUploadGetService {

	BaseOutput getUsersourceUploadUrlGet();

	BaseOutput uploadFile(MultipartFormDataInput input);

	BaseOutput importUsersourceDate(String fileId);

	BaseOutput usersourceCheck();

}
