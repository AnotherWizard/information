package cn.edu.csu.information.service;

import cn.edu.csu.information.dataObject.InfoComment;
import cn.edu.csu.information.dataObject.InfoCommentLike;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<InfoComment> findCommentByNewsIdOrderByCreateTimeDesc(Integer newsId);

    List<InfoCommentLike> findCommentLikeByCommentIdInAndUserId(List<Integer> commentIds, Integer userID);

    List<InfoComment> findCommentByNewsId(Integer newsId);

    Optional<InfoComment> findCommentByCommentId(Integer commentId);

    InfoComment saveComment(InfoComment infoComment);

    Integer countLikeByComentId(Integer commentId);

    Optional<InfoCommentLike> findCommentLikeByUserIdAndCommentId(Integer userId, Integer commentId);

}
