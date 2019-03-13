package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.AdminConstants;
import cn.edu.csu.information.dataObject.InfoUser;
import cn.edu.csu.information.service.UserService;
import cn.edu.csu.information.utils.AdminUtil;
import cn.edu.csu.information.vo.UserCountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private UserService userService;
    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(Model model) {

        log.info("post...login");

        model.addAttribute("errmsg","参数错误");
        return "admin/login";
    }

    @RequestMapping("/index")
    public String index(Model model){

        model.addAttribute("user",userService.findUserByMobile("17347313219"));
        return "admin/index";
    }

    @RequestMapping("user_count")
    public String userCount(Model model){
        UserCountVo countVo = new UserCountVo();

        List<InfoUser> infoUsers = userService.findUserByType(AdminConstants.NOT_ADMIN);
        //用户总数
        countVo.setTotalCount(infoUsers.size());

        //月新增数
        countVo.setMonCount(AdminUtil.monCountInfoUser(infoUsers));

        //日新增数
        countVo.setDayCount(AdminUtil.dayCountInfoUser(infoUsers));

        model.addAttribute("data",countVo);
        return "admin/user_count";
    }

    @RequestMapping("user_list")
    public String userList(@RequestParam(value = "currentPage",defaultValue = "1")Integer currentPage,
            @RequestParam(value = "size",defaultValue = "1")Integer size,
            Model model){
        List<InfoUser> users = userService.findUserByType(AdminConstants.NOT_ADMIN);

        model.addAttribute(currentPage);
        model.addAttribute("totalPage",1);
        model.addAttribute("userList",users);
        return "admin/user_list";
    }

    @RequestMapping("news_review")
    public String newsReview(@RequestParam(value = "currentPage",defaultValue = "1")Integer currentPage,
                           @RequestParam(value = "size",defaultValue = "1")Integer size,
                           Model model){


        return "admin/news_review";
    }
}
