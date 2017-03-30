/*************************************************
 * @功能及特点的描述简述: 定时任务与相应的线程池管理类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-3-22
 * @date(最后修改日期)：2017-3-22
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.job.util;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class ScheduledFutureExecutor {
    private ScheduledFuture<?> scheduledFuture = null;
    private ScheduledExecutorService scheduledExecutor = null;

    public ScheduledFutureExecutor() {
    }

    public ScheduledFutureExecutor(ScheduledFuture<?> scheduledFuture, ScheduledExecutorService scheduledExecutor) {
        this.scheduledFuture = scheduledFuture;
        this.scheduledExecutor = scheduledExecutor;
    }

    public ScheduledFuture<?> getScheduledFuture() {
        return scheduledFuture;
    }

    public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }

    public ScheduledExecutorService getScheduledExecutor() {
        return scheduledExecutor;
    }

    public void setScheduledExecutor(ScheduledExecutorService scheduledExecutor) {
        this.scheduledExecutor = scheduledExecutor;
    }
}
