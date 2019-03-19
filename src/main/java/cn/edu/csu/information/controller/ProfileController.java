package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.AdminConstants;
import cn.edu.csu.information.constants.CommonConstants;
import cn.edu.csu.information.dataObject.InfoCategory;
import cn.edu.csu.information.dataObject.InfoNews;
import cn.edu.csu.information.dataObject.InfoUser;
import cn.edu.csu.information.dto.UserShowDto;
import cn.edu.csu.information.enums.ResultEnum;
import cn.edu.csu.information.form.NewsForm;
import cn.edu.csu.information.sal.ImageStorage;
import cn.edu.csu.information.service.CategoryService;
import cn.edu.csu.information.service.NewsService;
import cn.edu.csu.information.service.UserService;
import cn.edu.csu.information.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user")
public class ProfileController {
    @Resource
    private UserService userService;

    @Resource
    private ImageStorage imageStorage;

    @Resource
    private CategoryService categoryService;

    @Resource
    private NewsService newsService;

    @RequestMapping("/info")
    public String User(HttpServletRequest request, Model model) {

        InfoUser user = SessionUtil.getUser(request, userService);

        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "news/user";
    }

    @GetMapping("/pass_info")
    public String passInfo(HttpServletRequest request) {

        return "news/user_pass_info";
    }

    @PostMapping("/pass_info")
    @ResponseBody
    public Map passInfo(@RequestBody Map map,
                        HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String old_password = (String) map.get("old_password");
        String new_password = (String) map.get("new_password");
        InfoUser infoUser = SessionUtil.getUser(request, userService);

        /**
         * 判断旧密码是否为空
         */
        if (StringUtils.isEmpty(old_password)) {
//            model.addAttribute("errmsg", ResultEnum.PARAMERR.getMsg());
            result.put("errno", ResultEnum.PARAMERR.getCode());
            result.put("errmsg", ResultEnum.PARAMERR.getMsg());
            return result;
        }

        /**
         * 判断旧密码是否正确
         */
        if (!DigestUtils.md5DigestAsHex(old_password.getBytes()).equals(infoUser.getPasswordHash())) {
//            model.addAttribute("errmsg", ResultEnum.PWDERR.getMsg());
            result.put("errno", ResultEnum.PWDERR.getCode());
            result.put("errmsg", ResultEnum.PWDERR.getMsg());
            return result;
        } else {
            infoUser.setPasswordHash(DigestUtils.md5DigestAsHex(new_password.getBytes()));
            userService.updatOrAddUser(infoUser);
        }
        result.put("errno", ResultEnum.OK.getCode());
        result.put("errmsg", ResultEnum.OK.getMsg());
        return result;
    }

    @GetMapping("/base_info")
    public String UserBaseInfo(HttpServletRequest request, Model model) {
        InfoUser user = SessionUtil.getUser(request, userService);
        model.addAttribute("user", user);
        return "news/user_base_info";
    }

    @PostMapping("/base_info")
    @ResponseBody
    public Map UserBaseInfo(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        String nickName = (String) map.get("nick_name");
        String signature = (String) map.get("signature");
        String gender = (String) map.get("gender");

        InfoUser infoUser = SessionUtil.getUser(request, userService);
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

    @GetMapping("/news_release")
    public String newsRelease(Model model) {

        List<InfoCategory> categoryList = categoryService.getCategoryExcludeNew();
        model.addAttribute(categoryList);
        return "news/user_news_release";
    }

    @PostMapping("/news_release")
    @ResponseBody
    public Map newsRelease(@Valid NewsForm form,
                           BindingResult bindingResult) throws IOException {
        Map<String, Object> result = new HashMap<>();
        if (bindingResult.hasErrors()) {
            result.put("errmsg", ResultEnum.PARAMERR.getMsg());
            return result;
        }

        InputStream inputStream = form.getIndexImage().getInputStream();
        String indexImageUrl = String.format("%s%s", AdminConstants.QINIU_DOMIN_PREFIX, imageStorage.storage(inputStream));
        InfoNews news = new InfoNews();
        BeanUtils.copyProperties(form, news);
        news.setIndexImageUrl(indexImageUrl);
        news.setCreateTime(new Date());
        news.setSource(CommonConstants.DEFAULT_SOURCE);
        news.setStatus(CommonConstants.NEWS_NOT_REVIEW);

        newsService.updateOrAddNews(news);
        result.put("errno", ResultEnum.OK.getCode());
        result.put("errmsg", ResultEnum.OK.getMsg());
        return result;
    }

    @RequestMapping("/collection")
    public String collection(@RequestParam(value = "p", defaultValue = "1") Integer page,
                             HttpServletRequest request, Model model) {
        InfoUser user = SessionUtil.getUser(request, userService);
//        Pageable pageable = PageRequest.of(page - 1, CommonConstants.DEFAULT_PAGE_SIZE);
        List<InfoNews> news = userService.findUserCollection(user.getId());
        model.addAttribute("collection_list", news);
//        model.addAttribute("total_page", newsPage.getTotalPages());
//        model.addAttribute("current_page", page);
        return "news/user_collection";
    }

    @RequestMapping("/news_list")
    public String newsList(@RequestParam(value = "p", defaultValue = "1") Integer page,
                           HttpServletRequest request, Model model) {

        InfoUser user = SessionUtil.getUser(request, userService);
        Pageable pageable = PageRequest.of(page - 1, CommonConstants.DEFAULT_PAGE_SIZE);

        Page<InfoNews> newsPage = newsService.findNewsByUserId(user.getId(), pageable);
        model.addAttribute("news_list", newsPage.getContent());
        model.addAttribute("total_page", newsPage.getTotalPages());
        model.addAttribute("current_page", page);

        return "news/user_news_list";
    }

    @RequestMapping("/user_follow")
    public String userFollow(@RequestParam(value = "p", defaultValue = "1") Integer page,
                             HttpServletRequest request, Model model) {

        InfoUser user = SessionUtil.getUser(request, userService);

        List<UserShowDto> userShowDtoList = userService.findUserFollowed(user.getId());
        model.addAttribute("userList", userShowDtoList);
        model.addAttribute("total_page", 1);
        model.addAttribute("current_page", page);

        return "news/user_follow";
    }

    @RequestMapping("/other_news_list")
    @ResponseBody
    public Map otherNewsList(@RequestParam(value = "user_id") Integer userId,
                             @RequestParam(value = "p",defaultValue = "1") Integer page) {
        Map<String, Object> result = new HashMap<>();

        Pageable pageable = PageRequest.of(page-1, CommonConstants.DEFAULT_PAGE_SIZE);
        Page<InfoNews> newsPage = newsService.findNewsByUserId(userId, pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("news_list", newsPage.getContent());
        data.put("total_page", newsPage.getTotalPages());
        data.put("current_page", page);

        result.put("errno", ResultEnum.OK.getCode());
        result.put("errmsg", ResultEnum.OK.getMsg());
        result.put("data", data);
        return result;
    }
}
