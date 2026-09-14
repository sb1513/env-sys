package com.backend.envsysbackend.config;

import com.backend.envsysbackend.web.interceptor.JwtInterceptorGrid;
import com.backend.envsysbackend.web.interceptor.JwtInterceptorSupervisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtInterceptorSupervisor jwtInterceptorSupervisor;
    @Autowired
    private JwtInterceptorGrid jwtInterceptorGrid;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptorSupervisor).addPathPatterns("/supervisor/aqiquery","/supervisor/aqifeedback").order(1);
        registry.addInterceptor(jwtInterceptorGrid).addPathPatterns("/grid/aqilist","/grid/aqidetail").order(2);
    }

}
