package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InfoCommentRepository extends JpaRepository<InfoComment,Integer> {

    List<InfoComment> findByNewsIdOrderByCreateTimeDesc(Integer newsId);

    List<InfoComment> findByNewsId(Integer newsId);

}