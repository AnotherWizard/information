package cn.edu.csu.information.config.interecptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author liuchengsheng
 */
@Component
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("管理员拦截器:{}", request.getServletPath());
        HttpSession session = request.getSession();
        if (session.getAttribute("isAdmin") == null) {
            log.info("非法访问后台管理");
//            response.sendRedirect("/admin/login");
            return true;
        }

        return true;
    }
}
