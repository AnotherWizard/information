package cn.edu.csu.information.config.interecptor;

import cn.edu.csu.information.constants.CommonConstants;
import cn.edu.csu.information.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("拦截到请求:{}",request.getServletPath());
        Cookie cookie = CookieUtil.get(request, CommonConstants.COOKIE_TOKEN);
        if (cookie == null) {
            log.info("未登录");
//            response.sendRedirect("/");
            return true;
        }

        String token = redisTemplate.opsForValue().get(String.format("%s%s",CommonConstants.TOKEN_PREFIX,cookie.getValue()));
        if (StringUtils.isEmpty(token)){
            log.info("登录过期");
//            response.sendRedirect("/");
            return true;
        }


        return true;
    }
}
