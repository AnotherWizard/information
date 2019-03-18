package cn.edu.csu.information.controller;

import cn.edu.csu.information.dataObject.InfoUser;
import cn.edu.csu.information.service.UserService;
import cn.edu.csu.information.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/user")
public class ProfileController {
    @Resource
    private UserService userService;

    @RequestMapping("/info")
    public String User(HttpServletRequest request, Model model) {

        InfoUser user = SessionUtil.getUser(request, userService);
        model.addAttribute("user", user);
        return "news/user";
    }
    @RequestMapping("/base_info")
    public String UserBaseInfo(HttpServletRequest request,Model model){
        InfoUser user = SessionUtil.getUser(request, userService);
        model.addAttribute("user", user);
        return "/news/user_base_info";
    }



}
