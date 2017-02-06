/*************************************************
 * @功能及特点的描述简述: 分享数据权限aop切面类
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
public class DataAuthShareInterceptor {
    @Autowired
    private DataAuthService dataAuthService;
    
    // service层切入点
    @Pointcut("@annotation(cn.rongcapital.mkt.dataauth.interceptor.DataAuthShare)")
    public void shareServiceAspect() {}

    @AfterReturning("shareServiceAspect()")
    @Transactional
    public void doAfterMethod(JoinPoint joinPoint) throws Throwable {
        Method method = ExpressionHelper.getMethod(joinPoint);
        if (method != null && method.isAnnotationPresent(DataAuthShare.class)) {
            DataAuthShare annotation = method.getAnnotation(DataAuthShare.class);
            String resourceIdTemp = annotation.resourceId();
            String toOrgIdTemp = annotation.toOrgId();
            String writeableTemp  =annotation.writeable();
            
            String resourceType = annotation.resourceType();
            Long resourceId =null;
            Long toOrgId = null;
            Boolean writeable = false;
            
            switch(annotation.type()){
                case SpEl:
                    resourceId = ExpressionHelper.executeTemplate(resourceIdTemp, joinPoint,Long.class);
                    toOrgId = ExpressionHelper.executeTemplate(toOrgIdTemp, joinPoint, Long.class);
                    writeable = ExpressionHelper.executeTemplate(writeableTemp, joinPoint, Boolean.class);
                    break;
                default:
                    resourceId =Long.parseLong(resourceIdTemp);
                    toOrgId = Long.parseLong(toOrgIdTemp);
                    writeable = Boolean.parseBoolean(writeableTemp);
                    break;
                }
            //参数校验
            if(StringUtils.isBlank(resourceType )){
                throw new NoSuchElementException();
            }
            //将新增数据保存对应的数据权限
            this.dataAuthService.share(resourceType, resourceId, toOrgId, writeable);
        }
    }
}
