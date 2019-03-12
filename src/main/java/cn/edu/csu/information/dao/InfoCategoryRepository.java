package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoCategory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InfoCategoryRepository  extends JpaRepository<InfoCategory,Integer> {

}