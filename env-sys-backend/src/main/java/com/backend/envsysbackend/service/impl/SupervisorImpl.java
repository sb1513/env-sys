package com.backend.envsysbackend.service.impl;

import com.backend.envsysbackend.entity.Supervisor;
import com.backend.envsysbackend.mapper.SupervisorMapper;
import com.backend.envsysbackend.service.SupervisorService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SupervisorImpl
        extends ServiceImpl<SupervisorMapper, Supervisor>
        implements SupervisorService {
}
