/*************************************************
 * @功能及特点的描述简述: 数据权限删除aop切面类
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
public class DataAuthEvictInterceptor {
    @Autowired
    private DataAuthService dataAuthService;
    
    // service层切入点
    @Pointcut("@annotation(cn.rongcapital.mkt.dataauth.interceptor.DataAuthEvict)")
    public void evictServiceAspect() {}

    @AfterReturning("evictServiceAspect()")
    @Transactional
    public void doAfterMethod(JoinPoint joinPoint) throws Throwable {
        Method method = ExpressionHelper.getMethod(joinPoint);
        if (method != null && method.isAnnotationPresent(DataAuthEvict.class)) {
            DataAuthEvict annotation = method.getAnnotation(DataAuthEvict.class);
            String resourceIdTemp = annotation.resourceId();
            
            String resourceType = annotation.resourceType();
            Long resourceIdObj =null;
            switch(annotation.type()){
                case SpEl:
                    resourceIdObj = ExpressionHelper.executeTemplate(resourceIdTemp, joinPoint,Long.class);
                    break;
                default:
                    resourceIdObj =Long.parseLong(resourceIdTemp);
                    break;
                }
            //参数校验
            if(StringUtils.isBlank(resourceType )){
                throw new NoSuchElementException();
            }
            long resourceId = resourceIdObj.longValue();
            //将新增数据保存对应的数据权限
            this.dataAuthService.evict(resourceType, resourceId);
        }
    }
}
