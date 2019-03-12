package cn.edu.csu.information.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/")
public class BaseController {

    @RequestMapping("/base")
    public String index(){
        return "news/base";
    }
}
