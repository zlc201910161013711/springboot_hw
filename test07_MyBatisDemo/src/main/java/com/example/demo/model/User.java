package com.example.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String user_id;//学号
    private String user_name;//姓名
    private String user_pwd;//密码
    private String user_tel;//电话
    private String user_desp;//自我介绍
    private String user_birthdate;//出生日期
    private String user_ismanager;//是否是管理员
    private String user_class;//所在班级
    private int user_isleader;//返回学生管理的社团编号，如果没有
    List<Organization> organizationList;//加入的社团列表
    List<Actv> actvList;//参加了哪些活动
    private String user_posi;//在社团里担任职位
}