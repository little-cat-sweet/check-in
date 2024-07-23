package com.hongyun.interceptor;

import com.hongyun.dto.vo.User;
import com.hongyun.util.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User userDto = UserHolder.getUser();
        if(Objects.isNull(userDto)){
            response.setStatus(404);
            return false;
        }
        return true;
    }
}