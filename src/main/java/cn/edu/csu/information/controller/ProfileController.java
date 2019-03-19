package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.AdminConstants;
import cn.edu.csu.information.dataObject.InfoUser;
import cn.edu.csu.information.enums.ResultEnum;
import cn.edu.csu.information.enums.GenderEnum;
import cn.edu.csu.information.sal.ImageStorage;
import cn.edu.csu.information.service.UserService;
import cn.edu.csu.information.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Controller
@RequestMapping("/user")
public class ProfileController {
    @Resource
    private UserService userService;

    @Resource
    private ImageStorage imageStorage;

    @RequestMapping("/info")
    public String User(HttpServletRequest request, Model model) {

        InfoUser user = SessionUtil.getUser(request, userService);

        if (user==null){
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "news/user";
    }

//    @GetMapping("/pass_info")
//    public String UserPassInfo(HttpServletRequest request,Model model){
//        InfoUser user = SessionUtil.getUser(request, userService);
//        model.addAttribute("user", user);
//        return "news/user_pass_info";
//    }


    @GetMapping("/pass_info")
    public String passInfo(HttpServletRequest request) {


//        if (userId != null && isAdmin != null) {
//            return "redirect:/admin/index";
//        }
        return "news/user_pass_info";
    }


    @PostMapping("/pass_info")
    @ResponseBody
    public Map passInfo(@RequestParam(value = "old_password") String password,@RequestParam(value = "password") String new_password,
                        HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> result = new HashMap<>();

        InfoUser infoUser= SessionUtil.getUser(request,userService);
//        InfoUser user = userService.findUserByMobile(username);

        if (StringUtils.isEmpty(password)) {
//            model.addAttribute("errmsg", ResultEnum.PARAMERR.getMsg());
            result.put("errno",ResultEnum.PARAMERR.getCode());
            result.put("errmsg", ResultEnum.PARAMERR.getMsg());
            return result;
        }
//        if (infoUser == null || !infoUser.getIsAdmin()) {
//            model.addAttribute("errmsg", ResultEnum.NO_USER.getMsg());
//            return "admin/login";
//        }

        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(infoUser.getPasswordHash())) {
//            model.addAttribute("errmsg", ResultEnum.PWDERR.getMsg());
            result.put("errno",ResultEnum.PWDERR.getCode());
            result.put("errmsg", ResultEnum.PWDERR.getMsg());
            return result;
        }
        else{
            infoUser.setPasswordHash(new_password);
            userService.updatOrAddUser(infoUser);

        }
        result.put("errno", ResultEnum.OK.getCode());
        result.put("errmsg", ResultEnum.OK.getMsg());
        return result;


    }



    @GetMapping("/base_info")
    public String UserBaseInfo(HttpServletRequest request,Model model){
        InfoUser user = SessionUtil.getUser(request, userService);
        model.addAttribute("user", user);
        return "news/user_base_info";
    }

    @PostMapping("/base_info")
    @ResponseBody
    public Map UserBaseInfo(@RequestBody Map<String, Object> map,HttpServletRequest request ){
        Map<String, Object> result = new HashMap<>();

        String nickName = (String)map.get("nick_name");
        String signature = (String)map.get("signature");
        String gender =(String)map.get("gender");


        InfoUser infoUser= SessionUtil.getUser(request,userService);
//            # 2. 校验参数
        if (!map.containsKey("signature")) {
            result.put("errmsg", ResultEnum.PARAMERR.getMsg());
            return result;
        }
        if (StringUtils.isEmpty(nickName)) {
            result.put("errmsg", ResultEnum.PARAMERR.getMsg());
            return result;
        }

//        result.put(nickName, ResultEnum.OK.getMsg());
//        result.put(signature, ResultEnum.OK.getMsg());
//        result.put(gender, ResultEnum.OK.getMsg());

        infoUser.setSignature(signature);
        infoUser.setNickName(nickName);
        infoUser.setGender(gender);
        userService.updatOrAddUser(infoUser);

        result.put("errno", ResultEnum.OK.getCode());
        result.put("errmsg", ResultEnum.OK.getMsg());
        return result;

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
