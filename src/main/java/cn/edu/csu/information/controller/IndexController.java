package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.CommonConstants;
import cn.edu.csu.information.dao.InfoCategoryRepository;
import cn.edu.csu.information.dataObject.InfoCategory;
import cn.edu.csu.information.dataObject.InfoNews;
import cn.edu.csu.information.service.CategoryService;
import cn.edu.csu.information.service.IndexNewsListService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Controller
//@RequestMapping("/")
public class IndexController {

    @Resource
    private CategoryService categoryService;
    @Resource
    private IndexNewsListService indexNewsListService;

    @GetMapping("/news_list")
    @ResponseBody
    public String news_list(){
        Pageable newsOrderedPageable = PageRequest.of(0,6);
        Page<InfoNews> newsOrderedPage = indexNewsListService.findAll(newsOrderedPageable);

        System.out.println("总条数："+newsOrderedPage.getTotalElements());
        System.out.println("总页数："+newsOrderedPage.getTotalPages());
        return "I am news_list";
    }

    @RequestMapping("/")
    public String index(Model model){
        List<InfoCategory> infoCategories = categoryService.findAll();
        Sort sort = new Sort(Sort.Direction.DESC, "clicks");
        List<InfoNews> newsOrderedList = indexNewsListService.findAll(sort);

        model.addAttribute("categories",infoCategories);
        model.addAttribute("news_list",newsOrderedList);
        model.addAttribute("top",CommonConstants.CLICK_RANK_MAX_NEWS);
//        model.addAttribute("",)
//        for(InfoCategory attribute : infoCategories) {
//            System.out.println(attribute);
//        }
        return "news/index";
    }
}
