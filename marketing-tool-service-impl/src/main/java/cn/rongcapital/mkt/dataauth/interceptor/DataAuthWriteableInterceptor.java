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

import cn.rongcapital.mkt.common.exception.NoWriteablePermissionException;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;

@Aspect
@Component
public class DataAuthWriteableInterceptor {
    @Autowired
    private DataAuthService dataAuthService;
    
    // service层切入点
    @Pointcut("@annotation(cn.rongcapital.mkt.dataauth.interceptor.DataAuthWriteable)")
    public void writeableServiceAspect() {}

    @AfterReturning("writeableServiceAspect()")
    @Transactional
    public void doAfterMethod(JoinPoint joinPoint) throws NoSuchElementException,NoWriteablePermissionException  {
        Method method = ExpressionHelper.getMethod(joinPoint);
        if (method != null && method.isAnnotationPresent(DataAuthWriteable.class)) {
            DataAuthWriteable annotation = method.getAnnotation(DataAuthWriteable.class);
            String resourceIdTemp = annotation.resourceId();
            String orgIdTemp = annotation.orgId();
            String resourceType = annotation.resourceType();
            Long resourceIdObj =null;
            Long orgIdObj = null;
            switch(annotation.type()){
                case SpEl:
                    resourceIdObj = ExpressionHelper.executeTemplate(resourceIdTemp, joinPoint,Long.class);
                    orgIdObj = ExpressionHelper.executeTemplate(orgIdTemp, joinPoint,Long.class);
                    break;
                default:
                    resourceIdObj =Long.parseLong(resourceIdTemp);
                    orgIdObj = Long.parseLong(orgIdTemp);
                    break;
                }
            //参数校验
            if(StringUtils.isBlank(resourceType )){
                throw new NoSuchElementException();
            }
            long resourceId = resourceIdObj.longValue();
            long orgId =  orgIdObj.longValue();
            //将新增数据保存对应的数据权限
            this.dataAuthService.validateWriteable(resourceType, resourceId, orgId);
        }
    }
}
