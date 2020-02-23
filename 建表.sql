create table sys_job(
 id int AUTO_INCREMENT PRIMARY KEY ,
 bean_name varchar(64),
 method_name varchar(64),
 method_parms varchar(64),
 ron_expression varchar(32),
 remark varchar(64),
 `status` tinyint(1) comment '状态，1-正常；0-暂停',
 create_time datetime default now(),
 update_time datetime
)