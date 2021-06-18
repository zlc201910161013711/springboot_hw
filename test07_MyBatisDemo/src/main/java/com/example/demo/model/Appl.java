package com.example.demo.model;


import lombok.Data;

@Data
public class Appl {
    private int appl_id;//申请表编号
    private String appl_user_id;//提交申请的学生
    private int appl_st;//申请状态（加入或退出）
    private int appl_organization;//申请的目标社团
    private String appl_date;//申请提交的时间
}


