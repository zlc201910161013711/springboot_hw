package com.example.demo.controller;


import com.example.demo.mapper.OrgMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.result.ExceptionMsg;
import com.example.demo.result.Response;
import com.example.demo.result.ResponseData;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.io.StringWriter;
import java.rmi.MarshalledObject;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/org")
public class OrgController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    OrgMapper orgMapper;

    @CrossOrigin
    @PostMapping("add")
    ResponseData AddOrg (@RequestBody  Map<String,String> mp) {
        //添加一个社团
        //需要提交社团名称和社长学号
        //日期自动创建为当前日期

        String org_name=mp.get("org_name");
        String user_id = mp.get("user_id");
        String org_desp = mp.get("org_desp");
        String org_birth=null;
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        org_birth=sdf.format(date);
        System.out.println(org_name+" "+org_birth);
        int x=orgMapper.addOrg(org_name,org_desp,org_birth);
        ResponseData tt=this.UpdateOrgLeader(mp);

        mp.put("org_posi","社长");
        ResponseData ttt=this.AddUserToOrg(mp);

        //自动插入会长和副会长

        int xx=orgMapper.AddPosi(org_name,"社长");
        int yy=orgMapper.AddPosi(org_name,"副社长");
        //查询社团的当前编号
        return x==1? new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"): new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }


    @CrossOrigin
    @PostMapping("allorg")
    ResponseData AllOrg () {
        List<Map<String,Object> > ans = orgMapper.AllOrg();
        for (int i=0;i<ans.size();i++) {
            List<Map<String,Object> > item = orgMapper.SelectMeeFromOrg((String)ans.get(i).get("organization_name"));
            ans.get(i).put("meetinglist",item);
        }
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }



    @CrossOrigin
    @PostMapping("modify_desp")
    ResponseData UpdateOrgDespByName (@RequestBody Map<String,String> mp) {
        //通过名字修改社团的描述
        String org_desp = mp.get("org_desp");
        String org_name = mp.get("org_name");
        System.out.println(org_desp+" "+org_name);
        int x=orgMapper.UpdateOrgDespByName(org_desp,org_name);
        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("delete_org")
    ResponseData DeleteOrgByName (@RequestBody Map<String,String> mp) {
        //通过名字删除社团
        String org_name = mp.get("org_name");
        int x=orgMapper.DeleteOrgByName(org_name);
        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("update_leader")
    ResponseData UpdateOrgLeader (@RequestBody Map<String,String> mp) {
        //转让社团的社长
        //原社长的isleader属性清空
        //新社长的isleader属性置为该社团的名称
        //社团的社长学号改成新社长
        String org_name = mp.get("org_name");
        String old_leader_id = orgMapper.getLeaderidByName(org_name);
        String new_leader_id = mp.get("user_id");


        Map<String,String> mp1 = new TreeMap<>();
        mp1.put("user_id",old_leader_id);
        mp1.put("org_name",org_name);
        mp1.put("posi_name","NULL");
        ResponseData tt = this.GivePosiToUser(mp1);

        mp1.put("user_id",new_leader_id);
        mp1.put("org_name",org_name);
        mp1.put("posi_name","社长");
        tt = this.GivePosiToUser(mp1);


        //把社团的社长属性修改成新社长
        int x=orgMapper.UpdateOrgLeaderByName (new_leader_id,org_name);

        //把新社长的isleader属性置为这个社团的名称
        int y=orgMapper.UpdateUserIsLeaderById (org_name,new_leader_id);

        //把旧社长的isleader属性置为null
        int z=orgMapper.UpdateUserIsLeaderById(null,old_leader_id);

        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("query_orgbyname")
    ResponseData QueryOrgByName (@RequestBody  Map<String,String> mp) {
        //提交一个社团的名称
        //返回该社团的名称、简介、创建日期、活动教室
        String org_name = mp.get("org_name");
        System.out.println(org_name);
        Map<String,Object> ans=orgMapper.queryOrgByName(org_name);
        return ans!=null? new ResponseData(ExceptionMsg.SUCCESS,ans):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }


    @CrossOrigin
    @PostMapping("queryuserfromorg")
    ResponseData QueryUserFromOrg (@RequestBody Map<String,String> mp) {
        //查询一个社团里的学生列表
        //提交社团名称
        //返回每个学生的所有信息和职位
        String org_name = mp.get("org_name");
        List<Map<String,Object> > tt = orgMapper.SelectAllUserByOrgName(org_name);
        List<Map<String,Object> > ans = new ArrayList<Map<String,Object> >();
        for (int i=0;i<tt.size();i++) {
            System.out.println(tt.get(i));
            Map<String,Object> item = userMapper.queryById((String)tt.get(i).get("user_org_user_id"));
            item.put("posi",tt.get(i).get("user_org_posi"));
            ans.add(item);
        }
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("addusertoorg")
    ResponseData AddUserToOrg (@RequestBody Map<String,String> mp) {
        //提交学号和社团名称，加入社团
        //在学生-社团表里插入一项
        //社团人数+1

        String user_id = mp.get("user_id");
        String org_name = mp.get("org_name");
        String org_posi = mp.get("org_posi");
        if (org_posi == null) {
            org_posi = new String();
            org_posi = "NULL";
        }

        List<Map<String,Object> > ttt = orgMapper.CheckUserInOrg(user_id,org_name);
        if (ttt.size() > 0) {
            System.out.println("ddddd");
            return new ResponseData(ExceptionMsg.FAILED,"该学生已经在社团中");
        }

        int x=orgMapper.InsertUserOrg (user_id,org_name,org_posi);
        int y=orgMapper.upOrgNum(org_name);
        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
        //return new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS");
    }

    @CrossOrigin
    @PostMapping("deleteuserfromorg")
    ResponseData DeleteUserFromOrg (@RequestBody Map<String,String> mp) {
        String user_id = mp.get("user_id");
        String org_name = mp.get("org_name");
        int x=orgMapper.DeleteUserFromOrg(user_id,org_name);

        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("addmeeting")
    ResponseData AddMeeToOrg (@RequestBody Map<String,String> mp) {
        String mee_org_name = mp.get("mee_org_name");
        String mee_day = mp.get("mee_day");
        String mee_st = mp.get("mee_st");
        String mee_ed = mp.get("mee_ed");
        String mee_address = mp.get("mee_address");

        int day=0,st=0,ed=0;
        for (int i=0;i<mee_st.length();i++) {
            if (mee_st.charAt(i)==' ') continue;
            if (mee_st.charAt(i)==':') {
                st*=60;
                continue;
            }
            st=st*10+mee_st.charAt(i)-'0';
        }
        for (int i=0;i<mee_ed.length();i++) {
            if (mee_ed.charAt(i)==' ') continue;
            if (mee_ed.charAt(i)==':') {
                ed*=60;
                continue;
            }
            ed=ed*10+mee_ed.charAt(i)-'0';
        }
        if (ed<=st) return new ResponseData(ExceptionMsg.FAILED,"社团时间不合法！");

        List<Map<String,Object> > ans = orgMapper.SelectMee();//取出当前所有社团的时间
        if (ans!=null) {
            for (int i=0;i<ans.size();i++) {
                Map<String,Object> tt=ans.get(i);
                String t_mee_day=(String)tt.get("mee_day");//取出周几
                String tt_mee_st=(String)tt.get("mee_st");//取出开始时间
                String tt_mee_ed=(String)tt.get("mee_ed");//取出结束时间

                int t_mee_st = 0;
                int t_mee_ed = 0;
                for (int j=0;j<tt_mee_st.length();j++) {
                    if (tt_mee_st.charAt(j)==' ') continue;
                    if (tt_mee_st.charAt(j)==':') {
                        t_mee_st*=60;
                        continue;
                    }
                    t_mee_st = t_mee_st*10 + tt_mee_st.charAt(j)-'0';
                }

                for (int j=0;j<tt_mee_ed.length();j++) {
                    if (tt_mee_ed.charAt(j)==' ') continue;
                    if (tt_mee_ed.charAt(j)==':') {
                        t_mee_ed*=60;
                        continue;
                    }
                    t_mee_ed = t_mee_st*10 + tt_mee_st.charAt(j)-'0';
                }
                String t_address=(String)tt.get("mee_address");//取出举办地点

                if (t_mee_day == null) continue;

                int t_st=t_mee_st;
                int t_ed=t_mee_ed;

                if (!t_mee_day.equals(day)) continue;
                if (!t_address.equals(mee_address)) continue;
                int L,R;
                if (st>60) {
                    L=st-60;
                }
                else {
                    L=st;
                }
                if (ed<2400-60) {
                    R=ed+60;
                }
                else {
                    R=ed;
                }
                System.out.println(t_st+" "+t_ed+" "+L+" "+R);
                if (!(t_ed<L||t_st>R)) return new ResponseData(ExceptionMsg.FAILED,"和其他组会时间冲突");
            }
        }
        int z=orgMapper.InsertMeeToOrg(mee_org_name,mee_day,mee_st,mee_ed,mee_address);
        return z==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("allmeeting")
    ResponseData SelectAllMeetingFromOrg (@RequestBody Map<String,String> mp) {
        String mee_org_name = mp.get("mee_org_name");
        List<Map<String,Object> > ans = orgMapper.SelectMeeFromOrg(mee_org_name);
        //获取一个社团的所有组会信息
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("modifymeeting")
    ResponseData ModifyMeetingById (@RequestBody Map<String,String> mp) {
        String mee_id = mp.get("mee_id");
        String mee_day = mp.get("mee_day");
        String mee_st = mp.get("mee_st");
        String mee_ed = mp.get("mee_ed");
        String mee_address = mp.get("mee_address");

        int st=0,ed=0;
        for (int i=0;i<mee_st.length();i++) st=st*10+mee_st.charAt(i)-'0';
        for (int i=0;i<mee_ed.length();i++) ed=ed*10+mee_ed.charAt(i)-'0';
        if (ed<=st) return new ResponseData(ExceptionMsg.FAILED,"社团时间不合法！");

        List<Map<String,Object> > ans = orgMapper.SelectMee();//取出当前所有社团的时间
        if (ans!=null) {
            for (int i=0;i<ans.size();i++) {
                Map<String,Object> tt=ans.get(i);
                if (tt.get("mee_id").equals(mee_id)) continue;
                String t_mee_day=(String)tt.get("mee_day");//取出周几
                int t_mee_st=(int)tt.get("mee_st");//取出开始时间
                int t_mee_ed=(int)tt.get("mee_ed");//取出结束时间
                String t_address=(String)tt.get("mee_address");//取出举办地点

                if (t_mee_day == null) continue;

                int t_st=t_mee_st;
                int t_ed=t_mee_ed;

                if (!t_mee_day.equals(mee_day)) continue;
                if (!t_address.equals(mee_address)) continue;
                int L,R;
                if (st>1) {
                    L=st-1;
                }
                else {
                    L=st;
                }
                if (ed<23) {
                    R=ed+1;
                }
                else {
                    R=ed;
                }
                System.out.println(t_st+" "+t_ed+" "+L+" "+R);
                if (!(t_ed<L||t_st>R)) return new ResponseData(ExceptionMsg.FAILED,"和其他组会时间冲突");
            }
        }
        int z=orgMapper.UpateMeeFromOrg(mee_day,mee_st,mee_ed,mee_address,mee_id);
        return z==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");

    }

    @CrossOrigin
    @PostMapping("deletemeeting")
    ResponseData DeleteMeetingById (@RequestBody Map<String,String> mp) {
        //提交组会编号
        //删除组会
        String mee_id = mp.get("mee_id");
        int x=orgMapper.DeleteMeeFromOrg(mee_id);
        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");

    }

    @CrossOrigin
    @PostMapping("addposi")
    ResponseData AddPosiByUser (@RequestBody Map<String,String> mp) {
        //添加一个职位
        //提交社团名称，职位名称
        String posi_org = mp.get("posi_org");
        String posi_name = mp.get("posi_name");
        int x=orgMapper.AddPosi(posi_org,posi_name);
        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("allposi")
    ResponseData AllPosi (@RequestBody Map<String,String> mp) {
        //查询指定社团的所有职位列表
        String posi_org = mp.get("posi_org");
        List<Map<String,Object> > ans = orgMapper.SelectPosiByOrg(posi_org);
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    @CrossOrigin
    @PostMapping("modifyposi")
    ResponseData ModifyPosi (@RequestBody Map<String,String> mp) {
        //修改一个职位
        String posi_id = mp.get("posi_id");
        String posi_name = mp.get("posi_name");

        int x=orgMapper.UpdatePosi(posi_id,posi_name);
        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");

    }

    @CrossOrigin
    @PostMapping("deleteposi")
    ResponseData DeletePosi (@RequestBody Map<String,String> mp) {
        //删除一个职位
        String posi_id = mp.get("posi_id");
        int x=orgMapper.DeletePosi(posi_id);
        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    @CrossOrigin
    @PostMapping("giveposi")
    ResponseData GivePosiToUser (@RequestBody Map<String,String> mp) {
        //提交社团编号、学生编号、职位名称给一个学生安排职位
        String posi_name = mp.get("posi_name");
        String user_id = mp.get("user_id");
        String org_name = mp.get("org_name");
        int x=orgMapper.UpdateUserOrgPosi(posi_name,user_id,org_name);
        return x==1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }
}
