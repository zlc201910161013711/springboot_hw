package com.example.demo.mapper;

import com.example.demo.model.User;
import com.example.demo.result.ResponseData;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface UserOrgMapper {


    //查询该学生参加了什么社团，返回一个社团id的列表
    @Select("select user_org_org_name from user_org where user_org_user_id = #{userid}")
    List<String> getJoinedOrg(String user_id);

    //
}
