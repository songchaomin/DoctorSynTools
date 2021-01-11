package com.kuka.listener;

import com.kuka.domain.SchedulerJob;
import com.kuka.event.SynInventoryEvent;
import com.kuka.scheduler.services.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SynInventoryListener implements ApplicationListener<SynInventoryEvent> {
    @Autowired
    private SchedulerService schedulerService;
    @Override
    public void onApplicationEvent(SynInventoryEvent event)
    {
        log.info("【定时任务开启中】");
        SchedulerJob schedulerJob = new SchedulerJob();
        schedulerJob.setClassName("com.kuka.scheduler.job.SynInventoryJob");
        schedulerJob.setJobName("synInventory");
        schedulerJob.setJobGroup("ds");
        schedulerJob.setTriggerName("synInventoryTrigger");
        schedulerJob.setTriggerGroup("ds");
        schedulerJob.setCronExpression("0 0 0/1 * * ?");
        try {
            schedulerService.runJob(schedulerJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
