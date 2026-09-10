package com.backend.envsysbackend.service.impl;

import com.backend.envsysbackend.entity.Statistics;
import com.backend.envsysbackend.mapper.StatisticsMapper;
import com.backend.envsysbackend.service.StatisticsService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class StatisticsImpl
        extends ServiceImpl<StatisticsMapper, Statistics>
        implements StatisticsService {
}
