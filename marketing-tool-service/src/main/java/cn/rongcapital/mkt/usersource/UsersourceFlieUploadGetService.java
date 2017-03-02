package cn.rongcapital.mkt.usersource;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface UsersourceFlieUploadGetService {

	BaseOutput getUsersourceUploadUrlGet();

	BaseOutput uploadFile(MultipartFormDataInput input);

}
