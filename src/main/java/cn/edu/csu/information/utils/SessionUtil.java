package cn.edu.csu.information.utils;

import cn.edu.csu.information.constants.CommonConstants;
import cn.edu.csu.information.dataObject.InfoUser;
import cn.edu.csu.information.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SessionUtil {

    public static void setSeesionAndToken(InfoUser infoUser, HttpServletRequest request,
                                          HttpServletResponse response,
                                          StringRedisTemplate redisTemplate) {
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(String.format("%s%s", CommonConstants.TOKEN_PREFIX, token), infoUser.getMobile(),
                CommonConstants.TOKEN_EXPIRE, TimeUnit.SECONDS);
        CookieUtil.set(response, CommonConstants.COOKIE_TOKEN, token, CommonConstants.TOKEN_EXPIRE);

        HttpSession session = request.getSession();
        session.setAttribute("userId", infoUser.getId());
        session.setAttribute("mobile", infoUser.getMobile());
        session.setAttribute("nickName", infoUser.getNickName());

    }

    public static void removeSeesionAndToken(HttpServletRequest request,
                                             HttpServletResponse response,
                                             StringRedisTemplate redisTemplate) {

        Cookie cookie = CookieUtil.get(request, CommonConstants.COOKIE_TOKEN);

        if (cookie != null) {
            redisTemplate.opsForValue().getOperations().delete(String.format("%s%s", CommonConstants.TOKEN_PREFIX, cookie.getValue()));
            CookieUtil.set(response, CommonConstants.COOKIE_TOKEN, null, 0);
        }

        HttpSession session = request.getSession();
        session.removeAttribute("userId");
        session.removeAttribute("mobile");
        session.removeAttribute("nickName");
        session.removeAttribute("isAdmin");

    }

    public static InfoUser getUser(HttpServletRequest request, UserService userService) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return null;
        }
        return userService.findUserById(userId);

    }

}
