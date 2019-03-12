package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoCommentRepository extends JpaRepository<InfoComment,Integer> {


}