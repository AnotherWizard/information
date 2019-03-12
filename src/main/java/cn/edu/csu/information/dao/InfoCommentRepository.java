package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoComment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InfoCommentRepository extends JpaRepository<InfoComment,Integer> {


}