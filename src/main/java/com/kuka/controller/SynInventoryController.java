package com.kuka.controller;

import com.kuka.scheduler.job.SynInventoryJob;
import com.kuka.scheduler.services.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SynInventoryController {

    @Autowired
    private SynInventoryJob synInventoryJob;

    @ResponseBody
    @GetMapping("synInventory/test")
    public void test() {
      synInventoryJob.synInventory();
    }

}
