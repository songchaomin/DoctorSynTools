
/**
  库存同步配置表
 */
create table inventory_config(
    id int primary key IDENTITY (1,1),--主键
    type varchar (32),--类型
    content varchar (256), --值
    notes varchar (256),--备注
)

insert into inventory_config (type,content,notes) values('time','2010-01-01 00:00:00','查询库存开始时间');
insert into inventory_config (type,content,notes) values('hwi','0','货位流水号末位值');
insert into inventory_config (type,content,notes) values('spi','0','商品流水号末位值');
