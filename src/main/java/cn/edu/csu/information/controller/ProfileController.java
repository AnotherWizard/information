package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.AdminConstants;
import cn.edu.csu.information.constants.CommonConstants;
import cn.edu.csu.information.dataObject.InfoCategory;
import cn.edu.csu.information.dataObject.InfoNews;
import cn.edu.csu.information.dataObject.InfoUser;
import cn.edu.csu.information.enums.ResultEnum;
import cn.edu.csu.information.form.NewsForm;
import cn.edu.csu.information.sal.ImageStorage;
import cn.edu.csu.information.service.CategoryService;
import cn.edu.csu.information.service.NewsService;
import cn.edu.csu.information.service.UserService;
import cn.edu.csu.information.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @RequestMapping("/base_info")
    public String UserBaseInfo(HttpServletRequest request,Model model){
        InfoUser user = SessionUtil.getUser(request, userService);
        model.addAttribute("user", user);
        return "/news/user_base_info";
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
            result.put("errmsg", bindingResult.getFieldError().getDefaultMessage());
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
}
