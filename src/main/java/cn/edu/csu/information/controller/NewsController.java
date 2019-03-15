package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.CommonConstants;
import cn.edu.csu.information.dataObject.*;
import cn.edu.csu.information.dto.CommentBeLikedDto;
import cn.edu.csu.information.dto.NewsShowDto;
import cn.edu.csu.information.dto.UserShowDto;
import cn.edu.csu.information.service.CategoryService;
import cn.edu.csu.information.service.CommentService;
import cn.edu.csu.information.service.NewsService;
import cn.edu.csu.information.service.UserService;
import cn.edu.csu.information.utils.DateUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    public String newsDetail(Model model, @PathVariable("newsId") Integer newsId){
        InfoUser infoUser = null;
        /*** 此处获取用户登录信息 ***/
//        infoUser = session.get(user)

        rankList(model, categoryService, newsService);
        InfoNews infoNews = newsService.findNewsById(newsId).getInfoNews();
        infoNews.setClicks(infoNews.getClicks()+1);

        List<InfoComment> infoComments = commentService.findCommentByNewsIdOrderByCreateTimeDesc(newsId);

        Boolean isCollected = false;
        List<Integer> infoCommentLikeIds = new LinkedList<>();
        if(infoUser != null){
            isCollected = ifCollected(infoUser, newsId);

            List<Integer> infoCommentIds = getCommentIds(infoComments);
            List<InfoCommentLike> infoCommentLikes = commentService.findCommentLikeByCommentIdInAndUserId(infoCommentIds, infoUser.getId());
            infoCommentLikeIds = getCommentLikeIds(infoCommentLikes);
        }

        List<CommentBeLikedDto> commentDictLi = getCommentDictLi(infoComments, infoCommentLikeIds);

        Boolean isFollowed = false;
        if(infoNews.getUserId() != null && infoUser != null){
            isFollowed = ifFollowed(infoNews, infoUser);
        }


        if(infoUser != null){
            /*** 此处要对infoUser做数据处理转化为dto(还没做) ***/
            model.addAttribute("user",infoUser);
        }else{
            model.addAttribute("user", null);
        }
        UserShowDto userShowDto = null;
        if(infoNews.getUserId() != null) {
            userShowDto = new UserShowDto();
            InfoUser authorUser = userService.findUserById(infoNews.getUserId()).get();
            BeanUtils.copyProperties(authorUser, userShowDto);
            userShowDto.setNewsCount(newsService.findNewsByUserId(userShowDto.getId()).size());
            userShowDto.setFollowersCount(userService.findUserFansByFollowedId(userShowDto.getId()).size());
        }

        NewsShowDto newsShowDto = new NewsShowDto();
        BeanUtils.copyProperties(infoNews, newsShowDto);
        newsShowDto.setAuthor(userShowDto);
        newsShowDto.setCommentsCount(commentService.findCommentByNewsId(newsId).size());
        newsShowDto.setCreateTimeStr(DateUtil.formatDate2(newsShowDto.getCreateTime()));
        newsShowDto.setCategory(categoryService.findCategoryById(infoNews.getCategoryId()));

//        System.out.println(newsShowDto);
        model.addAttribute("news", newsShowDto);
        model.addAttribute("is_collected",isCollected);
        model.addAttribute("is_followed",isFollowed);
        model.addAttribute("comments",commentDictLi);



//        System.out.println(infoNews);
        return "news/detail";
    }

    private Boolean ifCollected(InfoUser infoUser, Integer newsId){
        List<InfoUserCollection> infoUserCollections = userService.findUserCollectionByUserId(infoUser.getId());
        for(InfoUserCollection infoUserCollection : infoUserCollections){
            if(infoUserCollection.getNewsId().equals(newsId)){
                return CommonConstants.IS_COLLECTED;
            }
        }
        return  CommonConstants.NOT_COLLECTED;
    }

    private Boolean ifFollowed(InfoNews infoNews, InfoUser infoUser){
        List<InfoUserFans> infoUserFansList = userService.findUserFansByFollowerId(infoUser.getId());
        for(InfoUserFans infoUserFans : infoUserFansList){
            if (infoUserFans.getFollowedId().equals(infoNews.getUserId())){
                return CommonConstants.IS_FOLLOWED;
            }
        }
        return CommonConstants.NOT_FOLLOWED;
    }

    private List<Integer> getCommentIds(List<InfoComment> infoComments){
        List<Integer> commentIds = new LinkedList<>();
        for(InfoComment infoComment : infoComments){
            commentIds.add(infoComment.getId());
        }
        return commentIds;
    }

    private List<Integer> getCommentLikeIds(List<InfoCommentLike> infoCommentLikes){
        List<Integer> commentLikeIds = new LinkedList<>();
        for(InfoCommentLike infoCommentLike : infoCommentLikes){
            commentLikeIds.add(infoCommentLike.getCommentId());
        }
        return commentLikeIds;
    }

    private List<CommentBeLikedDto> getCommentDictLi(List<InfoComment> infoComments, List<Integer> infoCommentLikeIds){
        List<CommentBeLikedDto> commentDictLi = new LinkedList<>();
        for(InfoComment infoComment : infoComments){
            CommentBeLikedDto commentBeLikedDto = new CommentBeLikedDto();
            BeanUtils.copyProperties(infoComment, commentBeLikedDto);
            commentBeLikedDto.setCreateTimeStr(
                    DateUtil.formatDate2(commentBeLikedDto.getCreateTime()));
            commentBeLikedDto.setLiked(false);

            for(Integer commentLikeIds : infoCommentLikeIds){
                if(infoComment.getId().equals(commentLikeIds)){
                    commentBeLikedDto.setLiked(true);
                }
            }
            commentDictLi.add(commentBeLikedDto);
        }
        return  commentDictLi;
    }


}
