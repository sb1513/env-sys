package com.backend.envsysbackend.service.impl;

import com.backend.envsysbackend.entity.Aqi_statistics;
import com.backend.envsysbackend.entity.Province_statistics;
import com.backend.envsysbackend.entity.Statistics;
import com.backend.envsysbackend.mapper.StatisticsMapper;
import com.backend.envsysbackend.service.StatisticsService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsImpl
        extends ServiceImpl<StatisticsMapper, Statistics>
        implements StatisticsService {
    @Override
    public List<Province_statistics> selectProvinceStatistics() {
        return baseMapper.selectProvinceStatistics();
    }

    @Override
    public List<Aqi_statistics> selectAqiStatistics() {
        return baseMapper.selectAqiStatistics();
    }
}
