package com.hongyun.interceptor;

import com.hongyun.dto.vo.User;
import com.hongyun.util.UserHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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