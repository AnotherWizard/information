package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.CommonConstants;
import cn.edu.csu.information.dataObject.*;
import cn.edu.csu.information.dto.CommentDetailDto;
import cn.edu.csu.information.dto.NewsShowDto;
import cn.edu.csu.information.dto.UserShowDto;
import cn.edu.csu.information.service.CategoryService;
import cn.edu.csu.information.service.CommentService;
import cn.edu.csu.information.service.NewsService;
import cn.edu.csu.information.service.UserService;
import cn.edu.csu.information.utils.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedList;
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

    @RequestMapping(value = "/{newsId}")
    public String newsDetail(Model model, @PathVariable("newsId") Integer newsId) {
        InfoUser infoUser = null;
        /*** 此处获取用户登录信息 ***/
//        infoUser = session.get(user)

        rankList(model, categoryService, newsService);
        InfoNews infoNews = newsService.findNewsById(newsId).getInfoNews();
        infoNews.setClicks(infoNews.getClicks() + 1);

        List<InfoComment> infoComments = commentService.findCommentByNewsIdOrderByCreateTimeDesc(newsId);

        Boolean isCollected = false;
        List<Integer> infoCommentLikeIds = new LinkedList<>();
        if (infoUser != null) {
            isCollected = ifCollected(infoUser, newsId);

            List<Integer> infoCommentIds = getCommentIds(infoComments);
            List<InfoCommentLike> infoCommentLikes = commentService.findCommentLikeByCommentIdInAndUserId(infoCommentIds, infoUser.getId());
            infoCommentLikeIds = getCommentLikeIds(infoCommentLikes);
        }

        List<CommentDetailDto> commentDictLi = getCommentDictLi(infoComments, infoCommentLikeIds);

        Boolean isFollowed = false;
        if (infoNews.getUserId() != null && infoUser != null) {
            isFollowed = ifFollowed(infoNews, infoUser);
        }

        if (infoUser != null) {
            /*** 此处要对infoUser做数据处理转化为dto(还没做) ***/
            model.addAttribute("user", infoUser);
        } else {
            model.addAttribute("user", null);
        }
        UserShowDto userShowDto = null;
        if (infoNews.getUserId() != null) {
            userShowDto = new UserShowDto();
            InfoUser authorUser = userService.findUserById(infoNews.getUserId());
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
        model.addAttribute("is_collected", isCollected);
        model.addAttribute("is_followed", isFollowed);
        model.addAttribute("comments", commentDictLi);

//        System.out.println(infoNews);
        return "news/detail-temp";
    }

    private Boolean ifCollected(InfoUser infoUser, Integer newsId) {
        List<InfoUserCollection> infoUserCollections = userService.findUserCollectionByUserId(infoUser.getId());
        for (InfoUserCollection infoUserCollection : infoUserCollections) {
            if (infoUserCollection.getNewsId().equals(newsId)) {
                return CommonConstants.IS_COLLECTED;
            }
        }
        return CommonConstants.NOT_COLLECTED;
    }

    private Boolean ifFollowed(InfoNews infoNews, InfoUser infoUser) {
        List<InfoUserFans> infoUserFansList = userService.findUserFansByFollowerId(infoUser.getId());
        for (InfoUserFans infoUserFans : infoUserFansList) {
            if (infoUserFans.getFollowedId().equals(infoNews.getUserId())) {
                return CommonConstants.IS_FOLLOWED;
            }
        }
        return CommonConstants.NOT_FOLLOWED;
    }

    private List<Integer> getCommentIds(List<InfoComment> infoComments) {
        List<Integer> commentIds = new LinkedList<>();
        for (InfoComment infoComment : infoComments) {
            commentIds.add(infoComment.getId());
        }
        return commentIds;
    }

    private List<Integer> getCommentLikeIds(List<InfoCommentLike> infoCommentLikes) {
        List<Integer> commentLikeIds = new LinkedList<>();
        for (InfoCommentLike infoCommentLike : infoCommentLikes) {
            commentLikeIds.add(infoCommentLike.getCommentId());
        }
        return commentLikeIds;
    }

    private List<CommentDetailDto> getCommentDictLi(List<InfoComment> infoComments, List<Integer> infoCommentLikeIds) {
        List<CommentDetailDto> commentDictLi = new LinkedList<>();
        for (InfoComment infoComment : infoComments) {
            CommentDetailDto commentDetailDto = new CommentDetailDto();
            BeanUtils.copyProperties(infoComment, commentDetailDto);
            commentDetailDto.setCreateTimeStr(
                    DateUtil.formatDate2(commentDetailDto.getCreateTime()));
            commentDetailDto.setLiked(false);

            for (Integer commentLikeIds : infoCommentLikeIds) {
                if (infoComment.getId().equals(commentLikeIds)) {
                    commentDetailDto.setLiked(true);
                }
            }

            commentDetailDto.setUser(userService.findUserById(commentDetailDto.getUserId()));
            if (commentDetailDto.getParentId() != null) {
                InfoComment parent = commentService.findCommentByCommentId(commentDetailDto.getParentId()).get();
                parent.setUserNickName(userService.findUserById(parent.getUserId()).getNickName());
                commentDetailDto.setParent(parent);
            }

            commentDictLi.add(commentDetailDto);
        }
        return commentDictLi;
    }

}
