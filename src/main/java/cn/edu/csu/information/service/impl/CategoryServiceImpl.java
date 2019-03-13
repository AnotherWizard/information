package cn.edu.csu.information.service.impl;

import cn.edu.csu.information.dao.InfoCategoryRepository;
import cn.edu.csu.information.dataObject.InfoCategory;
import cn.edu.csu.information.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private InfoCategoryRepository infoCategoryRepository;

    @Override
    public List<InfoCategory> findAll() {
        return infoCategoryRepository.findAll();
    }
}
