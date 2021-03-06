package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.CommonConstants;
import cn.edu.csu.information.dataObject.InfoCategory;
import cn.edu.csu.information.dataObject.InfoNews;
import cn.edu.csu.information.dataObject.InfoUser;
import cn.edu.csu.information.dto.NewsBasicDto;
import cn.edu.csu.information.enums.ResultEnum;
import cn.edu.csu.information.service.CategoryService;
import cn.edu.csu.information.service.NewsService;
import cn.edu.csu.information.service.UserService;
import cn.edu.csu.information.utils.DateUtil;
import cn.edu.csu.information.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class IndexController {

    @Resource
    private CategoryService categoryService;
    @Resource
    private NewsService newsService;
    @Resource
    private UserService userService;

    @GetMapping("/news_list")
    @ResponseBody
    public Map<String, Object> newsList(@RequestParam(value = "cid", defaultValue = "1") Integer cid,
                                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "per_page", defaultValue = "10") Integer per_page) {
        Page<InfoNews> newsOrderedPage = null;
        Pageable newsOrderedPageable = PageRequest.of(page, per_page);
//        List<InfoNews> newsOrderedList = null;
        if (cid == 1) {
            newsOrderedPage = newsService.findNewsByStatusOrderByCreateTimeDesc(CommonConstants.NEWEST_STATUS_NEWS, newsOrderedPageable);
//            newsOrderedList = newsService.findByStatusOrderByCreateTimeDesc(CommonConstants.NEWEST_STATUS_NEWS);
        } else {
            newsOrderedPage = newsService.findNewsByCategoryIdAndStatusOrderByCreateTimeDesc(cid, CommonConstants.NEWEST_STATUS_NEWS, newsOrderedPageable);
//            newsOrderedList = newsService.findByCategoryIdAndStatusOrderByCreateTimeDesc(cid, CommonConstants.NEWEST_STATUS_NEWS);
        }

//        int totalPage = newsOrderedList.size();
        int totalPage = newsOrderedPage.getTotalPages();
        int currentPage = page;
        List<NewsBasicDto> newsDictLi = new LinkedList<>();
//        for(InfoNews news : newsOrderedList){
        for (InfoNews news : newsOrderedPage) {
            NewsBasicDto newsBasicDto = new NewsBasicDto();
            BeanUtils.copyProperties(news, newsBasicDto);
            newsBasicDto.setCreateTimeStr(
                    DateUtil.formatDate2(newsBasicDto.getCreateTime()));
            newsDictLi.add(newsBasicDto);
        }

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("news_dict_li", newsDictLi);
        data.put("current_page", currentPage);
        data.put("total_page", totalPage);

        Map<String, Object> jsonBag = new HashMap<String, Object>();

        jsonBag.put("data", data);
        jsonBag.put("errno", ResultEnum.OK.getCode());
        jsonBag.put("errmsg", ResultEnum.OK.getMsg());

        return jsonBag;
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {

        InfoUser user = SessionUtil.getUser(request, userService);
        if (user!=null){
            model.addAttribute("user",user);
        }

        rankList(model, categoryService, newsService);

        return "news/index";
    }

    static void rankList(Model model, CategoryService categoryService, NewsService newsService) {
        List<InfoCategory> infoCategories = categoryService.findAll();
        Sort sort = new Sort(Sort.Direction.DESC, "clicks");
        List<InfoNews> newsOrderedList = newsService.findNewsAll(sort);

        model.addAttribute("categories", infoCategories);
        model.addAttribute("news_list", newsOrderedList);
        model.addAttribute("top", CommonConstants.CLICK_RANK_MAX_NEWS);
    }

}
