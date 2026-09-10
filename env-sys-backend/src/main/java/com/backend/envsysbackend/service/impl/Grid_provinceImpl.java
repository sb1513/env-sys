package com.backend.envsysbackend.service.impl;

import com.backend.envsysbackend.entity.Grid_province;
import com.backend.envsysbackend.mapper.Grid_provinceMapper;
import com.backend.envsysbackend.service.Grid_provinceService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class Grid_provinceImpl
        extends ServiceImpl<Grid_provinceMapper, Grid_province>
        implements Grid_provinceService {
}
