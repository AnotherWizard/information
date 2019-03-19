package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.CommonConstants;
import cn.edu.csu.information.dataObject.*;
import cn.edu.csu.information.dataObject.multiKeys.InfoUserCollectionMultiKey;
import cn.edu.csu.information.dataObject.multiKeys.InfoUserFansMultiKey;
import cn.edu.csu.information.dto.CommentDetailDto;
import cn.edu.csu.information.dto.NewsShowDto;
import cn.edu.csu.information.dto.UserShowDto;
import cn.edu.csu.information.enums.ResultEnum;
import cn.edu.csu.information.service.CategoryService;
import cn.edu.csu.information.service.CommentService;
import cn.edu.csu.information.service.NewsService;
import cn.edu.csu.information.service.UserService;
import cn.edu.csu.information.utils.DateUtil;
import cn.edu.csu.information.utils.SessionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    @PostMapping(value = "/followed_user")
    @ResponseBody
    public Map<String, Object> followedUser(@RequestBody Map<String, Object> map,HttpServletRequest request){
        InfoUser user = SessionUtil.getUser(request, userService);
        if (user == null){
            return sessionErr();
        }

        String userIdstr = (String) map.get("user_id");
        Integer userId = null;
        if(userIdstr != null) {
            userId = Integer.parseInt(userIdstr);
        }
        String action = (String) map.get("action");

        if(userId == null || action == null || !(action.equals("follow") || action.equals("unfollow"))){
            return paramErr();
        }

        InfoUser other = userService.findUserById(userId);
        if(other == null){
            return noDataErr();
        }

        boolean isFollowed = false;

        if(action.equals("follow")){
            List<InfoUserFans> userFolloweds = userService.findUserFansByFollowerId(userId);
            for(InfoUserFans userFollowed : userFolloweds){
                if(userFollowed.getFollowedId().equals(other.getId())){
                    isFollowed = true;
                }
            }
            if(!isFollowed){
                InfoUserFans userFans = new InfoUserFans(userId,other.getId());
                userService.saveUserFans(userFans);
            }
        }else{
            InfoUserFansMultiKey userFansMultiKey = new InfoUserFansMultiKey(userId, other.getId());
            userService.deleteFansById(userFansMultiKey);
        }

        return okMsg();
    }

    @PostMapping(value = "/news_comment")
    @ResponseBody
    public Map<String,Object> newsComment(@RequestBody Map<String, Object> map,HttpServletRequest request){
        InfoUser user = SessionUtil.getUser(request, userService);
        if (user == null){
            return sessionErr();
        }

        String newsIdstr = (String) map.get("news_id");
        String comment_content = (String) map.get("comment");
        String parentIdstr = (String) map.get("parent_id");
        Integer parentId = null;
        Integer newsId = null;
        if(newsIdstr != null){
            newsId = Integer.parseInt(newsIdstr);
        }else{
            return paramErr();
        }

        if(comment_content == null){
            return paramErr();
        }

        Optional<InfoNews> infoNews = newsService.findById(newsId);
        if(!infoNews.isPresent()){
            return noDataErr();
        }

        InfoComment comment = new InfoComment();
        comment.setNewsId(newsId);
        comment.setUserId(user.getId());
        comment.setContent(comment_content);
        if(parentIdstr != null){
            parentId = Integer.parseInt(parentIdstr);
            comment.setParentId(parentId);
        }

        commentService.saveComment(comment);

        return okMsg();


    }

    @PostMapping(value = "/news_collect")
    @ResponseBody
    public Map<String,Object> newsCollect(@RequestBody Map<String, Object> map,HttpServletRequest request){
        InfoUser user = SessionUtil.getUser(request, userService);
        if (user == null){
            return sessionErr();
        }

        String newsIdstr = (String) map.get("news_id");
        Integer newsId = null;
        if(newsIdstr != null){
            newsId = Integer.parseInt(newsIdstr);
        }
        String action = (String) map.get("action");

        if(newsId == null || action == null || !(action.equals("collect") || action.equals("cancel_collect"))){
            return paramErr();
        }

        Optional<InfoNews> infoNews = newsService.findById(newsId);
        if(!infoNews.isPresent()){
            return noDataErr();
        }

        if(action.equals("cancel_collect")){
            List<InfoUserCollection> userCollections = userService.findUserCollectionByUserId(user.getId());
            for(InfoUserCollection userCollection : userCollections){
                if(newsId.equals(userCollection.getNewsId())){
                    InfoUserCollectionMultiKey userCollectionMultiKey = new InfoUserCollectionMultiKey(user.getId(), newsId);
                    userService.deleteUserCollectionById(userCollectionMultiKey);
                }
            }
        }else{
            InfoUserCollectionMultiKey userCollectionMultiKey = new InfoUserCollectionMultiKey(user.getId(),newsId);

            InfoUserCollection userCollection = new InfoUserCollection(user.getId(),newsId,new Date());
            userService.saveUserCollection(userCollection);
        }

        return okMsg();

    }

    @RequestMapping(value = "/{newsId}")
    public String newsDetail(HttpServletRequest request, Model model, @PathVariable("newsId") Integer newsId) {

        InfoUser infoUser = SessionUtil.getUser(request, userService);
        if (infoUser!=null){
            model.addAttribute("user",infoUser);
        }

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
        return "news/detail";
    }

    private Map<String,Object> sessionErr(){
        Map<String,Object> jsonBag = new HashMap<>();
        jsonBag.put("errmsg",ResultEnum.SESSIONERR.getMsg());
        jsonBag.put("errno",ResultEnum.SESSIONERR.getCode());
        return jsonBag;
    }

    private Map<String,Object> paramErr(){
        Map<String,Object> jsonBag = new HashMap<>();
        jsonBag.put("errmsg",ResultEnum.PARAMERR.getMsg());
        jsonBag.put("errno",ResultEnum.PARAMERR.getCode());
        return jsonBag;
    }

    private Map<String,Object> noDataErr(){
        Map<String,Object> jsonBag = new HashMap<>();
        jsonBag.put("errmsg",ResultEnum.NODATA.getMsg());
        jsonBag.put("errno",ResultEnum.NODATA.getCode());
        return jsonBag;
    }

    private Map<String,Object> okMsg(){
        Map<String,Object> jsonBag = new HashMap<>();
        jsonBag.put("errmsg",ResultEnum.OK.getMsg());
        jsonBag.put("errno",ResultEnum.OK.getCode());
        return jsonBag;
    }

    private Boolean ifCollected(InfoUser infoUser, Integer newsId){

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
