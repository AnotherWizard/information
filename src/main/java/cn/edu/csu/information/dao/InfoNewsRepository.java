package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InfoNewsRepository extends JpaRepository<InfoNews, Integer> {
    /**
     * 根据新闻的状态查找新闻
     *
     * @param status
     * @return
     */
    List<InfoNews> findByStatus(Integer status);

    /**
     * 查找不满足给定状态的新闻
     * @param status
     * @return
     */
    List<InfoNews> findByStatusNot(Integer status);

//    Page<InfoNews> findByOrderByClicks(Pageable pageable);

    @Override
    List<InfoNews> findAll(Sort sort);

    @Query("select news from InfoNews news ORDER BY news.createTime DESC")
    Page<InfoNews> findAll(Pageable pageable);

    Page<InfoNews> findByCategoryIdAndStatusOrderByCreateTimeDesc(Integer categoryId,  Integer status, Pageable pageable);

    Page<InfoNews> findByStatusOrderByCreateTimeDesc(Integer status, Pageable pageable);

    List<InfoNews> findByCategoryIdAndStatusOrderByCreateTimeDesc(Integer categoryId,  Integer status);

    List<InfoNews> findByStatusOrderByCreateTimeDesc(Integer status);



}