
/**
  供应商库存（中间表）
 */
create table supplier_stock(
    store_id varchar (256) primary key,--库存Id
    is_retail int (1),--是否拆零
    drug_num varchar (256), --药品编码
    clerk_num varchar (64),--业务员编码
    drug_name varchar (256),--药品名称
    product_factory varchar (256),--生产厂家
    drug_owner varchar (64),--上市许可持有人
	spec varchar(64),--规格
    pack varchar(64), --包装（剂量）
    middle_package varchar(64), --中包装
    batch_num varchar(64), --批号
    batch_group varchar(64), --批次号
    due_date varchar(64), --有效期
    due_date2 varchar(64), --有效期(计算)
    store_num decimal(18, 0), --库存数量
    hightest_price decimal(18, 4), --最高限价
    price decimal(18, 4), --售价
    unit varchar(64), --单位
    drug_type varchar(64), --剂型
    tax_rate int(32), --税率
    approve_no varchar(64), --批准文号
    store_no varchar(64), --仓库编号
    special_drug int(32), --专机销售(可以查看，线下销售)
    supplier_category varchar(64), --供货单位类别
    main_url varchar(256), --图片路径
    update_date varchar(256), --最后更新时间
    drug_bar_code varchar(256), --条码
    drug_code varchar(256), --药品简码
    factory_code varchar(256), --厂商简码
    drug_owner_code varchar(256), --持有人简码
    prod_date varchar(256), --生产日期
    prod_date2 varchar(256), --生产日期(计算)
)
