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
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.exception.NoWriteablePermissionException;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Aspect
@Component
public class DataAuthUnshareInterceptor {
    @Autowired
    private DataAuthService dataAuthService;
    
    // service层切入点
    @Pointcut("@annotation(cn.rongcapital.mkt.dataauth.interceptor.DataAuthUnshare)")
    public void unshareServiceAspect() {}

    @AfterReturning(pointcut ="unshareServiceAspect()", returning = "returnValue")
    @Transactional
    public void doAfterMethod(JoinPoint joinPoint,Object returnValue) throws Throwable {
        Method method = ExpressionHelper.getMethod(joinPoint);
        if (method != null && method.isAnnotationPresent(DataAuthUnshare.class)) {
            DataAuthUnshare annotation = method.getAnnotation(DataAuthUnshare.class);
            String shareIdTemp = annotation.shareId();
            
            //判断当前克隆数据成功，继续执行克隆数据权限
            BaseOutput baseOutput = (BaseOutput) returnValue;
            if(ApiErrorCode.SUCCESS.getCode() ==  baseOutput.getCode()){
            	String shareId = null;
                switch(annotation.type()){
                    case SpEl:
                    	unShareWithSpel(joinPoint, shareIdTemp);                    
                    	break;
                    default:
                        shareId =shareIdTemp;
                        unShareWithNormal(shareId);
                        break;
                }
            }
        }
    }

	private void unShareWithSpel(JoinPoint joinPoint, String shareIdTemp)
			throws NoWriteablePermissionException {
		List<String> shareIds;
		shareIds = ExpressionHelper.executeTemplate(shareIdTemp, joinPoint,List.class);

		for (String string : shareIds) {
	        unShareWithNormal(string);
		}
	}

	private void unShareWithNormal(String string) throws NoWriteablePermissionException {
		//参数校验
		if(StringUtils.isBlank(string )){
		    throw new NoSuchElementException();
		}
		//判断用户是否对该分享资源有读写权限
		//调用业务处理方法
		this.dataAuthService.unshare(string);
	}
}
