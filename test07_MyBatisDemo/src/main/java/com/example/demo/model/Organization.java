package com.example.demo.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
public class Organization {
    //社团类
    private int organization_id;//社团编号
    private String organization_name;//社团名称
    private int organization_leader_id;//社长学号
    private int organization_num;//社团人数
    private String organization_desp;//社团描述
    private String organization_birth;//创建日期
    private String organization_class;//活动教室
}
