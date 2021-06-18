package com.example.demo.mapper;


import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ApplMapper {

    //提交学生学号、申请状态、社团名称、申请日期新建加入社团申请
    @Insert("insert into join_org_appl (join_org_appl_user_id,join_org_appl_user_class,join_org_appl_user_tel,join_org_appl_user_name,join_org_appl_st,join_org_appl_organization,join_org_appl_date,join_org_appl_desp)values(#{user_id},#{user_class},#{user_tel},#{user_name},#{appl_st},#{org_name},#{appl_date},#{appl_desp})")
    int AddJoinOrgAppl (String user_id,String user_class,String user_tel,String user_name,String appl_st,String org_name,String appl_date,String appl_desp);

    //查询指定编号的加入社团申请
    @Select("select * from join_org_appl where join_org_appl_id = #{appl_id}")
    Map<String,String> QueryJoinOrgApplById (String appl_id);

    //通过申请，修改状态为1
    @Update("update join_org_appl set join_org_appl_st = 1 where join_org_appl_id = #{id}")
    int UpdateJoinOrgAppl1 (String id);

    //驳回申请，修改状态为2
    @Update("update join_org_appl set join_org_appl_st = 2 where join_org_appl_id = #{id}")
    int UpdateJoinOrgAppl2 (String id);

    //查询指定社团的所有加入社团申请
    @Select("select * from join_org_appl where join_org_appl_organization = #{org_name}")
    List<Map<String,Object>> QueryJoinApplByOrg (String org_name);

    //查询指定学号的所有加入社团申请
    @Select("select * from join_org_appl where join_org_appl_user_id = #{user_id}")
    List<Map<String, Object> > QueryJoinApplByUser (String user_id);

    //查询所有加入社团申请
    @Select("select * from join_org_appl")
    List<Map<String,Object> > QueryJoinAppl ();

    //查询指定学号的指定状态加入社团申请
    @Select("select * from join_org_appl where join_org_appl_user_id = #{user_id} and join_org_appl_st = #{appl_st}")
    List<Map<String,Object> > QueryJoinApplByUserSt (String user_id,String appl_st);

    //查询指定社团的指定状态加入社团申请
    @Select("select * from join_org_appl where join_org_appl_organization = #{org_name} and join_org_appl_st = #{appl_st}")
    List<Map<String,Object> > QueryJoinApplByOrgSt (String org_name,String appl_st);

    //返回所有未通过的社团申请表
    @Select("select * from join_org_appl where join_org_appl_st = 0")
    List<Map<String,Object> > QueryJoinOrgAppl2();

    //提交学号、状态、社团名称、申请理由，日期新建社团创建表
    @Insert("insert into cr_org_appl (cr_org_appl_user_id,cr_org_appl_user_class,cr_org_appl_user_tel,cr_org_appl_user_name,cr_org_appl_st,cr_org_appl_organization,cr_org_appl_desp,cr_org_appl_date) values(#{user_id},#{user_class},#{user_tel},#{user_name},#{appl_st},#{org_name},#{appl_desp},#{appl_date})")
    int AddCreateOrgAppl (String user_id,String user_class,String user_tel,String user_name,String appl_st,String org_name,String appl_desp,String appl_date);

    //查询指定编号的创建社团申请
    @Select("select * from cr_org_appl where cr_org_appl_id = #{appl_id}")
    Map<String,String> QueryCreateOrgApplById (String appl_id);

    //通过创建申请，修改状态为1
    @Update("update cr_org_appl set cr_org_appl_st = 1 where cr_org_appl_id = #{appl_id}")
    int UpdateCreateOrgAppl1 (String appl_id);

    //驳回申请，修改状态为2
    @Update("update cr_org_appl set cr_org_appl_st = 2 where cr_org_appl_id = #{appl_id}")
    int UpdateCreateOrgAppl2 (String appl_id);

    //根据学生学号展示所有申请
    @Select("select * from cr_org_appl where cr_org_appl_user_id = #{user_id}")
    List<Map<String,Object> > QueryCreateOrgApplByUser (String user_id);

    //根据学生学号展示指定状态的申请
    @Select("select * from cr_org_appl where cr_org_appl_user_id = #{user_id} and cr_org_appl_st = #{appl_st}")
    List<Map<String,Object> > QueryCreateOrgApplByUserSt (String user_id,String appl_st);

    //展示所有申请
    @Select("select * from cr_org_appl")
    List<Map<String,Object> > QueryCreateOrgAppl ();

    //展示所有申请
    @Select("select * from cr_org_appl where cr_org_appl_st = 0")
    List<Map<String,Object> > QueryCreateOrgAppl2 ();

    //提交活动名称、开始时间、结束时间、举办社团名称、活动地点、活动内容、申请时间、申请状态来新建活动申请
    @Insert("insert into cr_actv_appl (cr_actv_appl_actv_name,cr_actv_appl_begin_time,cr_actv_appl_end_time,cr_actv_appl_org_name,cr_actv_appl_address,cr_actv_appl_desp,cr_actv_appl_date,cr_actv_appl_st)values(#{actv_name},#{begin_time},#{end_time},#{org_name},#{appl_address},#{appl_desp},#{appl_date},#{appl_st})")
    int AddCreateActvAppl (String actv_name,String begin_time,String end_time,String org_name,String appl_address,String appl_desp,String appl_date,String appl_st);

    //查询指定编号的创建活动申请
    @Select("select * from cr_actv_appl where cr_actv_appl_id = #{appl_id}")
    Map<String,Object> QueryCreateActvApplById (String appl_id);


    //管理员通过活动申请，修改活动申请状态为1
    @Update("update cr_actv_appl set cr_actv_appl_st = 1 where cr_actv_appl_id = #{appl_id}")
    int UpdateCreateActvAppl1 (String appl_id);

    //管理员驳回活动申请，修改活动申请状态为2

    @Update("update cr_actv_appl set cr_actv_appl_st = 2 where cr_actv_appl_id = #{appl_id}")
    int UpdateCreateActvAppl2 (String appl_id);

    //根据举办社团名称展示当前所有活动申请
    @Select("select * from cr_actv_appl where cr_actv_appl_org_name = #{org_name}")
    List<Map<String,Object> > QueryCreateActvApplByOrg (String org_name);

    //根据举办社团名称展示当前指定状态活动申请
    @Select("select * from cr_actv_appl where cr_actv_appl_org_name = #{org_name} and cr_actv_appl_st = #{appl_st}")
    List<Map<String,Object> > QueryCreateActvApplByOrgSt (String org_name,String appl_st);

    //展示所有活动申请
    @Select("select * from cr_actv_appl")
    List<Map<String,Object> > QueryCreateActvAppl ();

    //展示指定状态活动申请
    @Select("select * from cr_actv_appl where cr_actv_appl_st = #{appl_st}")
    List<Map<String,Object> > QueryCreateActvApplBySt (String appl_st);
}
