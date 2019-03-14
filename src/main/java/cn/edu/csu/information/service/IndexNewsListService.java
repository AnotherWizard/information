package cn.edu.csu.information.service;

import cn.edu.csu.information.dataObject.InfoNews;
import cn.edu.csu.information.dto.NewsDetailDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IndexNewsListService {
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
}
