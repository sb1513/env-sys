package com.backend.envsysbackend.web.admin;

import com.backend.envsysbackend.entity.Admins;
import com.backend.envsysbackend.entity.Aqi_feedback;
import com.backend.envsysbackend.service.AdminService;
import com.backend.envsysbackend.service.Aqi_feedbackService;
import com.backend.envsysbackend.util.JWTutil;
import com.backend.envsysbackend.web.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
    @Autowired
    private JWTutil jwtutil;

    @Autowired
    private AdminService adminService;

    @Autowired
    private Aqi_feedbackService aqi_feedbackService;

    @PostMapping("/login")
    public R login(@RequestBody Map<String,Object> map){
        QueryWrapper<Admins> qw = new QueryWrapper<>();
        qw.eq("admin_code",map.get("user_name"));
        qw.eq("password",map.get("password"));
        Admins admins = adminService.getOne(qw);
        if(admins!=null){
            Map<String,Object> map1 = new HashMap<>();
            String jwt = jwtutil.generateToken(Map.of("user_id",admins.getAdminId(),"nickName",admins.getAdminCode(),"role","admin"));
            map1.put("id",admins.getAdminId());
            map1.put("userName",admins.getAdminCode());
            return new R(2000,"登录成功!",Map.of("user",map1,"token",jwt));
        }
        else{
            return new R(4001,"用户名或密码错误",null);
        }
    }

    @GetMapping("/supervisor/list")
    public R supervisorlist(int pageNum,int pageSize,String province,String city,Integer grade,String feedbackDate,Integer state){
        Page<Aqi_feedback> pg = new Page<>(pageNum,pageSize);
        QueryWrapper<Aqi_feedback> qw = new QueryWrapper<>();
        if(province!=null&&province!=""){
            qw.eq("gp.province_name",province);
        }
        if(city!=null&&city!=""){
            qw.eq("gc.city_name",city);
        }
        if(feedbackDate!=null){
            qw.eq("af_date",feedbackDate);
        }
        if(grade!=null){
            qw.eq("estimated_grade",grade);
        }
        if(state!=null){
            qw.eq("state",state);
        }
        aqi_feedbackService.page(pg,qw);
        return new R (2000, "获取成功", pg);
    }

    @GetMapping("/supervisor/aqidetail/{id}")
    public R aqidetail(@PathVariable int id) {
        Aqi_feedback aqiFeedback = aqi_feedbackService.getById(id);
        if (aqiFeedback == null) {
            return new R(5001, "查询失败",null);
        }
        return new R(2000, "获取成功", aqiFeedback);
    }
}
