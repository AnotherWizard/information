package cn.edu.csu.information.service;

import cn.edu.csu.information.dataObject.InfoNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IndexNewsListService {
//    Page<InfoNews> findByOrderByClicks(Pageable pageable);
    List<InfoNews> findAll(Sort sort);
}
