/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年1月20日 
 * @date(最后修改日期)：2017年1月20日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.interceptor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountAnnotationAdvisor extends AbstractPointcutAdvisor{

   /* public CountInterceptor getCountInterceptor() {
        return countInterceptor;
    }

    public void setCountInterceptor(CountInterceptor countInterceptor) {
        this.countInterceptor = countInterceptor;
    }*/

    /* (non-Javadoc)
     * @see org.springframework.aop.PointcutAdvisor#getPointcut()
     */
    @Autowired
    private CountInterceptor  countInterceptor;
    
    
    @Override
    public Pointcut getPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(ReportI.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(ReportI.class);
        ComposablePointcut result = new ComposablePointcut(cpc).union(mpc);
        return result;
    }

    /* (non-Javadoc)
     * @see org.springframework.aop.Advisor#getAdvice()
     */
    @Override
    public Advice getAdvice() {
        // TODO Auto-generated method stub
        return this.countInterceptor;
    }

}
