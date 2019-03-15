package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InfoCategoryRepository  extends JpaRepository<InfoCategory,Integer> {

    /**
     * 查询指定分类以外的新闻分类
     * @param id
     * @return
     */
    List<InfoCategory> findByIdNot(Integer id);

}