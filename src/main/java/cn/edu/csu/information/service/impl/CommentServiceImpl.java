package cn.edu.csu.information.service.impl;

import cn.edu.csu.information.dao.InfoCommentLikeRepository;
import cn.edu.csu.information.dao.InfoCommentRepository;
import cn.edu.csu.information.dataObject.InfoComment;
import cn.edu.csu.information.dataObject.InfoCommentLike;
import cn.edu.csu.information.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private InfoCommentRepository infoCommentRepository;

    @Autowired
    private InfoCommentLikeRepository infoCommentLikeRepository;

    @Override
    public List<InfoComment> findCommentByNewsIdOrderByCreateTimeDesc(Integer newsId) {
        return infoCommentRepository.findByNewsIdOrderByCreateTimeDesc(newsId);
    }

    @Override
    public List<InfoCommentLike> findCommentLikeByCommentIdInAndUserId(List<Integer> commentIds, Integer userID) {
        return infoCommentLikeRepository.findByCommentIdInAndUserId(commentIds, userID);
    }

    @Override
    public List<InfoComment> findCommentByNewsId(Integer newsId) {
        return infoCommentRepository.findByNewsId(newsId);
    }

    @Override
    public Optional<InfoComment> findCommentByCommentId(Integer commentId) {
        return infoCommentRepository.findById(commentId);
    }

    @Override
    public InfoComment saveComment(InfoComment infoComment) {
        return infoCommentRepository.save(infoComment);
    }

    @Override
    public Integer countLikeByComentId(Integer commentId) {
        return infoCommentLikeRepository.countByCommentId(commentId);
    }

    @Override
    public Optional<InfoCommentLike> findCommentLikeByUserIdAndCommentId(Integer userId, Integer commentId) {
        return infoCommentLikeRepository.findByUserIdAndCommentId(userId, commentId);
    }
}

