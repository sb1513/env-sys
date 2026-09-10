package com.backend.envsysbackend.service.impl;

import com.backend.envsysbackend.entity.Admins;
import com.backend.envsysbackend.mapper.AdminMapper;
import com.backend.envsysbackend.service.AdminService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AdminImpl
        extends ServiceImpl<AdminMapper, Admins>
        implements AdminService {
}
