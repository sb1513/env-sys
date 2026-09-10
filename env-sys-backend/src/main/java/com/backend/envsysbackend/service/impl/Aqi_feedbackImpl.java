package com.backend.envsysbackend.service.impl;

import com.backend.envsysbackend.entity.Aqi_feedback;
import com.backend.envsysbackend.mapper.Aqi_feedbackMapper;
import com.backend.envsysbackend.service.Aqi_feedbackService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class Aqi_feedbackImpl
        extends ServiceImpl<Aqi_feedbackMapper, Aqi_feedback>
        implements Aqi_feedbackService {
}
