package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.AdminConstants;
import cn.edu.csu.information.dataObject.InfoCategory;
import cn.edu.csu.information.dataObject.InfoNews;
import cn.edu.csu.information.dataObject.InfoUser;
import cn.edu.csu.information.dto.NewsDetailDto;
import cn.edu.csu.information.enums.ResultEnum;
import cn.edu.csu.information.service.CategoryService;
import cn.edu.csu.information.service.NewsService;
import cn.edu.csu.information.service.UserService;
import cn.edu.csu.information.utils.DateUtil;
import cn.edu.csu.information.vo.UserCountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author liuchengsheng
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private UserService userService;

    @Resource
    private NewsService newsService;

    @Resource
    private CategoryService categoryService;

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(Model model) {

        log.info("post...login");

        model.addAttribute("errmsg", "参数错误");
        return "admin/login";
    }

    @RequestMapping("/index")
    public String index(Model model) {

        model.addAttribute("user", userService.findUserByMobile("15289353925"));
        return "admin/index";
    }

    @RequestMapping("/user_count")
    public String userCount(Model model) {
        UserCountVo countVo = new UserCountVo();

        List<InfoUser> infoUsers = userService.findUserByType(AdminConstants.NOT_ADMIN);
        //用户总数
        countVo.setTotalCount(infoUsers.size());

        //月新增数
        countVo.setMonCount(userService.findUserRegisterNearTime(DateUtil.getTimeBeforeAMonth(new Date())).size());

        //日新增数
        countVo.setDayCount(userService.findUserRegisterNearTime(DateUtil.getStartTimeOfDay(new Date())).size());

        //折线图数据
        Date now = new Date();

        List<Integer> activeCount = new ArrayList<>();
        List<String> activeTime = new ArrayList<>();

        for (int i = 30; i >= 0; i--) {

            Date date = DateUtil.getTimeBeforeDays(now, i);

            Date startTime = DateUtil.getStartTimeOfDay(date);
            Date endTime = DateUtil.getEndTimeOfDay(date);

            activeCount.add(userService.findUserLoginBetweenTime(startTime, endTime).size());
            activeTime.add(DateUtil.formatDate(date));

        }

        countVo.setActiveCount(activeCount);
        countVo.setActiveTime(activeTime);
        model.addAttribute("data", countVo);
        return "admin/user_count";
    }

    @RequestMapping("/user_list")
    public String userList(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                           @RequestParam(value = "size", defaultValue = "1") Integer size,
                           Model model) {
        List<InfoUser> users = userService.findUserByType(AdminConstants.NOT_ADMIN);

        model.addAttribute(currentPage);
        model.addAttribute("totalPage", 1);
        model.addAttribute("userList", users);
        return "admin/user_list";
    }

    @RequestMapping("/news_review")
    public String newsReview(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                             @RequestParam(value = "size", defaultValue = "1") Integer size,
                             @RequestParam(value = "keywords", required = false) String keywords,
                             Model model) {

        List<InfoNews> newsList = newsService.findNewsNotPub();
        model.addAttribute(currentPage);
        model.addAttribute("totalPage", 1);
        model.addAttribute(newsList);
        return "admin/news_review";
    }

    @RequestMapping("/news_review_detail/{newsId}")
    public String newsReviewDetail(@PathVariable Integer newsId,
                                   Model model) {

        model.addAttribute("data", newsService.findNewsById(newsId));
        return "admin/news_review_detail";
    }

    @ResponseBody
    @PostMapping("/news_review_action")
    public Map newsReviewAction(@RequestBody Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if (!map.containsKey("newsId") | !map.containsKey("action")) {

            result.put("errmsg", ResultEnum.OK.getMsg());
            return result;
        }

        Integer newsId = Integer.valueOf((String) map.get("newsId"));
        String action = (String) map.get("action");

        InfoNews news = newsService.findNewsById(newsId).getInfoNews();

        if (AdminConstants.REVIEW_ACCEPT.equals(action)) {
            news.setStatus(AdminConstants.NEWS_REVIEW_PASS);

        } else if (!map.containsKey("reason")) {
            result.put("errmsg", ResultEnum.REASONERR.getMsg());
            return result;

        } else {
            news.setStatus(AdminConstants.NEWS_REVIEW_NOT_PASS);
            news.setReason((String) map.get("reason"));
        }

        news.setUpdateTime(new Date());
        newsService.updateNews(news);
        result.put("errno", ResultEnum.OK.getCode());
        result.put("errmsg", ResultEnum.OK.getMsg());

        return result;
    }

    @RequestMapping("/news_edit")
    public String newsEdit(@RequestParam(value = "p", defaultValue = "1") Integer currentPage,
                           @RequestParam(value = "keywords", required = false) String keywords,
                           Model model) {

        PageRequest pageRequest = PageRequest.of(currentPage - 1, AdminConstants.DEFAULT_PAGE_SIZE);

        Page<InfoNews> infoNewsPage = newsService.findAll(pageRequest);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", infoNewsPage.getTotalPages());
        model.addAttribute(infoNewsPage.getContent());
        return "admin/news_edit";
    }

    @GetMapping("/news_edit_detail")
    public String newsEditDetail(@RequestParam(value = "newsId") Integer newsId,
                                 Model model) {

        NewsDetailDto detailDto = newsService.findNewsById(newsId);
        model.addAttribute(detailDto.getInfoNews());

        model.addAttribute("categorys", categoryService.getCategoryExcludeNew());
        return "admin/news_edit_detail";
    }

    @PostMapping("/news_edit_detail")
    @ResponseBody
    public Map newsEditDetail(InfoNews infoNews,
                              @RequestParam(value = "index_image") MultipartFile image) {

        log.info(image.isEmpty()+"");
        log.info(infoNews.toString());

        Map<String, Object> result = new HashMap<>();
        return result;
    }

    @GetMapping("/news_type")
    public String newType(Model model) {

        model.addAttribute("categorys", categoryService.getCategoryExcludeNew());
        return "admin/news_type";
    }

    @PostMapping("/news_type")
    @ResponseBody
    public Map newType(@RequestBody Map map) {

        Map<String, Object> result = new HashMap<>();

        if (!map.containsKey("name")) {

            result.put("errmsg", ResultEnum.OK.getMsg());
            return result;
        }

        String categoryName = (String) map.get("name");

        InfoCategory category;
        if (map.containsKey("id")) {

            Integer id = Integer.valueOf((String) map.get("id"));
            category = categoryService.findCategoryById(id);
            if (!categoryName.equals(category.getName())) {
                category.setName(categoryName);
                category.setUpdateTime(new Date());
            }

        } else {

            category = new InfoCategory(categoryName);

        }

        categoryService.save(category);

        result.put("errno", ResultEnum.OK.getCode());
        result.put("errmsg", ResultEnum.OK.getMsg());
        return result;
    }
}
