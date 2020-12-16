package com.kuka.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Data
public class SchedulerJob {
    private String jobId;
    private String className;

    private String cronExpression;

    private String jobName;

    private String jobGroup;

    private String triggerName;

    private String triggerGroup;

    private Boolean pause;

    private Boolean enable;

    private String description;

    private Date createTime;

    private Date lastupdateTime;

    private String owner;

}
