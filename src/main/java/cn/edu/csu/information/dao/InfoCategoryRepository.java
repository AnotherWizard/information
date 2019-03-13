package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InfoCategoryRepository  extends JpaRepository<InfoCategory,Integer> {

    @Override
    List<InfoCategory> findAll();
}