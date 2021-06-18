package com.example.demo.mapper;

import com.example.demo.model.User;
import com.example.demo.result.ResponseData;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {


    //根据id查询用户
    @Select("select * from user where user_id = #{id}")
    Map<String,Object> queryById(@Param("id") String id);


    //注册新用户，插入一条新的用户信息
    @Insert({"insert into user(user_id,user_name,user_pwd,user_desp,user_tel,user_isleader,user_class) values(#{id},#{name},#{pwd},'NULL','NULL','NULL','NULL')"})
    int add(String id,String name,String pwd);

    //根据id修改用户密码
    @Update("update user set user_pwd = #{pwd} where user_id = #{id}")
    int updatePwdById(String id,String pwd);

    //根据id修改用户班级
    @Update("update user set user_class = #{user_class} where user_id = #{user_id}")
    int updateClassById(String user_class,String user_id);

    @Update("update user set user_name = #{name} where user_id = #{id}")
    int updateNameById(String id,String name);

    @Update("update user set user_tel = #{tel} where user_id = #{id}")
    int updateTelById(String id,String tel);

    @Update("update user set user_desp = #{desp} where user_id = #{id}")
    int updateDespById(String id,String desp);

    @Insert("insert into user_org (user_org_user_id, user_org_org_id) values (#{userid}, #{orgid})")
    int joinOrg(String userid, String orgid);

    @Delete("delete from user where user_id = #{user_id}")
    int DeleteUser (String user_id);

//    @Select("SELECT * FROM user WHERE id = #{id}")
//    User queryById(@Param("id") int id);

//    @Select("SELECT * FROM user")
//    List<User> queryAll();
//
//    @Insert({"INSERT INTO user(name,age) VALUES(#{name},#{age})"})
//    int add(User user);
//
//    @Delete("DELETE FROM user WHERE id = #{id}")
//    int delById(int id);
//
//    @Update("UPDATE user SET name=#{name},age=#{age} WHERE id = #{id}")
//    int updateById(User user);
}
