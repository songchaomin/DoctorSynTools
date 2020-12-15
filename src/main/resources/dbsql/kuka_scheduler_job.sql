
/**
  定时任务信息表
 */
create table kuka_scheduler_job(
    id int primary key IDENTITY (1,1),--主键
    className varchar (256),--执行的任务类
    cronExpression varchar (256),--定时表达式
    jobName varchar (256), --任务名称
    jobGroup varchar (256),--任务组
    triggerName varchar (256),--触发器名称
    triggerGroup varchar (256),--触发器组
    description varchar (1024),--描述
	status varchar(8),--状态1：启动 2：暂停 3：取消
    createTime datetime --创建时间
)
