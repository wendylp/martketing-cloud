/*************************************************
 * @功能及特点的描述简述: 克隆数据权限aop切面类
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Aspect
@Component
public class DataAuthCloneInterceptor {
    @Autowired
    private DataAuthService dataAuthService;
    
    // service层切入点
    @Pointcut("@annotation(cn.rongcapital.mkt.dataauth.interceptor.DataAuthClone)")
    public void cloneServiceAspect() {}

    @AfterReturning(pointcut ="cloneServiceAspect()",returning = "returnValue")
    @Transactional
    public void doAfterMethod(JoinPoint joinPoint,Object returnValue) throws Throwable {
        Method method = ExpressionHelper.getMethod(joinPoint);
        if (method != null && method.isAnnotationPresent(DataAuthClone.class)) {
            DataAuthClone annotation = method.getAnnotation(DataAuthClone.class);
            String resourceIdTemp = annotation.resourceId();
            String fromResourceIdTemp = annotation.fromResourceId();
            String toOrgIdTemp = annotation.toOrgId();
            String writeableTemp  =annotation.writeable();
            
            String resourceType = annotation.resourceType();
            
            //参数校验
            if(StringUtils.isBlank(resourceType )){
                throw new NoSuchElementException();
            }
            
            //判断当前克隆数据成功，继续执行克隆数据权限
            BaseOutput baseOutput = (BaseOutput) returnValue;
            if(ApiErrorCode.SUCCESS.getCode() ==  baseOutput.getCode()){
                //将新增数据保存对应的数据权限
            	List<Object> resourceIds = baseOutput.getData();
                switch(annotation.type()){
                case SpEl:
                	prepareSpel(joinPoint, fromResourceIdTemp, toOrgIdTemp, writeableTemp, resourceIds, resourceType);
                	break;
                default:
                	prepareNormal(resourceIdTemp, fromResourceIdTemp, toOrgIdTemp, writeableTemp, resourceType);
                    break;
                }
            }
        }
    }

	private void prepareNormal(String resourceIdTemp, String fromResourceIdTemp, String toOrgIdTemp,
			String writeableTemp,String  resourceType) {
		Long resourceId =Long.parseLong(resourceIdTemp);
		Long fromResourceId = Long.parseLong(fromResourceIdTemp);
		Long toOrgId = Long.parseLong(toOrgIdTemp);
		Boolean writeable = Boolean.parseBoolean(writeableTemp);
		//将新增数据保存对应的数据权限
        this.dataAuthService.clone(resourceType, resourceId, fromResourceId, toOrgId, writeable);

	}

	private void prepareSpel(JoinPoint joinPoint, String fromResourceIdTemp, String toOrgIdTemp, String writeableTemp,
			List<Object> resourceIds,String  resourceType) {
		Long resourceId;
		Long fromResourceId;
		Long toOrgId;
		Boolean writeable;
		List<Long> toOrgIds = ExpressionHelper.executeTemplate(toOrgIdTemp, joinPoint, List.class);
		for (int j = 0; j < toOrgIds.size(); j++) {
			resourceId = (Long)resourceIds.get(j);
		    fromResourceId = ExpressionHelper.executeTemplate(fromResourceIdTemp, joinPoint,Long.class);
		    toOrgId =  toOrgIds.get(j);
		    writeable = ExpressionHelper.executeTemplate(writeableTemp, joinPoint, Boolean.class);
		    //将新增数据保存对应的数据权限
            this.dataAuthService.clone(resourceType, resourceId, fromResourceId, toOrgId, writeable);
		}
	}
    
    
}
