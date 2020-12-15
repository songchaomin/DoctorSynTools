package com.kuka.scheduler.services;

import com.kuka.domain.SchedulerJob;
import org.quartz.SchedulerException;

public interface SchedulerService {
    /**
     * 创建Job
     * @param job
     */
     void addJob(SchedulerJob job) throws SchedulerException, ClassNotFoundException;

    /**
     * 执行Job
     */
     void runJob(SchedulerJob job) throws SchedulerException;

    /**
     * 修改Job
     * @param job
     */
     boolean updateJob(SchedulerJob job) throws SchedulerException;

    /**
     * 暂定Job
     * @param job
     */
     void pauseJob(SchedulerJob job) throws SchedulerException;

    /**
     * 唤醒Job
     * @param job
     */
     void resumeJob(SchedulerJob job) throws SchedulerException;

    /**
     * 删除Job
     * @param job
     */
     void deleteJob(SchedulerJob job) throws SchedulerException;

    /**
     * 获取Job
     * @param job
     */
     String selectJob(SchedulerJob job);

     boolean existsJob(SchedulerJob job);
}
