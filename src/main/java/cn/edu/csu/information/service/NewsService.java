package cn.edu.csu.information.service;

import cn.edu.csu.information.dataObject.InfoNews;
import cn.edu.csu.information.dto.NewsDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface NewsService {
//    Page<InfoNews> findByOrderByClicks(Pageable pageable);
    List<InfoNews> findNewsAll(Sort sort);

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

    Page<InfoNews> findNewsAllByOrderByCreateTimeDesc(Pageable pageable);

    Page<InfoNews> findNewsByCategoryIdAndStatusOrderByCreateTimeDesc(Integer categoryId,  Integer status, Pageable pageable);

    Page<InfoNews> findNewsByStatusOrderByCreateTimeDesc(Integer status, Pageable pageable);

    List<InfoNews> findNewsByCategoryIdAndStatusOrderByCreateTimeDesc(Integer categoryId,  Integer status);

    List<InfoNews> findNewsByStatusOrderByCreateTimeDesc(Integer status);

    InfoNews updateNews(InfoNews infoNews);

    Optional<InfoNews> findById(Integer integer);

    List<InfoNews> findNewsByUserId(Integer userId);

}
