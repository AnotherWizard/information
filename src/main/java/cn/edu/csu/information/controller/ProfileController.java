package cn.edu.csu.information.controller;


import cn.edu.csu.information.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Slf4j
@Controller
@RequestMapping("/user")
public class ProfileController {
    @Resource
    private UserService userService;

    @RequestMapping("/info")
    public String index(Model model) {

        model.addAttribute("user", userService.findUserByMobile("13387678328"));
        return "news/user";
    }




}
