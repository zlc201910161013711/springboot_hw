package com.example.demo.model;


import lombok.Data;

@Data
public class Posi {
    private int posi_id;//编号
    private int posi_org;//职位所属社团
    private String posi_user_id;//职位所属学生学号
    private String posi_name;//职位名称
}
