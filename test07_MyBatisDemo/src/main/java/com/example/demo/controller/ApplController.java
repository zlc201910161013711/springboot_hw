package com.example.demo.controller;


import com.example.demo.mapper.ActvMapper;
import com.example.demo.mapper.ApplMapper;
import com.example.demo.mapper.OrgMapper;
import com.example.demo.result.ExceptionMsg;
import com.example.demo.result.ResponseData;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/appl")
public class ApplController {

    @Autowired
    ActvMapper actvMapper;

    @Autowired
    OrgMapper orgMapper;

    @Autowired
    ApplMapper applMapper;


    @Autowired
    OrgController orgController;

    @Autowired
    ActvController actvController;

    @CrossOrigin
    @PostMapping("addjoinorg")
    ResponseData AddJoinOrgAppl (@RequestBody Map<String,String> mp)  {
        //新增一个加入社团的申请
        String user_id = mp.get("user_id");
        String org_name = mp.get("org_name");
        String user_class = mp.get("user_class");
        String user_tel = mp.get("user_tel");
        String user_name = mp.get("user_name");
        String appl_desp = mp.get("appl_desp");

        String appl_st = "0";
        String appl_date = null;

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        appl_date = sdf.format(date);

        int x=applMapper.AddJoinOrgAppl(user_id,user_class,user_tel,user_name,appl_st,org_name,appl_date,appl_desp);
        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");

    }

    @CrossOrigin
    @PostMapping("passjoinorg")
    ResponseData PassJoinOrgAppl (@RequestBody Map<String,String> mp) {
        String appl_id = mp.get("appl_id");
        int x=applMapper.UpdateJoinOrgAppl1(appl_id);
        Map<String,String> tt = applMapper.QueryJoinOrgApplById(appl_id);
        System.out.println(appl_id);
        Map<String,String> mp1 = new TreeMap<>();
        if (tt == null) System.out.println("wawa");
        mp1.put("user_id",tt.get("join_org_appl_user_id"));
        mp1.put("org_name",tt.get("join_org_appl_organization"));
        System.out.println(mp1.get("user_id")+" "+mp1.get("org_name"));
        ResponseData ans = orgController.AddUserToOrg(mp1);
        return ans;
    }

    @CrossOrigin
    @PostMapping("notpassjoinorg")
    ResponseData NotPassJoinOrgAppl (@RequestBody Map<String,String> mp) {
        String appl_id = mp.get("appl_id");
        int x=applMapper.UpdateJoinOrgAppl2(appl_id);
        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("alljoinorg")
    ResponseData AllJoinOrgAppl () {
        List<Map<String,Object> > ans = applMapper.QueryJoinAppl();
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("alljoinorg2")
    ResponseData AllJoinOrgAppl2 (){
        List<Map<String,Object> > ans = applMapper.QueryJoinOrgAppl2();
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("byuserst")
    ResponseData QueryJoinOrgApplByUserSt (@RequestBody Map<String,String> mp) {
        //展示指定学号的指定状态的社团加入申请
        String user_id = mp.get("user_id");
        String appl_st = mp.get("appl_st");
        List<Map<String,Object> > ans = applMapper.QueryJoinApplByUserSt(user_id,appl_st);
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }


    @CrossOrigin
    @PostMapping("byuser")
    ResponseData QueryJoinOrgApplByUser (@RequestBody Map<String,String> mp) {
        //展示指定学号的所有社团加入申请
        String user_id = mp.get("user_id");
        List<Map<String,Object> > ans = applMapper.QueryJoinApplByUser(user_id);
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("byorgst")
    ResponseData QueryJoinOrgApplByOrgSt (@RequestBody Map<String,String> mp) {
        //展示指定社团的不同状态的社团申请
        String org_name = mp.get("org_name");
        String appl_st = mp.get("appl_st");
        List<Map<String,Object> > ans = applMapper.QueryJoinApplByOrgSt(org_name,appl_st);
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("byorg")
    ResponseData QueryJoinOrgApplByOrg (@RequestBody Map<String,String> mp) {
        //展示指定社团的所有申请
        String org_name = mp.get("org_name");
        List<Map<String,Object> > ans = applMapper.QueryJoinApplByOrg(org_name);
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("createorg")
    ResponseData AddCreateOrg (@RequestBody Map<String,String> mp) {
        //提交学生学号、社团名称新建创建社团申请
        String user_id = mp.get("user_id");
        String org_name = mp.get("org_name");
        String appl_desp = mp.get("appl_desp");
        String user_class = mp.get("user_class");
        String user_tel = mp.get("user_tel");
        String user_name = mp.get("user_name");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String appl_date = sdf.format(date);

        int x = applMapper.AddCreateOrgAppl(user_id,user_class,user_tel,user_name,"0",org_name,appl_desp,appl_date);
        return x == 1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("passcreateorg")
    ResponseData PassCreateOrgById (@RequestBody Map<String,String> mp) {
        //提交申请表编号，通过创建社团的申请
        String appl_id = mp.get("appl_id");
        Map<String,String> tt = applMapper.QueryCreateOrgApplById(appl_id);
        int x=applMapper.UpdateCreateOrgAppl1(appl_id);
        System.out.println(x);
        Map<String,String> mp1 = new TreeMap<>();
        mp1.put("org_name",tt.get("cr_org_appl_organization"));
        mp1.put("user_id",tt.get("cr_org_appl_user_id"));
        mp1.put("org_desp",tt.get("cr_org_appl_desp"));
        ResponseData ans = orgController.AddOrg(mp1);
        return ans;
    }

    @CrossOrigin
    @PostMapping("notpasscreateorg")
    ResponseData NotPassCreateOrgById (@RequestBody Map<String,String> mp) {
        //提交申请表编号，通过创建社团的申请
        String appl_id = mp.get("appl_id");
        Map<String,String> tt = applMapper.QueryCreateOrgApplById(appl_id);
        int x=applMapper.UpdateCreateOrgAppl2(appl_id);
        System.out.println(x);
        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("createorgbyuser")
    ResponseData CreateOrgByUser (@RequestBody Map<String,String> mp) {
        //提交学生学号返回所有申请
        String user_id = mp.get("user_id");
        List<Map<String,Object> > ans = applMapper.QueryCreateOrgApplByUser(user_id);
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("createorgbyuserst")
    ResponseData CreateOrgByUserSt (@RequestBody Map<String,String> mp) {
        //提交学生学号返回所有申请
        String user_id = mp.get("user_id");
        String appl_st = mp.get("appl_st");
        List<Map<String,Object> > ans = applMapper.QueryCreateOrgApplByUserSt(user_id,appl_st);
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("allcreateorg")
    ResponseData AllCreateOrg () {
        //展示所有创建社团的申请
        List<Map<String,Object> > ans = applMapper.QueryCreateOrgAppl();
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("allcreateorg2")
    ResponseData AllCreateOrg2 () {
        //展示所有未通过创建社团的
        List<Map<String,Object> > ans = applMapper.QueryCreateOrgAppl2();
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }



    @CrossOrigin
    @PostMapping("createactv")
    ResponseData CreateActv (@RequestBody Map<String,String> mp) {
        String actv_name = mp.get("actv_name");
        String begin_time = mp.get("begin_time");
        String end_time = mp.get("end_time");
        String org_name = mp.get("org_name");
        String appl_st = "0";
        String appl_desp = mp.get("appl_desp");
        String appl_address = mp.get("appl_address");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String appl_date = sdf.format(date);

        int x = applMapper.AddCreateActvAppl(actv_name,begin_time,end_time,org_name,appl_address,appl_desp,appl_date,appl_st);
        return x == 1 ? new ResponseData(ExceptionMsg.SUCCESS,"SUCESS"): new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("passcractv")
    ResponseData PassCreateActv (@RequestBody Map<String,String> mp) {
        String appl_id = mp.get("appl_id");
        int x = applMapper.UpdateCreateActvAppl1(appl_id);
        Map<String,Object> mp2 = applMapper.QueryCreateActvApplById(appl_id);
        Map<String,String> mp1 = new TreeMap<>();
//        String actv_name = mp.get("actv_name");
//        String actv_begin_time = mp.get("actv_begin_time");
//        String actv_end_time = mp.get("actv_end_time");
//        String actv_org_name = mp.get("actv_org_name");
//        String actv_desp = mp.get("actv_desp");
//        String actv_address = mp.get("actv_address");
        mp1.put("actv_name",(String)mp2.get("cr_actv_appl_actv_name"));
        mp1.put("actv_begin_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mp2.get("cr_actv_appl_begin_time")));
        mp1.put("actv_end_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mp2.get("cr_actv_appl_begin_time")));
        mp1.put("actv_org_name",(String)mp2.get("cr_actv_appl_org_name"));
        mp1.put("actv_desp",(String)mp2.get("cr_actv_appl_desp"));
        mp1.put("actv_address",(String)mp2.get("cr_actv_appl_address"));

        System.out.println(mp1.get("actv_name")+ " " + mp1.get("actv_org_name")+ " " + mp1.get("actv_desp") + " " + mp1.get("actv_address"));
        return actvController.AddActv(mp1);
    }

    @CrossOrigin
    @PostMapping("notpasscractv")
    ResponseData NotPassCreateActv (@RequestBody Map<String,String> mp) {
        String appl_id = mp.get("appl_id");
        int x = applMapper.UpdateCreateActvAppl2(appl_id);
        return x == 1 ? new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"): new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("allcractvbyorg")
    ResponseData AllCreateActvByOrg (@RequestBody Map<String,String> mp) {
        String org_name = mp.get("org_name");
        List<Map<String,Object> > ans = applMapper.QueryJoinApplByOrg(org_name);
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("allcractvbyorgst")
    ResponseData AllCreateActvByOrgSt (@RequestBody Map<String,String> mp) {
        String org_name = mp.get("org_name");
        String appl_st = mp.get("appl_st");
        List<Map<String,Object> > ans = applMapper.QueryJoinApplByOrgSt(org_name,appl_st);
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("allcractv")
    ResponseData AllCreateActv () {
        List<Map<String,Object> > ans = applMapper.QueryCreateActvAppl();
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("allcractvst")
    ResponseData AllCreateActvBySt (@RequestBody Map<String,String> mp) {
        String appl_st = mp.get("appl_st");
        List<Map<String,Object> > ans = applMapper.QueryCreateActvApplBySt(appl_st);
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }
}
