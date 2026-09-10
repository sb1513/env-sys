package com.backend.envsysbackend.service.impl;

import com.backend.envsysbackend.entity.Aqi;
import com.backend.envsysbackend.mapper.AqiMapper;
import com.backend.envsysbackend.service.AqiService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AqiImpl
        extends ServiceImpl<AqiMapper, Aqi>
        implements AqiService {
}
