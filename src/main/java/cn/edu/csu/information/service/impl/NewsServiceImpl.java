package cn.edu.csu.information.service.impl;

import cn.edu.csu.information.constants.AdminConstants;
import cn.edu.csu.information.dao.InfoCategoryRepository;
import cn.edu.csu.information.dao.InfoNewsRepository;
import cn.edu.csu.information.dataObject.InfoCategory;
import cn.edu.csu.information.dataObject.InfoNews;
import cn.edu.csu.information.dto.NewsDetailDto;
import cn.edu.csu.information.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private InfoNewsRepository infoNewsRepository;

    @Autowired
    private InfoCategoryRepository categoryRepository;

    @Override
    public List<InfoNews> findAll(Sort sort) {
        return infoNewsRepository.findAll(sort);
    }


   @Override
    public Page<InfoNews> findAll(Pageable pageable) {
        return infoNewsRepository.findAll(pageable);
    }

    @Override
    public Page<InfoNews> findByCategoryIdAndStatusOrderByCreateTimeDesc(Integer categoryId, Integer status, Pageable pageable) {
        return infoNewsRepository.findByCategoryIdAndStatusOrderByCreateTimeDesc(categoryId, status, pageable);
    }

    @Override
    public Page<InfoNews> findByStatusOrderByCreateTimeDesc(Integer status, Pageable pageable) {
        return infoNewsRepository.findByStatusOrderByCreateTimeDesc(status ,pageable);
    }

    @Override
    public List<InfoNews> findByCategoryIdAndStatusOrderByCreateTimeDesc(Integer categoryId, Integer status) {
        return infoNewsRepository.findByCategoryIdAndStatusOrderByCreateTimeDesc(categoryId, status);
    }

    @Override
    public List<InfoNews> findByStatusOrderByCreateTimeDesc(Integer status) {
        return infoNewsRepository.findByStatusOrderByCreateTimeDesc(status);
    }


    @Override
    public List<InfoNews> findNewsByStatus(Integer status) {
        return infoNewsRepository.findByStatus(status);
    }

    @Override
    public List<InfoNews> findNewsNotPub() {
        return infoNewsRepository.findByStatusNot(AdminConstants.NEWS_REVIEW_PASS);
    }

    @Override
    public NewsDetailDto findNewsById(Integer id) {
        NewsDetailDto newsDetailDto = new NewsDetailDto();
        InfoNews news = infoNewsRepository.getOne(id);

        InfoCategory category = categoryRepository.getOne(news.getCategoryId());
        newsDetailDto.setInfoNews(news);
        newsDetailDto.setInfoCategory(category);

        return newsDetailDto;
    }

    @Override
    public InfoNews updateNews(InfoNews infoNews) {
        return infoNewsRepository.save(infoNews);
    }
}
