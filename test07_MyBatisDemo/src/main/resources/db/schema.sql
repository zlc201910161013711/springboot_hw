-- drop table if exists `user`;
-- drop table if exists `organization`;
-- drop table if exists `posi`;
-- drop table if exists `cla`;
-- drop table if exists `actv`;
-- drop table if exists `user_org`;
-- drop table if exists `user_actv`;
-- drop table if exists `ann`;
-- drop table if exists `mee`;
-- drop table if exists `join_org_appl`;
-- drop table if exists `cr_org_appl`;
-- drop table if exists `cr_actv_appl`;


-- create table `user` (
--                         `id` int(11) not null auto_increment,
--                         `name` varchar(225) default null,
--                         `age` int(11) default null,
--                         primary key (`id`)
-- )ENGINE=InnoDB default CHARSET=utf8
create table if not exists `user`(
                        `user_id` varchar(20) not null,
                        `user_pwd` varchar(20),
                        `user_name` varchar(1024),
                        `user_desp` varchar(1024),
                        `user_tel` varchar(1024),
                        `user_isleader` varchar(1024),
                        `user_class` varchar(1024),
                        primary key (`user_id`)
)ENGINE=InnoDB default CHARSET=utf8;

-- insert into user (user_id,user_pwd,user_name) values('admin','admin','admin');


create table if not exists `organization` (
                                `organization_id`      int not null auto_increment,
                                `organization_name`    varchar(1024),
                                `organization_leader_id` varchar(1024),
                                `organization_num`     int,
                                `organization_desp`    varchar(1024),
                                `organization_birth`   date,
                                `organization_class`   varchar(1024),
                                primary key (`organization_id`)
)ENGINE=InnoDB default CHARSET=utf8;

-- insert into organization (organization_name,organization_desp,organization_birth,organization_class,organization_num,organization_leader_id) values('ACM实验室','ACM实验室','2021-06-11','NULL',0,'NULL');

create table if not exists `posi` (
                        `posi_id` int not null auto_increment,
                        `posi_org` varchar(1024),
                        `posi_name` varchar(1024),
                        primary key (`posi_id`)
)ENGINE=InnoDB default CHARSET=utf8;

create table if not exists `cla` (
                       `class_id` int not null auto_increment,
                       `class_name` varchar(1024),
                       `class_used` int,
                       primary key (`class_id`)
)ENGINE=InnoDB default CHARSET=utf8;

create table if not exists `join_org_appl` (
                                `join_org_appl_id` int not null auto_increment,
                                `join_org_appl_user_id` varchar(1024),
                                `join_org_appl_user_class` varchar(1024),
                                `join_org_appl_user_tel` varchar(1024),
                                `join_org_appl_user_name` varchar(1024),
                                `join_org_appl_st`  int,
                                `join_org_appl_organization` varchar(1024),
                                `join_org_appl_date` date,
                                `join_org_appl_desp` varchar(1024),
                                primary key (`join_org_appl_id`)
)ENGINE=InnoDB default CHARSET=utf8;

create table if not exists `cr_org_appl` (
                              `cr_org_appl_id` int not null auto_increment,
                              `cr_org_appl_user_id` varchar(1024),
                              `cr_org_appl_user_class` varchar(1024),
                              `cr_org_appl_user_tel` varchar(1024),
                              `cr_org_appl_user_name` varchar(1024),
                              `cr_org_appl_st`  int,
                              `cr_org_appl_organization` varchar(1024),
                              `cr_org_appl_date` date,
                              `cr_org_appl_desp` varchar(1024),
                              primary key (`cr_org_appl_id`)
)ENGINE=InnoDB default CHARSET=utf8;

create table if not exists `cr_actv_appl` (
                               `cr_actv_appl_id` int not null auto_increment,
                               `cr_actv_appl_actv_name` varchar(1024),
                               `cr_actv_appl_begin_time` datetime,
                               `cr_actv_appl_end_time` datetime,
                               `cr_actv_appl_org_name` varchar(1024),
                               `cr_actv_appl_st` int,
                               `cr_actv_appl_desp` varchar(1024),
                               `cr_actv_appl_address` varchar(1024),
                               `cr_actv_appl_date` varchar(1024),
                               primary key (`cr_actv_appl_id`)
)ENGINE=InnoDB default CHARSET=utf8;

create table if not exists `actv` (
                        `actv_id` int not null auto_increment,
                        `actv_name` varchar(1024),
                        `actv_begin_time` datetime,
                        `actv_end_time` datetime,
                        `actv_organization` varchar(1024),
                        `actv_desp` varchar(1024),
                        `actv_address` varchar(1024),
                        primary key (`actv_id`)
)ENGINE=InnoDB default CHARSET=utf8;

create table if not exists `user_org` (
                            `user_org_id` int not null auto_increment,
                            `user_org_user_id` varchar(1024),
                            `user_org_org_name` varchar(1024),
                            `user_org_posi` varchar(1024),
                            primary key (`user_org_id`)
)ENGINE=InnoDB default CHARSET=utf8;

create table if not exists `user_actv` (
                             `user_actv_id` int not null auto_increment,
                             `user_actv_user_id` varchar(1024),
                             `user_actv_actv_id` int,
                             primary key (`user_actv_id`)
)ENGINE=InnoDB default CHARSET=utf8;

create table if not exists `ann` (
                       `ann_id` int not null auto_increment,
                       `ann_title` varchar(1024),
                       `ann_date` date,
                       `ann_user_id` varchar(1024),
                       `ann_st` int,
                       `ann_desp` varchar(1024),
                       primary key (`ann_id`)
)ENGINE=InnoDB default CHARSET=utf8;


create table if not exists `mee` (
    `mee_id` int not null auto_increment,
    `mee_org_name` varchar(1024),
    `mee_day` varchar(1024),
    `mee_st` varchar(1024),
    `mee_ed` varchar(1024),
    `mee_address` varchar(1024),
    primary key (`mee_id`)
)ENGINE=InnoDB default CHARSET=utf8;