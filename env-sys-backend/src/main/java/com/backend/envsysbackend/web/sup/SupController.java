package com.backend.envsysbackend.web.sup;

import com.backend.envsysbackend.entity.Admins;
import com.backend.envsysbackend.entity.Aqi_feedback;
import com.backend.envsysbackend.entity.Supervisor;
import com.backend.envsysbackend.service.AdminService;
import com.backend.envsysbackend.service.Aqi_feedbackService;
import com.backend.envsysbackend.service.SupervisorService;
import com.backend.envsysbackend.util.JWTutil;
import com.backend.envsysbackend.web.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/supervisor")
@CrossOrigin(origins = "http://localhost:5173")
public class SupController {
    @Autowired
    private JWTutil jwtutil;

    @Autowired
    private SupervisorService supervisorService;
    @Autowired
    private Aqi_feedbackService aqi_feedbackService;

    @PostMapping("/login")
    public R login(@RequestBody Map<String,Object> map){
        QueryWrapper<Supervisor> qw = new QueryWrapper<>();
        qw.eq("tel_id",map.get("phone"));
        qw.eq("password",map.get("password"));
        Supervisor supervisor = supervisorService.getOne(qw);
        if(supervisor!=null){
            Map<String,Object> map1 = new HashMap<>();
            String jwt = jwtutil.generateToken(Map.of("user_id",supervisor.getTelId(),"nickName",supervisor.getRealName(),"role","supervisor"));
            map1.put("id",supervisor.getTelId());
            map1.put("userName",supervisor.getRealName());
            return new R(2000,"登录成功!",Map.of("user",map1,"token",jwt));
        }
        else{
            return new R(4001,"用户名或密码错误",null);
        }
    }

    @PostMapping("/register")
    public R register(@RequestBody Supervisor supervisor){
        try{
            supervisorService.save(supervisor);
            return new R (2000, "注册成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return new R (5001, "注册失败，手机号已注册",null);
        }
    }

    @PostMapping("/aqifeedback")
    public R aqifeedback(@RequestBody Aqi_feedback aqi_feedback){
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");
        aqi_feedback.setAfDate(now.format(dateFormatter));
        aqi_feedback.setAfTime(now.format(timeFormatter));
        aqi_feedbackService.save(aqi_feedback);
        return new R (2000, "上报成功", null);
    }

    @GetMapping("/aqiquery")
    public R aqiquery(@RequestAttribute("tel_id") int tel_id){
        QueryWrapper<Aqi_feedback> qw = new QueryWrapper<>();
        qw.eq("af.tel_id",tel_id);
        qw.orderByDesc("af_date");
        qw.orderByDesc("af_time");
        List<Aqi_feedback> aqi_feedbacks = aqi_feedbackService.list(qw);
        return new R (2000, "获取成功", aqi_feedbacks);
    }

    @GetMapping("/aqidetail/{af_id}")
    public R aqidetail(@PathVariable int af_id){
        QueryWrapper<Aqi_feedback> qw = new QueryWrapper<>();
        Aqi_feedback aqi_feedback = aqi_feedbackService.getById(af_id);
        return new R (2000, "获取成功", aqi_feedback);
    }
}
