package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.AdminConstants;
import cn.edu.csu.information.dataObject.InfoUser;
import cn.edu.csu.information.enums.ResultEnum;
import cn.edu.csu.information.sal.ImageStorage;
import cn.edu.csu.information.service.UserService;
import cn.edu.csu.information.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user")
public class ProfileController {
    @Resource
    private UserService userService;

    @Resource
    private ImageStorage imageStorage;

    @RequestMapping("/info")
    public String index(HttpServletRequest request, Model model) {

        InfoUser user = SessionUtil.getUser(request, userService);

        if (user==null){
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "news/user";
    }

    @GetMapping("/pic_info")
    public String picInfo(HttpServletRequest request, Model model) {
        InfoUser user = SessionUtil.getUser(request, userService);
        model.addAttribute("user", user);
        return "news/user_pic_info";
    }

    @PostMapping("/pic_info")
    @ResponseBody
    public Map picInfo(@RequestParam(value = "avatar", required = false) MultipartFile image,
                       HttpServletRequest request) throws IOException {

        InfoUser user = SessionUtil.getUser(request, userService);
        Map<String, Object> result = new HashMap<>();
        if (image == null) {
            result.put("errmsg", ResultEnum.PARAMERR.getMsg());
            return result;
        }

        InputStream inputStream = image.getInputStream();
        String imageUrl = String.format("%s%s", AdminConstants.QINIU_DOMIN_PREFIX, imageStorage.storage(inputStream));

        user.setAvatarUrl(imageUrl);
        userService.updatOrAddUser(user);

        result.put("avatar_url", imageUrl);
        result.put("errno", ResultEnum.OK.getCode());
        result.put("errmsg", ResultEnum.OK.getMsg());
        return result;

    }

}
