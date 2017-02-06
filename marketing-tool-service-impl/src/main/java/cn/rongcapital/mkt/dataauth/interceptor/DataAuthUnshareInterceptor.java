/*************************************************
 * @功能及特点的描述简述: 数据权限取消分享aop切面类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-02-06 
 * @date(最后修改日期)：2017-02-06 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.dataauth.interceptor;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.dataauth.service.DataAuthService;

@Aspect
@Component
public class DataAuthUnshareInterceptor {
    @Autowired
    private DataAuthService dataAuthService;
    
    // service层切入点
    @Pointcut("@annotation(cn.rongcapital.mkt.dataauth.interceptor.DataAuthUnshare)")
    public void unshareServiceAspect() {}

    @AfterReturning("unshareServiceAspect()")
    @Transactional
    public void doAfterMethod(JoinPoint joinPoint) throws Throwable {
        Method method = ExpressionHelper.getMethod(joinPoint);
        if (method != null && method.isAnnotationPresent(DataAuthUnshare.class)) {
            DataAuthUnshare annotation = method.getAnnotation(DataAuthUnshare.class);
            String orgIdTemp = annotation.orgId();
            String shareIdTemp = annotation.shareId();
            String shareId = null;
            long orgId;
            switch(annotation.type()){
                case SpEl:
                    shareId = ExpressionHelper.executeTemplate(shareIdTemp, joinPoint,String.class);
                    orgId = ExpressionHelper.executeTemplate(orgIdTemp, joinPoint, Long.class);
                    break;
                default:
                    shareId =shareIdTemp;
                    orgId = Long.parseLong(orgIdTemp);
                    break;
                }
            //参数校验
            if(StringUtils.isBlank(shareId )){
                throw new NoSuchElementException();
            }
            
            //调用业务处理方法
            this.dataAuthService.unshare(shareId);
        }
    }
}
