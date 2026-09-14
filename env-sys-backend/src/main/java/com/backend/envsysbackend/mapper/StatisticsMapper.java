package com.backend.envsysbackend.mapper;

import com.backend.envsysbackend.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticsMapper extends BaseMapper<Statistics> {
    List<Province_statistics> selectProvinceStatistics();
    List<Aqi_statistics> selectAqiStatistics();
    List<Monthly_aqistatistics> getMonthlyAqiStatistics();
    Other_statistics getOtherStatistics();
    List<ProvincePollutionStatistics> getProvincePollutionStatistics();
}
