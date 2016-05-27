package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.UserDao;
import cn.rongcapital.mkt.po.User;
import cn.rongcapital.mkt.service.ModifyPasswdService;
import cn.rongcapital.mkt.vo.ModifyInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.SecurityContext;

/**
 * Created by Yunfeng on 2016-5-26.
 */
@Service
public class ModifyPasswdServiceImpl implements ModifyPasswdService{


    @Autowired
    UserDao userDao;

    @Override
    public Object modifyPasswd(ModifyInput input, SecurityContext securityContext) {

        User user = constructUser(input);
        if(userDao.validateLoginInput(user) == 0){
            return null;
        }

        user.setPasswd(input.getNewPasswd());
        userDao.updatePasswd(user);
        return null;
    }

    private User constructUser(ModifyInput input) {
        User user = new User();
        user.setUserId(input.getUser_id());
        user.setPasswd(input.getOldPasswd());
        return user;
    }
}
