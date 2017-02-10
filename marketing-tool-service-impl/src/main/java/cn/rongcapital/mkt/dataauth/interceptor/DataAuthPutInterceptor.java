/*************************************************
 * @功能及特点的描述简述: 数据权限创建aop切面类 该类被编译测试过
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
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.dataauth.service.DataAuthService;

@Aspect
@Component
public class DataAuthPutInterceptor {

    @Autowired
    private DataAuthService dataAuthService;

    // service层切入点
    @Pointcut("@annotation(cn.rongcapital.mkt.dataauth.interceptor.DataAuthPut)")
    public void putServiceAspect() {}

    @AfterReturning(pointcut = "putServiceAspect()", returning = "result")
    @Transactional
    public void doAfterMethod(JoinPoint joinPoint, Object result) throws Throwable {
        Method method = ExpressionHelper.getMethod(joinPoint);
        if (method != null && method.isAnnotationPresent(DataAuthPut.class)) {
            DataAuthPut annotation = method.getAnnotation(DataAuthPut.class);

            String resourceIdTemp = annotation.resourceId();
            String orgIdTemp = annotation.orgId();
            String outputResourceId = annotation.outputResourceId();
            String resourceType = annotation.resourceType();

            // 参数校验
            if (StringUtils.isBlank(orgIdTemp)) {
                throw new IllegalArgumentException("orgId expression is illegal.");
            }
            if (StringUtils.isBlank(outputResourceId)) {
                throw new IllegalArgumentException("outputResourceId expression is illegal.");
            }
            if (StringUtils.isBlank(resourceType)) {
                throw new IllegalArgumentException("resourceType expression is illegal.");
            }

            Long resourceIdObj = null;
            Long orgIdObj = null;
            switch (annotation.type()) {
                case SpEl:
                    if (StringUtils.isNotBlank(resourceIdTemp)) {
                        resourceIdObj = ExpressionHelper.executeTemplate(resourceIdTemp, joinPoint, Long.class);
                    }
                    orgIdObj = ExpressionHelper.executeTemplate(orgIdTemp, joinPoint, Long.class);
                    break;
                default:
                    if (StringUtils.isNotBlank(resourceIdTemp)) {
                        resourceIdObj = Long.parseLong(resourceIdTemp);
                    }
                    orgIdObj = Long.parseLong(orgIdTemp);
                    break;
            }

            // 数据更新情况不需要生成权限
            if (resourceIdObj != null && resourceIdObj.longValue() > 0) {
                return;
            }
            if (orgIdObj == null || orgIdObj.longValue() == 0) {
                throw new NoSuchElementException("calculate orgId from expression is illegal.");
            }
            // 没有输出的情况
            if (result == null) {
                throw new NoSuchElementException("business method has no output.");
            }
            Long outputId = null;
            switch (annotation.type()) {
                case SpEl:
                    ExpressionParser parser = new SpelExpressionParser();
                    StandardEvaluationContext context = new StandardEvaluationContext(result);
                    outputId = parser.parseExpression(outputResourceId).getValue(context, Long.class);
                    break;
                default:
                    outputId = Long.parseLong(outputResourceId);
                    break;
            }
            // 输出资源ID不合法的情况
            if (outputId == null || outputId.longValue() == 0) {
                throw new NoSuchElementException("calculate generated resource_id from expression is illegal.");
            }
            // 将新增数据保存对应的数据权限
            this.dataAuthService.put(orgIdObj.longValue(), resourceType, outputId.longValue());
        }
    }
}
