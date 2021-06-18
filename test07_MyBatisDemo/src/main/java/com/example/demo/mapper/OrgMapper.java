package com.example.demo.mapper;

import com.example.demo.model.Organization;
import com.example.demo.model.User;
import com.example.demo.result.ResponseData;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrgMapper {



//    @Select("select * from user where user_id = #{id}")
//    User queryById(@Param("id") String id);

    //添加一个社团
    @Insert("insert into organization (organization_name,organization_birth,organization_leader_id,organization_num,organization_desp,organization_class) values(#{org_name},#{org_birth},'NULL',0,#{org_desp},'NULL')")
    int addOrg (String org_name,String org_desp,String org_birth);

    //查看所有社团
    @Select("select * from organization")
    List<Map<String,Object> > AllOrg ();


    //查询指定编号的社团是否存在
    @Select("select * from organization where organization_name = #{org_name}")
    Map<String,Object> queryOrgByName(String org_name);


    //往学生-社团表里插入一项
    @Insert("insert into user_org (user_org_user_id,user_org_org_id) values(#{user_id},#{org_id})")
    int addUser_org (@Param("user_id") String user_id,@Param("org_id") String org_id);

    //查询学生是否已经加入一个社团
    @Select("select * from user_org where user_org_user_id = #{user_id} and user_org_org_name = #{org_name}")
    List<Map<String,Object> > CheckUserInOrg (String user_id,String org_name);


    //修改社团人数+1
    @Update("update organization set organization_num = organization_num + 1 where organization_name = #{org_name}")
    int upOrgNum (@Param("org_name") String org_name);

    //根据社团名称搜索社长学号
    @Select("select organization_leader_id from organization where organization_name = #{orgname}")
    String getLeaderidByName(String orgname);

    //根据社团名称修改社团的描述
    @Update("update organization set organization_desp = #{org_desp} where organization_name = #{org_name}")
    int UpdateOrgDespByName (String org_desp, String org_name);

    //根据社团名称删除社团
    @Delete("delete from organization where organization_name = #{org_name}")
    int DeleteOrgByName (String org_name);

    //修改一个社团的社长
    @Update("update organization set organization_leader_id = #{user_id} where organization_name = #{org_name} ")
    int UpdateOrgLeaderByName (String user_id,String org_name);

    //修改一个学生的isleader属性为指定社团名称
    @Update("update user set user_isleader = #{org_name} where user_id = #{user_id}")
    int UpdateUserIsLeaderById (String org_name,String user_id);

    //查询社团当前参与成员的id列表
    @Select("select user_org_user_id from user_org where user_org-org_id = #{org_id}")
    List<String> QueryUserOrgByName (String org_id);

    //学生参加一个社团
    @Insert("insert into user_org (user_org_user_id,user_org_org_name,user_org_posi) values (#{user_id},#{org_name},#{org_posi})")
    int InsertUserOrg (String user_id,String org_name,String org_posi);

    //查询一个学生是否已经在社团里
    @Select("select * from user_org where user_org_user_id = #{user_id} and user_org_org_name = #{org_name}")
    List<Map<String,String> > SelectUserByOrgName (String user_id,String org_name);

    //查询一个社团的所有学生列表，返回学生学号和职位
    @Select("select user_org_user_id,user_org_posi from user_org where user_org_org_name = #{org_name}")
    List<Map<String,Object>> SelectAllUserByOrgName (String org_name);

    //提交学生-社团-关联编号，更新职位
    @Update("update user_org set user_org_posi = #{posi_name} where user_org_user_id = #{user_id} and user_org_org_name = #{org_name}")
    int UpdateUserOrgPosi (String posi_name,String user_id,String org_name);


    //从一个社团中删除学生
    @Delete("delete from user_org where user_org_user_id = #{user_id} and user_org_org_name = #{org_name}")
    int DeleteUserFromOrg (String user_id,String org_name);

    //给一个社团添加一个组会时间
    @Insert("insert into mee (mee_org_name,mee_day,mee_st,mee_ed,mee_address)values(#{mee_org_name},#{mee_day},#{mee_st},#{mee_ed},#{mee_address})")
    int InsertMeeToOrg (String mee_org_name,String mee_day,String mee_st,String mee_ed,String mee_address);

    //给一个社团删除一个组会时间
    @Delete("delete from mee where mee_id = #{mee_id}")
    int DeleteMeeFromOrg (String mee_id);

    //修改一个社团的组会时间地点
    @Update("update mee set mee_day = #{mee_day},mee_st = #{mee_st},mee_ed = #{mee_ed},mee_address = #{mee_address} where mee_id = #{mee_id}")
    int UpateMeeFromOrg (String mee_day,String mee_st,String mee_ed,String mee_address,String mee_id);

    //查询所有组会时间
    @Select("select * from mee")
    List<Map<String,Object> > SelectMee ();


    //查询指定社团的组会时间
    @Select("select * from mee where mee_org_name = #{mee_org_name}")
    List<Map<String,Object> > SelectMeeFromOrg (String mee_org_name);

    //通过提交社团名称、职位名称新建职位
    @Insert("insert into posi (posi_org,posi_name) values (#{posi_org},#{posi_name})")
    int AddPosi (String posi_org,String posi_name);

    //通过提交职位名称，职位编号，修改职位信息
    @Update("update posi set posi_name = #{posi_name} where posi_id = #{posi_id}")
    int UpdatePosi (String posi_id,String posi_name);

    //通过提交职位编号删除职位
    @Delete("delete from posi where posi_id = #{posi_id}")
    int DeletePosi (String posi_id);

    //展示一个社团的所有职位列表
    @Select("select * from posi where posi_org = #{posi_org}")
    List<Map<String,Object> > SelectPosiByOrg (String posi_org);


}
