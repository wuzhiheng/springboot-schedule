package com.wonders.commonweb.schedule;

import java.util.concurrent.ScheduledFuture;

/**
 * @Author : wuzhiheng
 * @Description :
 * @Date Created in 19:31 2020-02-11
 */
public final class ScheduledTask {

    volatile ScheduledFuture future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}