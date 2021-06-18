package com.example.demo.model;


import lombok.Data;

@Data
public class Actv {
    private int actv_id;//编号
    private String actv_name;//名称
    private String actv_begin_time;//活动开始的时间
    private String actv_end_time;//活动结束的时间
    private int actv_organization;//活动举办的社团
}
