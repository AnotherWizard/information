package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InfoUserRepository  extends JpaRepository<InfoUser,Integer> {

}