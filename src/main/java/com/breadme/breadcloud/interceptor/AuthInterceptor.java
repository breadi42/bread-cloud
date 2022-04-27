package com.breadme.breadcloud.interceptor;

import com.breadme.breadcloud.exception.BreadCloudException;
import com.breadme.breadcloud.util.Code;
import com.breadme.breadcloud.util.Constant;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 鉴权拦截器
 *
 * @author breadme@foxmail.com
 * @date 2022/4/27 19:42
 */
@Log4j2
public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("X-Token");
        if (Objects.isNull(token) || Boolean.FALSE.equals(redisTemplate.hasKey(Constant.USER_TOKEN_KEY + token))) {
            log.info("AuthInterceptor: token为空或key不存在");
            throw new BreadCloudException(Code.AUTH_FAIL, "你还没有登录哦");
        }
        return true;
    }
}