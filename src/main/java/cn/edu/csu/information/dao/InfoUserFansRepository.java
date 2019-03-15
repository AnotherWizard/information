package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoUserFans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InfoUserFansRepository  extends JpaRepository<InfoUserFans,Integer> {

    List<InfoUserFans> findByFollowerId(Integer followerId);

}