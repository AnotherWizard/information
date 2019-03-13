package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoNewsRepository  extends JpaRepository<InfoNews, Integer> {
    /**
     * 根据新闻的状态查找新闻
     * @param status
     * @return
     */
    List<InfoNews> findByStatus(Integer status);


}