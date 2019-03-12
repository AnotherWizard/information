package cn.edu.csu.information.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    @RequestMapping("/")
    public String login(){
        return "admin/login";
    }
}
