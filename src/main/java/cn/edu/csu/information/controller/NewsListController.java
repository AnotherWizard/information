package cn.edu.csu.information.controller;

import cn.edu.csu.information.dataObject.InfoNews;
import cn.edu.csu.information.service.IndexNewsListService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class NewsListController {
//    @Resource
//    private IndexNewsListService indexNewsListService;

    @GetMapping("/test")
    public String news_list(){
//        Pageable newsOrderedPageable = PageRequest.of(0,6);
//        Page<InfoNews> newsOrderedPage = indexNewsListService.findAll(newsOrderedPageable);
//
//        System.out.println("总条数："+newsOrderedPage.getTotalElements());
//        System.out.println("总页数："+newsOrderedPage.getTotalPages());
//        return "I am news_list";
        return "I am test";
    }


}
