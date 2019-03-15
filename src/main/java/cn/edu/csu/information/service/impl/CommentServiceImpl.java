package cn.edu.csu.information.service.impl;

import cn.edu.csu.information.dao.InfoCommentLikeRepository;
import cn.edu.csu.information.dao.InfoCommentRepository;
import cn.edu.csu.information.dataObject.InfoComment;
import cn.edu.csu.information.dataObject.InfoCommentLike;
import cn.edu.csu.information.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

