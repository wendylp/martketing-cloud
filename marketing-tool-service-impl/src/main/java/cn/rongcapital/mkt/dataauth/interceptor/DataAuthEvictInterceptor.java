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

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.exception.ReturnTypeMustBaseOutputException;
import cn.rongcapital.mkt.vo.BaseOutput;
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

    @AfterReturning(pointcut = "evictServiceAspect()",returning = "returnValue")
    @Transactional
    public void doAfterMethod(JoinPoint joinPoint,Object returnValue) throws Throwable {
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


            if(! (returnValue instanceof  BaseOutput)){
                String error = "return value must is BaseOutPut.";
                throw new ReturnTypeMustBaseOutputException(error);
            }


            long resourceId = resourceIdObj.longValue();

            //判断当前删除数据成功，继续执行删除数据权限，否者，不删除
            BaseOutput baseOutput = (BaseOutput) returnValue;
            if(ApiErrorCode.SUCCESS.getCode() ==  baseOutput.getCode()){
                //将新增数据保存对应的数据权限
                this.dataAuthService.evict(resourceType, resourceId);
            }
        }
    }
}
