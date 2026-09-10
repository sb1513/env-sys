package com.backend.envsysbackend.service.impl;

import com.backend.envsysbackend.entity.Grid_member;
import com.backend.envsysbackend.mapper.Grid_memberMapper;
import com.backend.envsysbackend.service.Grid_memberService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class Grid_memberImpl
        extends ServiceImpl<Grid_memberMapper, Grid_member>
        implements Grid_memberService {
}
