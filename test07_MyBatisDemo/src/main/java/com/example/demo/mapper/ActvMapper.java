package com.example.demo.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ActvMapper {

    //提交活动信息新建活动
    @Insert("insert into actv (actv_name,actv_begin_time,actv_end_time,actv_organization,actv_desp,actv_address) values(#{actv_name},#{actv_begin_time},#{actv_end_time},#{actv_organization},#{actv_desp},#{actv_address})")
    int AddActv (String actv_name,String actv_begin_time,String actv_end_time,String actv_organization,String actv_desp,String actv_address);

    //提交活动编号删除活动
    @Delete("delete from actv where actv_id = #{actv_id}")
    int DeleteActv (String actv_id);

    //删除指定活动的成员列表
    @Delete("delete from user_actv where user_actv_actv_id = #{actv_id}")
    int DeleteUserActv (String actv_id);

    //提交社团名称查看所有活动
    @Select("select * from actv where actv_organization = #{actv_org}")
    List<Map<String,Object>> SelectActvByOrg (String actv_org);

    //查看所有活动
    @Select("select * from actv")
    List<Map<String,Object> > SelectActv ();

    //提交活动名称查看活动信息
    @Select("select * from actv where actv_id = #{actv_id}")
    Map<String,Object> SelectActvById (String actv_id);

    //通过提交活动编号查看参与活动的所有学生学号
    @Select("select user_actv_user_id from user_actv where user_actv_actv_id = #{actv_id}")
    List<String> SelectUserIdByActv (String actv_id);

    //通过提交活动编号、学生学号来参加活动
    @Insert("insert into user_actv (user_actv_user_id,user_actv_actv_id) values(#{user_id},#{actv_id})")
    int JoinToActv (String user_id,String actv_id);

    //查询指定学生参加了哪些活动，返回活动的编号
    @Select("select user_actv_actv_id from user_actv where user_actv_user_id = #{user_id}")
    List<String> QueryActvIdByUser (String user_id);
}
