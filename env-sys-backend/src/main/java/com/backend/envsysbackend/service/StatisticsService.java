package com.backend.envsysbackend.service;

import com.backend.envsysbackend.entity.*;
import com.baomidou.mybatisplus.spring.service.IService;

import java.util.List;

public interface StatisticsService extends IService<Statistics> {
    List<Province_statistics> selectProvinceStatistics();
    List<Aqi_statistics> selectAqiStatistics();
    List<Monthly_aqistatistics> getMonthlyAqiStatistics();
    Other_statistics getOtherStatistics();
}
