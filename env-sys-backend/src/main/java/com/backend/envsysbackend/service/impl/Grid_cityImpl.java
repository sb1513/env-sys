package com.backend.envsysbackend.service.impl;

import com.backend.envsysbackend.entity.Grid_city;
import com.backend.envsysbackend.mapper.Grid_cityMapper;
import com.backend.envsysbackend.service.Grid_cityService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class Grid_cityImpl
        extends ServiceImpl<Grid_cityMapper, Grid_city>
        implements Grid_cityService {
}
