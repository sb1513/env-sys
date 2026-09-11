package com.backend.envsysbackend.web.interceptor;

import com.backend.envsysbackend.util.JWTutil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptorSupervisor implements HandlerInterceptor {
    @Autowired
    private JWTutil jwtutil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){return true;}
        String jwt = request.getHeader("Authorization");
        String user_id;
        String role;
        try{
            Claims claims = jwtutil.parseToken(jwt);
            user_id = claims.get("user_id",String.class);
            role = claims.get("role",String.class);
            if(!role.equalsIgnoreCase("supervisor")){
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().print("{\"code\":5001,\"msg\":\"凭证错误，请稍后重试\",\"data\":null}");
                return false;
            }
            request.setAttribute("tel_id",user_id);
            request.setAttribute("role",role);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print("{\"code\":5001,\"msg\":\"凭证错误，请稍后重试\",\"data\":null}");
            return false;
        }
    }
}