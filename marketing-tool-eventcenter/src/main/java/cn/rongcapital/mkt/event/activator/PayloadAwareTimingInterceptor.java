/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package cn.rongcapital.mkt.event.activator;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.util.StopWatch;

/**
 * A sample channel interceptor that illustrates a technique to capture elapsed times based on
 * message payload types.
 *
 * @author Gary Russell
 * @since 2.2
 *
 */
@ManagedResource
public class PayloadAwareTimingInterceptor extends ChannelInterceptorAdapter {


    private static final Logger LOGGER = LoggerFactory.getLogger(PayloadAwareTimingInterceptor.class);

    private final ThreadLocal<StopWatchHolder> stopWatchHolder =
            new ThreadLocal<PayloadAwareTimingInterceptor.StopWatchHolder>();

    private final Map<String, Stats> statsMap = new ConcurrentHashMap<String, PayloadAwareTimingInterceptor.Stats>();

    /**
     *
     * @param classes An array of types for which statistics will be captured; if not supplied
     *        {@link Object} will be added as a catch-all.
     */
    public PayloadAwareTimingInterceptor(String messageChannel) {
        if (!this.statsMap.containsKey(messageChannel)) {
            this.statsMap.put(messageChannel, new Stats());
        }
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //message.get
        String headtype = message.getHeaders().get("eventCenter.eventType") == null
                ? "events"
                : message.getHeaders().get("eventCenter.eventType").toString();
        this.stopWatchHolder.set(new StopWatchHolder(stopWatch, headtype));
        return super.preSend(message, channel);
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StopWatchHolder holder = this.stopWatchHolder.get();
        if (holder != null) {
            holder.getStopWatch().stop();
            Stats stats = this.statsMap.get(holder.getType());
            if (stats == null) {
                stats = this.statsMap.get("events");
            }
            stats.add(holder.getStopWatch().getLastTaskTimeMillis());
            String quename = "".equals(holder.getType()) ? "events" : holder.getType();

            LOGGER.info(" 消息类型:{},运行情况:{}", quename, stats.toString());
        }

    }

    @ManagedOperation
    public String[] getSummary() {
        String[] data = new String[this.statsMap.size()];
        int i = 0;
        for (Entry<String, Stats> entry : this.statsMap.entrySet()) {
            data[i++] = entry.getKey() + " : " + entry.getValue().toString();
        }
        return data;

    }

    @ManagedOperation
    public long getCount(String messageChannel) throws Exception {
        return this.statsMap.get(messageChannel).getCount();
    }

    @ManagedOperation
    public long getLastTime(String messageChannel) throws Exception {
        return this.statsMap.get(messageChannel).getLastTime();
    }

    @ManagedOperation
    public float getAverage(String messageChannel) throws Exception {
        return this.statsMap.get(messageChannel).getAverage();
    }

    private class StopWatchHolder {

        private final StopWatch stopWatch;

        private final String headtype;

        /**
         * @param stopWatch
         * @param type
         */
        public StopWatchHolder(StopWatch stopWatch, String type) {
            this.stopWatch = stopWatch;
            this.headtype = type;
        }

        public StopWatch getStopWatch() {
            return stopWatch;
        }

        public String getType() {
            return headtype;
        }
    }

    private class Stats {

        private long count;

        private long totalTime;

        private float average;

        private long lastTime;

        public long getCount() {
            return count;
        }

        public long getLastTime() {
            return lastTime;
        }

        public float getAverage() {
            return average;
        }

        public synchronized void add(long time) {
            this.count++;
            this.lastTime = time;
            this.totalTime += time;
            this.average = (float) this.totalTime / (float) this.count;
        }

        @Override
        public String toString() {
            return "Stats [count=" + count + ", totalTime="+totalTime/1000+" s,average=" + average / 1000 + "s, lastTime=" + lastTime / 1000 + "s]";
        }


    }
}
