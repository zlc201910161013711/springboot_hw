package com.example.demo.controller;


import com.example.demo.mapper.ActvMapper;
import com.example.demo.mapper.AnnMapper;
import com.example.demo.mapper.OrgMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Actv;
import com.example.demo.result.ExceptionMsg;
import com.example.demo.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ann")
public class AnnController {

    @Autowired
    AnnMapper annMapper;

    @Autowired
    ActvMapper actvMapper;

    @Autowired
    OrgMapper orgMapper;

    @Autowired
    UserMapper userMapper;

    //提交公告信息新建公告
    @CrossOrigin
    @PostMapping("addann")
    ResponseData AddAnn (@RequestBody Map<String,String> mp) {
        String ann_title = mp.get("ann_title");
        String ann_user_id = mp.get("ann_user_id");
        String ann_desp = mp.get("ann_desp");
        String ann_st = mp.get("ann_st");

        String ann_date=null;
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        ann_date=sdf.format(date);

        int x = annMapper.AddAnn(ann_title,ann_user_id,ann_desp,ann_st,ann_date);
        return x == 1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    //提交公告编号查看公告
    @CrossOrigin
    @PostMapping("queryannbyid")
    ResponseData QueryAnnById (@RequestBody Map<String,String> mp) {
        String ann_id = mp.get("ann_id");
        Map<String,Object> ans = annMapper.SelectAnnById(ann_id);
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    //提交公告编号，公告信息修改公告
    @CrossOrigin
    @PostMapping("modifyann")
    ResponseData ModifyAnn (@RequestBody Map<String,String> mp) {
        String ann_title = mp.get("ann_title");
        String ann_user_id = mp.get("ann_user_id");
        String ann_desp = mp.get("ann_desp");
        String ann_st = mp.get("ann_st");
        String ann_id = mp.get("ann_id");

        int x = annMapper.UpdateAnnById(ann_title,ann_desp,ann_st,ann_id);
        return x == 1?new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS"):new ResponseData(ExceptionMsg.FAILED,"FAILED");
    }

    //提交发布人ID，返回他发布的所有公告
    @CrossOrigin
    @PostMapping("queryannbyuser")
    ResponseData QueryAnnByUser (@RequestBody Map<String,String> mp) {
        String user_id = mp.get("user_id");

        List<Map<String,Object> > ans = annMapper.SelectAnnByUser(user_id);
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    //查询所有公告
    @CrossOrigin
    @PostMapping("allann")
    ResponseData QueryAllAnn () {
        List<Map<String,Object> > ans = annMapper.SelectAnn();
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }

    //根据状态查询公告
    @CrossOrigin
    @PostMapping("queryannbyst")
    ResponseData QueryAnnBySt (@RequestBody Map<String,String> mp) {
        String ann_st = mp.get("ann_st");
        List<Map<String,Object> > ans = annMapper.SelectAnnBySt(ann_st);
        return new ResponseData(ExceptionMsg.SUCCESS,ans);
    }
}
