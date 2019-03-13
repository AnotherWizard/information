package cn.edu.csu.information.service.impl;

import cn.edu.csu.information.dao.InfoNewsRepository;
import cn.edu.csu.information.dataObject.InfoNews;
import cn.edu.csu.information.service.IndexNewsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexNewsListServiceImpl implements IndexNewsListService {

    @Autowired
    private InfoNewsRepository infoNewsRepository;

    @Override
    public List<InfoNews> findAll(Sort sort) {
        return infoNewsRepository.findAll(sort);
    }


//    @Override
//    public Page<InfoNews> findByOrderByClicks(Pageable pageable) {
//        return infoNewsRepository.findByOrderByClicks(pageable);
//    }
}
