package com.backend.envsysbackend.mapper;

import com.backend.envsysbackend.entity.Aqi_statistics;
import com.backend.envsysbackend.entity.Monthly_aqistatistics;
import com.backend.envsysbackend.entity.Statistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.backend.envsysbackend.entity.Province_statistics;

import java.util.List;

@Mapper
public interface StatisticsMapper extends BaseMapper<Statistics> {
    List<Province_statistics> selectProvinceStatistics();
    List<Aqi_statistics> selectAqiStatistics();
    List<Monthly_aqistatistics> getMonthlyAqiStatistics();
}
