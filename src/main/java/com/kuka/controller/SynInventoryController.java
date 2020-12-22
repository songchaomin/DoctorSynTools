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
    private SynInventoryJob synInventoryJob;
    @Autowired
    private SchedulerService schedulerService;
    @ResponseBody
    @GetMapping("synInventory/test")
    public void test() {
      synInventoryJob.synInventory();
    }


    /**
     * 启动售前订单自动流转定时任务
     */
    @ResponseBody
    @GetMapping("synInventory/start")
    public void startOrderAssign() {
        log.info("定时任务【启动】！");
        SchedulerJob schedulerJob = new SchedulerJob();
        schedulerJob.setClassName("com.kuka.scheduler.job.SynInventoryJob");
        schedulerJob.setJobName("synInventory");
        schedulerJob.setJobGroup("ds");
        schedulerJob.setTriggerName("synInventoryTrigger");
        schedulerJob.setTriggerGroup("ds");
        try {
            schedulerService.runJob(schedulerJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停售前订单自动流转定时任务
     */
    @ResponseBody
    @GetMapping("synInventory/pause")
    public void pauseOrderAssign() {
        log.info("定时任务【暂停】！");
        SchedulerJob schedulerJob = new SchedulerJob();
        schedulerJob.setJobName("synInventory");
        schedulerJob.setJobGroup("ds");
        schedulerJob.setTriggerName("synInventoryTrigger");
        schedulerJob.setTriggerGroup("ds");
        try {
            schedulerService.pauseJob(schedulerJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复售前订单自动流转定时任务
     */
    @ResponseBody
    @GetMapping("synInventory/resume")
    public void resumeOrderAssign() {
        log.info("定时任务【恢复】！");
        SchedulerJob schedulerJob = new SchedulerJob();
        schedulerJob.setJobName("synInventory");
        schedulerJob.setJobGroup("ds");
        schedulerJob.setTriggerName("synInventoryTrigger");
        schedulerJob.setTriggerGroup("ds");
        try {
            schedulerService.resumeJob(schedulerJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除售前订单自动流转定时任务
     */
    @ResponseBody
    @GetMapping("synInventory/delete")
    public void deleteOrderAssign() {
        log.info("定时任务【删除】！");
        SchedulerJob schedulerJob = new SchedulerJob();
        schedulerJob.setJobName("synInventory");
        schedulerJob.setJobGroup("ds");
        schedulerJob.setTriggerName("synInventoryTrigger");
        schedulerJob.setTriggerGroup("ds");
        try {
            schedulerService.deleteJob(schedulerJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
