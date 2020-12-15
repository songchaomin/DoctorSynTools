package com.kuka.controller;

import com.kuka.domain.SchedulerJob;
import com.kuka.scheduler.job.SynInventoryJob;
import com.kuka.scheduler.services.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SynInventoryController {
    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private SynInventoryJob synInventoryJob;
    /**
     * 启动库存同步任务
     */
    @ResponseBody
    @GetMapping("synInventory/start")
    public void startOrderAssign() {
        log.info("库存同步任务【启动】！");
        SchedulerJob schedulerJob = new SchedulerJob();
        schedulerJob.setClassName("com.kuka.scheduler.job.SynInventoryJob");
        schedulerJob.setJobName("synInventory");
        schedulerJob.setJobGroup("ds");
        schedulerJob.setTriggerName("synInventoryTrigger");
        schedulerJob.setTriggerGroup("ds");
        schedulerJob.setCronExpression("0 0 18 * * ?");
        try {
            schedulerService.runJob(schedulerJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @GetMapping("synInventory/test")
    public void test() {
      synInventoryJob.synInventory();
    }


}
