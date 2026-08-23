package com.hongyun.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.hongyun.constants.NormalConstants;
import com.hongyun.constants.RedisConstants;
import com.hongyun.dto.vo.User;
import com.hongyun.util.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final StringRedisTemplate redisTemplate;

    public TokenAuthenticationFilter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(NormalConstants.AUTH);
        if (StringUtils.hasLength(token)) {
            Map<Object, Object> userMap = redisTemplate.opsForHash()
                    .entries(RedisConstants.LOGIN_CODE_TOKEN + token);
            if (!userMap.isEmpty()) {
                User user = BeanUtil.fillBeanWithMap(userMap, new User(), false);
                UserHolder.setUser(user);
                redisTemplate.expire(RedisConstants.LOGIN_CODE_TOKEN + token, Duration.ofHours(2));

                Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                ((UsernamePasswordAuthenticationToken) auth).setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        try {
            chain.doFilter(request, response);
        } finally {
            UserHolder.removeUser();
            SecurityContextHolder.clearContext();
        }
    }
}
