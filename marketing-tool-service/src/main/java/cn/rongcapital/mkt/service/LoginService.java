package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.LoginInput;

import javax.ws.rs.core.SecurityContext;

/**
 * Created by Yunfeng on 2016-5-25.
 */
public interface LoginService {

    public Object validateLoginPassword(LoginInput loginInput, SecurityContext securityContext);

}
