package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoCommentLikeRepository extends JpaRepository<InfoCommentLike,Integer> {


}