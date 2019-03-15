package cn.edu.csu.information.service;

import cn.edu.csu.information.dataObject.InfoComment;
import cn.edu.csu.information.dataObject.InfoCommentLike;

import java.util.List;

public interface CommentService {
    List<InfoComment> findCommentByNewsIdOrderByCreateTimeDesc(Integer newsId);

    List<InfoCommentLike> findCommentLikeByCommentIdInAndUserId(List<Integer> commentIds, Integer userID);

    List<InfoComment> findCommentByNewsId(Integer newsId);
}
