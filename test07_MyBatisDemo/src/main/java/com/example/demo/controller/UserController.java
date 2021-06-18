package com.example.demo.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.demo.mapper.ActvMapper;
import com.example.demo.mapper.OrgMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserOrgMapper;
import com.example.demo.model.Actv;
import com.example.demo.model.Organization;
import com.example.demo.model.User;
import com.example.demo.result.ExceptionMsg;
import com.example.demo.result.Response;
import com.example.demo.result.ResponseData;
import net.minidev.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    OrgMapper orgMapper;

    @Autowired
    UserOrgMapper userOrgMapper;

    @Autowired
    ActvMapper actvMapper;

    @CrossOrigin
    @PostMapping("/login")
    ResponseData login(@RequestBody Map<String,String> map) {
        //登录接口
        String id = map.get("id");
        String pwd = map.get("pwd");
        Map<String,Object> ans = userMapper.queryById(id);
        if (ans == null) return new ResponseData(ExceptionMsg.FAILED,"用户不存在！");
        if (!ans.get("user_pwd").equals(pwd)) return new ResponseData(ExceptionMsg.FAILED,"密码错误！");
        return new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS");
    }



    @CrossOrigin
    @PostMapping("/querybyid")

    ResponseData queryById(@RequestBody Map<String, String> map) {
        String id = map.get("user_id");
        Map<String,Object> ans = userMapper.queryById(id);
        if (ans.get("user_class") == null) ans.put("user_class","NULL");
        if (ans.get("user_isleader") == null) ans.put("user_isleader","NULL");
        if (ans.get("user_tel") == null) ans.put("user_tel","NULL");
        if (ans.get("user_desp") == null) ans.put("user_desp","NULL");
        if (ans == null) return new ResponseData(ExceptionMsg.FAILED,ans);
        List<String> organizationList = userOrgMapper.getJoinedOrg(id);

        ans.put("OrganizationList",organizationList);//学生参加社团列表

        List<String> tt = actvMapper.QueryActvIdByUser(id);

        List<Map<String,Object> > actvList = new ArrayList<>();
        for (int i=0;i<tt.size();i++) {
            Map<String,Object> item = actvMapper.SelectActvById(tt.get(i));
                String ttt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)item.get("actv_begin_time"));
                System.out.println(tt);
                for (int j=0;j<ttt.length();j++) {
                    if (ttt.charAt(j)==' ') {
                        ttt=ttt.substring(0,j);
                        break;
                    }
                }
                item.put("actv_begin_time",ttt);

                ttt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)item.get("actv_end_time"));
                for (int j=0;j<ttt.length();j++) {
                    if (ttt.charAt(j)==' ') {
                        ttt=ttt.substring(0,j);
                        break;
                    }
                }
                item.put("actv_end_time",ttt);
            actvList.add(item);
        }

        ans.put("ActvList",actvList);//学生参加活动列表

        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("/register")
    ResponseData register(@RequestBody Map<String, String> map) {
        //添加用户，返回用户添加是否成功
        //添加用户的学号、姓名、密码
        String id = map.get("user_id");
        String name = map.get("user_name");
        String pwd = map.get("user_pwd");

        ResponseData tt = queryById(map);
        if (tt.getData() != null) return new ResponseData(ExceptionMsg.FAILED,"学号已经存在！");

        return userMapper.add(id,name,pwd) == 1 ? new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS") : new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("/deleteuser")
    ResponseData DeleteUser (@RequestBody Map<String,String> mp) {
        //提交学号删除学生
        String user_id = mp.get("user_id");
        int x=userMapper.DeleteUser(user_id);
        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("/modify")
    ResponseData Modify (@RequestBody Map<String,String> map) {
        //修改学生信息、修改密码都用这个接口
        String id=map.get("user_id");
        String name=map.get("user_name");
        String pwd=map.get("user_pwd");
        String tel=map.get("user_tel");
        String desp=map.get("user_desp");
        String user_class=map.get("user_class");
        //if (name.length()>20) return new ResponseData(ExceptionMsg.FAILED,"姓名过长");
        if (pwd.length()<3) return new ResponseData(ExceptionMsg.FAILED,"密码长度过短");
        if (pwd.length()>20) return new ResponseData(ExceptionMsg.FAILED,"密码长度过长");
        ResponseData tt=queryById(map);
        if (tt.getData()==null) return new ResponseData(ExceptionMsg.FAILED,"用户不存在！");
        return userMapper.updatePwdById(id,pwd)==1&&
                userMapper.updateNameById(id,name)==1&&
                userMapper.updateTelById(id,tel)==1&&
                userMapper.updatePwdById(id,pwd)==1&&
                userMapper.updateDespById(id,desp)==1&&
                userMapper.updateClassById(user_class,id)==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("/changepwd")
    ResponseData changepwd(@RequestBody Map<String, String> map){
        String id = map.get("id");
        String pwd = map.get("pwd");
        int ret = userMapper.updatePwdById(id, pwd);
        if (ret==1){
            return new ResponseData(ExceptionMsg.SUCCESS, "SUCCESS");
        } else {
            return new ResponseData(ExceptionMsg.FAILED, "FAILED");
        }
    }

//    @CrossOrigin
//    @PostMapping("/joinorg")
//    ResponseData joinorg(@RequestBody Map<String, String> map){
//        String id = map.get("id");
//        String orgname = map.get("orgname");
//        ResponseData ret;
//        String orgid = orgMapper.getOrgNameByName(orgname);
//        if (userMapper.joinOrg(id, orgid)==1){
//            ret = new ResponseData(ExceptionMsg.SUCCESS);
//        } else {
//            ret = new ResponseData(ExceptionMsg.FAILED);
//        }
//        return ret;
//    }

    @CrossOrigin
    @PostMapping("/getuserorg")
    ResponseData getUserOrg(@RequestBody Map<String, String> map){
        String id = map.get("id");
        List<String> orgs = userOrgMapper.getJoinedOrg(id);
        return new ResponseData(orgs);
    }
}
