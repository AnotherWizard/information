package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InfoCommentLikeRepository extends JpaRepository<InfoCommentLike,Integer> {

    List<InfoCommentLike> findByCommentIdInAndUserId(List<Integer> commentIds, Integer userID);

}