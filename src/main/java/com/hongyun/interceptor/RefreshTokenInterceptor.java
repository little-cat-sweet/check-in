package com.hongyun.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.hongyun.constants.NormalConstants;
import com.hongyun.constants.RedisConstants;
import com.hongyun.dto.vo.User;
import com.hongyun.util.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Map;

public class RefreshTokenInterceptor implements HandlerInterceptor {

    StringRedisTemplate stringRedisTemplate = null;
    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader(NormalConstants.AUTH);
        if(! StringUtils.hasLength(token)) {
            return true;
        }

        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(RedisConstants.LOGIN_CODE_TOKEN + token);
        if(userMap.isEmpty()){
            return true;
        }

        User userDto = BeanUtil.fillBeanWithMap(userMap, new User(), false);

        UserHolder.setUser(userDto);

        flushTTL(RedisConstants.LOGIN_CODE_TOKEN + token);

        return true;
    }

    private void flushTTL(String token){
        stringRedisTemplate.expire(token, Duration.ofHours(2));
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}

