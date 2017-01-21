/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的） 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统) @author:liuhaizhan
 * @version: 版本v1.6 @date(创建、开发日期)：2017年1月20日 @date(最后修改日期)：2017年1月20日 @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.interceptor;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;

import cn.rongcapital.mkt.event.service.EventMCReceviceProcessorImpl;

@Aspect
@Component
public class CountInterceptor implements ThrowsAdvice, AfterReturningAdvice {

    final static MetricRegistry metrics = new MetricRegistry();
    final static Slf4jReporter reporter =
            Slf4jReporter.forRegistry(metrics).outputTo(LoggerFactory.getLogger(CountInterceptor.class))
                    .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang.Object,
     * java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
     */
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        // TODO Auto-generated method stub 
        ReportI ReportIAnnotation = AnnotationUtils.findAnnotation(getTargetMed(target,method), ReportI.class);
        reporter.start(30, TimeUnit.SECONDS);
        Counter successJobs = metrics.counter(ReportIAnnotation.value()+",SUCCESS");
        successJobs.inc();
    }

    public void afterThrowing(Method method, Object[] args, Object target, RuntimeException ex) throws Throwable {
        ReportI ReportIAnnotation = AnnotationUtils.findAnnotation(getTargetMed(target,method), ReportI.class);
        Counter failsJobs = metrics.counter(ReportIAnnotation.value()+",FAILS");
        reporter.start(30, TimeUnit.SECONDS);
        failsJobs.inc();
    }


    private Method getTargetMed(Object target, Method method) {
        Method[] methods = target.getClass().getDeclaredMethods();
        Method re = null;
        for (Method metd : methods) {
            if (method.getName().equals(metd.getName())) {
                re = metd;
                break;
            }
        }
        return re;
    }



}
