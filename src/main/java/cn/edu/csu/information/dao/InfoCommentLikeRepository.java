package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoComment;
import cn.edu.csu.information.dataObject.InfoCommentLike;
import cn.edu.csu.information.dataObject.multiKeys.InfoCommentLikeMultiKey;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface InfoCommentLikeRepository extends JpaRepository<InfoCommentLike, InfoCommentLikeMultiKey> {

    List<InfoCommentLike> findByCommentIdInAndUserId(List<Integer> commentIds, Integer userID);

    Integer countByCommentId(Integer commentId);

    Optional<InfoCommentLike> findByUserIdAndCommentId(Integer userId, Integer commentId);

    @Override
    void deleteById(InfoCommentLikeMultiKey infoCommentLikeMultiKey);
}