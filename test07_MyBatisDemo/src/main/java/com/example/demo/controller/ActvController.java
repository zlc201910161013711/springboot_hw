package com.example.demo.controller;


import com.example.demo.mapper.ActvMapper;
import com.example.demo.mapper.OrgMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.result.ExceptionMsg;
import com.example.demo.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actv")
public class ActvController {

    @Autowired
    ActvMapper actvMapper;

    @Autowired
    OrgMapper orgMapper;

    @Autowired
    UserMapper userMapper;

    @CrossOrigin
    @PostMapping("addactv")
    ResponseData AddActv (@RequestBody Map<String,String> mp) {
        //提交活动信息新建活动

        System.out.println(mp.get("actv_name")+ " " + mp.get("actv_org_name")+ " " + mp.get("actv_desp") + " " + mp.get("actv_address"));



        String actv_name = mp.get("actv_name");
        String actv_begin_time = mp.get("actv_begin_time");
        String actv_end_time = mp.get("actv_end_time");
        String actv_org_name = mp.get("actv_org_name");
        String actv_desp = mp.get("actv_desp");
        String actv_address = mp.get("actv_address");




        int x = actvMapper.AddActv(actv_name,actv_begin_time,actv_end_time,actv_org_name,actv_desp,actv_address);
        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("deleteactv")
    ResponseData DeleteActv (@RequestBody Map<String,String> mp) {
        //提交活动编号删除活动
        String actv_id = mp.get("actv_id");
        int x=actvMapper.DeleteUserActv(actv_id);
        int y=actvMapper.DeleteActv(actv_id);
        //System.out.println(y);
        return y == 1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("allorgactv")
    ResponseData AllOrgActv (@RequestBody Map<String,String> mp) {
        //通过提交社团名称返回所有该社团的活动信息
        String org_name = mp.get("org_name");
        List<Map<String,Object> > ans = actvMapper.SelectActvByOrg(org_name);
        for (int i=0;i<ans.size();i++) {
            String tt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)ans.get(i).get("actv_begin_time"));
            System.out.println(tt);
            for (int j=0;j<tt.length();j++) {
                if (tt.charAt(j)==' ') {
                    tt=tt.substring(0,j);
                    break;
                }
            }
            ans.get(i).put("actv_begin_time",tt);

            tt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)ans.get(i).get("actv_end_time"));
            for (int j=0;j<tt.length();j++) {
                if (tt.charAt(j)==' ') {
                    tt=tt.substring(0,j);
                    break;
                }
            }
            ans.get(i).put("actv_end_time",tt);
        }
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }
    @CrossOrigin
    @PostMapping("allactv")
    ResponseData AllActv () {
        List<Map<String,Object> > ans = actvMapper.SelectActv();
        List<Map<String,Object> > ans1 = new ArrayList<>();
        for (int i=0;i<ans.size();i++) {
            String tt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)ans.get(i).get("actv_begin_time"));
            System.out.println(tt);

            for (int j=0;j<tt.length();j++) {
                if (tt.charAt(j)==' ') {
                    tt=tt.substring(0,j);
                    break;
                }
            }
            ans.get(i).put("actv_begin_time",tt);

            tt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)ans.get(i).get("actv_end_time"));
            for (int j=0;j<tt.length();j++) {
                if (tt.charAt(j)==' ') {
                    tt=tt.substring(0,j);
                    break;
                }
            }
            ans.get(i).put("actv_end_time",tt);

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_time = sdf.format(date);

            if (((String)ans.get(i).get("actv_end_time")).compareTo ((String)date_time) == 1) {
                ans1.add(ans.get(i));
            }

        }
        return new ResponseData(ExceptionMsg.SUCCESS,ans1);
    }



    @CrossOrigin
    @PostMapping("allactvuser")
    ResponseData AllActvUser (@RequestBody Map<String,String> mp) {
        //通过提交活动编号返回成员id
        String actv_id = mp.get("actv_id");
        List<String> ans = actvMapper.SelectUserIdByActv(actv_id);
        List<Map<String,Object> > ans1 = new ArrayList<>();
        for (int i=0;i<ans.size();i++) {
            Map<String,Object> tt = userMapper.queryById(ans.get(i));
            ans1.add(tt);
        }
        return new ResponseData(ExceptionMsg.SUCCESS,ans1);
    }


    @CrossOrigin
    @PostMapping("jointoactv")
    ResponseData JoinToActv (@RequestBody Map<String,String> mp) {
        //通过提交活动编号、成员编号来参加活动
        String actv_id = mp.get("actv_id");
        String user_id = mp.get("user_id");
        int x = actvMapper.JoinToActv(user_id,actv_id);
        return x == 1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }
}
