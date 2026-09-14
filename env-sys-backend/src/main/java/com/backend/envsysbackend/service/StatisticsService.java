package com.backend.envsysbackend.service;

import com.backend.envsysbackend.entity.Statistics;
import com.baomidou.mybatisplus.spring.service.IService;
import com.backend.envsysbackend.entity.Province_statistics;

import java.util.List;

public interface StatisticsService extends IService<Statistics> {
    List<Province_statistics> selectProvinceStatistics();
}
