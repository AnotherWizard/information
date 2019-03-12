package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoCategoryRepository  extends JpaRepository<InfoCategory,Integer> {

}