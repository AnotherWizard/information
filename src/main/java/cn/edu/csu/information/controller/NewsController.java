package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.CommonConstants;
import cn.edu.csu.information.dataObject.*;
import cn.edu.csu.information.service.CategoryService;
import cn.edu.csu.information.service.CommentService;
import cn.edu.csu.information.service.NewsService;
import cn.edu.csu.information.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

import static cn.edu.csu.information.controller.IndexController.rankList;

@Controller
@RequestMapping("/news")
public class NewsController {

    @Resource
    private CategoryService categoryService;
    @Resource
    private NewsService newsService;
    @Resource
    private UserService userService;
    @Resource
    private CommentService commentService;

    @GetMapping(value = "/{newsId}")
    @ResponseBody
    public String newsDetail(Model model, @PathVariable("newsId") Integer newsId){
        InfoUser infoUser = null;
//        infoUser = session.get(user)
        rankList(model, categoryService, newsService);
        InfoNews infoNews = newsService.findNewsById(newsId).getInfoNews();
        infoNews.setClicks(infoNews.getClicks()+1);

        Boolean isCollected = false;
        if(infoUser != null){
            List<InfoUserCollection> infoUserCollections = userService.findUserCollectionByUserId(infoUser.getId());
            for(InfoUserCollection infoUserCollection : infoUserCollections){
                if(infoUserCollection.getNewsId() == newsId){
                    isCollected = true;
                    break;
                }
            }
        }

        List<InfoComment> infoComments = commentService.findCommentByNewsIdOrderByCreateTimeDesc(newsId);





//        System.out.println(infoNews);
        return "I am news " + newsId;
    }


}
