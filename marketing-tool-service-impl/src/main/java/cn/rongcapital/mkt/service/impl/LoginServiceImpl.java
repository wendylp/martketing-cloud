package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.RoleDao;
import cn.rongcapital.mkt.dao.RoleResourceMapDao;
import cn.rongcapital.mkt.dao.UserDao;
import cn.rongcapital.mkt.po.User;
import cn.rongcapital.mkt.service.LoginService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.LoginInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-5-25.
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    RoleResourceMapDao roleResourceMapDao;

    @Override
    public Object validateLoginPassword(LoginInput loginInput, SecurityContext securityContext) {
        //利用LoginInput对象构建User对象
        User user = new User();
        user.setUserId(loginInput.getUserId());
        user.setPasswd(loginInput.getPassword());
        if (validatePasswd(user)) return null;

        long roleId = userDao.getRoleId(user);
        String userName = userDao.getUserName(user);
        String roleName = roleDao.getRoleName(roleId);
        List<Long> resourceIds = roleResourceMapDao.selectResourceIds(roleId);
        StringBuilder builder = new StringBuilder("");
        for(long resourceId : resourceIds){
            builder.append(resourceId);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length()-1);

        BaseOutput baseOutput = constructBaseOutput(userName, roleName, builder);
        return Response.ok().entity(baseOutput).build();
    }

    private BaseOutput constructBaseOutput(String userName, String roleName, StringBuilder builder) {
        BaseOutput baseOutput = new BaseOutput();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("user_name",userName);
        map.put("role_name",roleName);
        map.put("resource_ids",builder.toString());
        baseOutput.setCode(0);
        baseOutput.setMsg("success");
        baseOutput.setTotal(1);
        baseOutput.getData().add(map);
        return baseOutput;
    }

    //在这种Service中日志的打印方式
    private boolean validatePasswd(User user) {
        //查询数据库中是否存在这个User对象,没有返回null,有继续第二步和第三步
        Integer validateValue = userDao.validateLoginInput(user);
        if(validateValue == 0){
            System.out.println("用户名，密码验证不通过");
            return true;
        }
        return false;
    }

}
