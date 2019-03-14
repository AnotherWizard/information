package cn.edu.csu.information.service;

import cn.edu.csu.information.dataObject.InfoNews;
import cn.edu.csu.information.dto.NewsDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface NewsService {
//    Page<InfoNews> findByOrderByClicks(Pageable pageable);
    List<InfoNews> findAll(Sort sort);

    /**
     * 根据新闻的状态查找新闻
     * @param status
     * @return
     */
    List<InfoNews> findNewsByStatus(Integer status);

    /**
     * 查询没有发布的新闻
     */
    List<InfoNews> findNewsNotPub();

    /**
     * 根据id查询新闻
     * @param id
     * @return
     */
    NewsDetailDto findNewsById(Integer id);

    Page<InfoNews> findAll(Pageable pageable);

    Page<InfoNews> findByCategoryIdAndStatusOrderByCreateTimeDesc(Integer categoryId,  Integer status, Pageable pageable);

    Page<InfoNews> findByStatusOrderByCreateTimeDesc(Integer status, Pageable pageable);

    List<InfoNews> findByCategoryIdAndStatusOrderByCreateTimeDesc(Integer categoryId,  Integer status);

    List<InfoNews> findByStatusOrderByCreateTimeDesc(Integer status);

    InfoNews updateNews(InfoNews infoNews);

}
