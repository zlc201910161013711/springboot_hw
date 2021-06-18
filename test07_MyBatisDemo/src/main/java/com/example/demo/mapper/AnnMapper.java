package com.example.demo.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface AnnMapper {

    //通过提交公告信息新建公告
    @Insert("insert into ann (ann_title,ann_user_id,ann_desp,ann_st,ann_date) values(#{ann_title},#{ann_user_id},#{ann_desp},#{ann_st},#{ann_date})")
    int AddAnn (String ann_title,String ann_user_id,String ann_desp,String ann_st,String ann_date);

    //通过提交公告编号查看公告
    @Select("select * from ann where ann_id = #{ann_id}")
    Map<String,Object> SelectAnnById (String ann_id);

    //通过提交公告编号、公告标题、公告内容，公告状态修改公告
    @Update("update ann set ann_title = #{ann_title},ann_desp = #{ann_desp},ann_st = #{ann_st} where ann_id = #{ann_id}")
    int UpdateAnnById (String ann_title,String ann_desp,String ann_st,String ann_id);

    //根据发布人ID查询他发布的所有公告
    @Select("select * from ann where ann_user_id =#{user_id}")
    List<Map<String,Object> > SelectAnnByUser (String user_id);

    //查询所有公告
    @Select("select * from ann")
    List<Map<String,Object> > SelectAnn ();

    //查询指定状态的公告、上架、下架
    @Select("select * from ann where ann_st = #{ann_st}")
    List<Map<String,Object> > SelectAnnBySt (String ann_st);
}
