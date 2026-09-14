package com.backend.envsysbackend.web.grid;

import com.backend.envsysbackend.entity.*;
import com.backend.envsysbackend.service.*;
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
@RequestMapping("/grid")
@CrossOrigin(origins = "http://localhost:5173")
public class GridController {
    @Autowired
    private JWTutil jwtutil;

    @Autowired
    private Grid_memberService grid_memberService;
    @Autowired
    private Grid_provinceService grid_provinceService;
    @Autowired
    private Grid_cityService grid_cityService;
    @Autowired
    private Aqi_feedbackService aqi_feedbackService;
    @Autowired
    private AqiService aqiService;
    @Autowired
    private StatisticsService statisticsService;

    @PostMapping("/login")
    public R login(@RequestBody Map<String,Object> map){
        QueryWrapper<Grid_member> qw = new QueryWrapper<>();
        qw.eq("gm_code",map.get("userName"));
        qw.eq("password",map.get("password"));
        Grid_member gm = grid_memberService.getOne(qw);
        if(gm!=null){
            Map<String,Object> map1 = new HashMap<>();
            String jwt = jwtutil.generateToken(Map.of("user_id",gm.getGmId(),"nickName",gm.getGmName(),"role","grid"));
            map1.put("id",gm.getGmId());
            map1.put("userName",gm.getGmName());
            return new R(2000,"登录成功!",Map.of("user",map1,"token",jwt));
        }
        else{
            return new R(4001,"用户名或密码错误",null);
        }
    }

    @PostMapping("/register")
    public R register(@RequestBody Grid_member grid_member){
        try{
            grid_memberService.save(grid_member);
            return new R (2000, "注册成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return new R (5001, "注册失败，该用户名已注册",null);
        }
    }

    @GetMapping("/province/list")
    public R provinceList() {

        List<Grid_province> list = grid_provinceService.list();

        return new R(2000, "获取成功", list);
    }

    @GetMapping("/city/list/{provinceId}")
    public R cityList(@PathVariable Integer provinceId) {

        QueryWrapper<Grid_city> qw = new QueryWrapper<>();

        qw.eq("province_id", provinceId);

        List<Grid_city> list = grid_cityService.list(qw);

        return new R(2000, "获取成功", list);
    }

    @GetMapping("/aqistandard")
    public R aqistandard() {
        List<Aqi> list = aqiService.list();
        return new R (2000, "获取成功", list);
    }

    @GetMapping("/aqilist")
    public R aqilist(@RequestAttribute("gm_id") int gmId) {
        QueryWrapper<Aqi_feedback> qw = new QueryWrapper<>();
        qw.eq("af.gm_id", gmId);
        qw.eq("status","1");
        List<Aqi_feedback> list = aqi_feedbackService.list(qw);
        return new R (2000, "获取成功", list);
    }

    @GetMapping("/aqidetail/{af_id}")
    public R aqidetail(@PathVariable int af_id) {
        QueryWrapper<Aqi_feedback> qw = new QueryWrapper<>();
        qw.eq("af.af_id", af_id);
        List<Aqi_feedback> list = aqi_feedbackService.list(qw);
        return new R (2000, "获取成功", list.get(0));
    }

    @PostMapping("/postaqi/{af_id}")
    public R postaqi(@PathVariable int af_id, @RequestBody Statistics statistics) {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");
        statistics.setConfirmDate(now.format(dateFormatter));
        statistics.setConfirmTime(now.format(timeFormatter));
        statisticsService.save(statistics);
        Aqi_feedback aqi_feedback = aqi_feedbackService.getById(af_id);
        aqi_feedback.setState(2);
        aqi_feedbackService.updateById(aqi_feedback);
        return new R (2000, "检测成功", null);
    }
}