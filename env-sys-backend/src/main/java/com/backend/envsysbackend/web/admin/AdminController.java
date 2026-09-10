package com.backend.envsysbackend.web.admin;

import com.backend.envsysbackend.entity.Admins;
import com.backend.envsysbackend.service.AdminService;
import com.backend.envsysbackend.util.JWTutil;
import com.backend.envsysbackend.web.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
    @Autowired
    private JWTutil jwtutil;

    @Autowired
    private AdminService adminService;

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
}
