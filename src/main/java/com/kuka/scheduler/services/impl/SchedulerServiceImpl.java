package com.kuka.scheduler.services.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.kuka.scheduler.services.SchedulerService;
import com.kuka.domain.SchedulerJob;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void addJob(SchedulerJob job) throws SchedulerException {
        try {
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getClassName()))
                    .withIdentity(job.getJobName(), job.getJobGroup())
                    .build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(job.getTriggerName(), job.getTriggerGroup())
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail,cronTrigger);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    @Override
    public void runJob(SchedulerJob job) throws SchedulerException {
        this.addJob(job);
        scheduler.start();
    }

    @Override
    public boolean updateJob(SchedulerJob job) throws SchedulerException {
        Date date=null;
        TriggerKey triggerKey=new TriggerKey(job.getTriggerName(), job.getTriggerGroup());
        CronTrigger cronTrigger= (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldCronExpression=cronTrigger.getCronExpression();
        if (!oldCronExpression.equalsIgnoreCase(job.getCronExpression())){
            CronScheduleBuilder cronScheduleBuilder=CronScheduleBuilder.cronSchedule(job.getCronExpression());
            CronTrigger trigger=TriggerBuilder.newTrigger().withIdentity(job.getTriggerName(),job.getTriggerGroup())
                    .withSchedule(cronScheduleBuilder).build();
            date=scheduler.rescheduleJob(triggerKey,trigger);
        }

        return date!=null;
    }

    @Override
    public void pauseJob(SchedulerJob job) throws SchedulerException {
        JobKey jobKey=new JobKey(job.getJobName(),job.getJobGroup());
        JobDetail jobDetail=scheduler.getJobDetail(jobKey);
        if (jobDetail==null)
            return;
        scheduler.pauseJob(jobKey);
    }

    @Override
    public void resumeJob(SchedulerJob job) throws SchedulerException {
        JobKey jobKey=new JobKey(job.getJobName(),job.getJobGroup());
        JobDetail jobDetail=scheduler.getJobDetail(jobKey);
        if (jobDetail==null)
            return;
        scheduler.resumeJob(jobKey);
    }

    @Override
    public void deleteJob(SchedulerJob job) throws SchedulerException {
        JobKey jobKey=new JobKey(job.getJobName(), job.getJobGroup());
        JobDetail jobDetail=scheduler.getJobDetail(jobKey);
        if (jobDetail==null)
            return;
        scheduler.deleteJob(jobKey);
    }

    @Override
    public String selectJob(SchedulerJob job){
        return JSON.toJSONString(queryJob(job));
    }

    @Override
    public boolean existsJob(SchedulerJob job){
        JobKey jobKey=new JobKey(job.getJobName(), job.getJobGroup());
        try {
            JobDetail jobDetail=scheduler.getJobDetail(jobKey);
            if(Objects.nonNull(jobDetail))return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据任务的jobName、jobGroup、triggerName、triggerGroup 查询任务信息。
     * @param job
     *
     * @return 返回如下信息：
     * jobName job名称
     * jobGroup job组名
     * triggerName trigger名称
     * triggerGroup trigger组名
     * jobClassName job类名称
     * startTime 任务启动时间
     * previousFireTime 上次执行时间
     * nextFireTime 下次执行时间
     * cronExpression 配置的cron表达式
     *
     */
    public Map queryJob(SchedulerJob job){
        Map<String,String> map=new HashMap<String,String>();
        try {
            String jobName=job.getJobName(),jobGroup=job.getJobGroup();
            map.put("jobName",jobName);
            map.put("jobGroup",jobGroup);
            if(StringUtils.isNotEmpty(jobName) && StringUtils.isNotEmpty(jobGroup)){
                JobKey jobKey=new JobKey(jobName, jobGroup);
                JobDetail jobDetail=scheduler.getJobDetail(jobKey);
                if(Objects.nonNull(jobDetail)){
                    map.put("jobClassName",jobDetail.getJobClass().getName());
                    //map.put("isDurable",String.valueOf(jobDetail.isDurable()));
                    //map.put("requestsRecovery",String.valueOf(jobDetail.requestsRecovery()));
                }
            }
            String triggerName=job.getTriggerName(),triggerGroup=job.getTriggerGroup();
            map.put("triggerName",triggerName);
            map.put("triggerGroup",triggerGroup);
            if(StringUtils.isNotEmpty(triggerName) && StringUtils.isNotEmpty(triggerGroup)){
                TriggerKey triggerKey=new TriggerKey(triggerName, triggerGroup);
                CronTrigger cronTrigger= (CronTrigger) scheduler.getTrigger(triggerKey);
                if(Objects.nonNull(cronTrigger)){
                    map.put("cronExpression",cronTrigger.getCronExpression());//配置的cron表达式
                    String startTime=Objects.nonNull(cronTrigger.getStartTime())? DateUtil.formatTime(cronTrigger.getStartTime()):"";
                    map.put("startTime",startTime);//初始时间
                    String previousFireTime=Objects.nonNull(cronTrigger.getPreviousFireTime())?DateUtil.formatTime(cronTrigger.getPreviousFireTime()):"";
                    map.put("previousFireTime",previousFireTime);//上次执行时间
                    String nextFireTime=Objects.nonNull(cronTrigger.getNextFireTime())?DateUtil.formatTime(cronTrigger.getNextFireTime()):"";
                    map.put("nextFireTime",nextFireTime);//下次执行时间
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return map;
    }

}
