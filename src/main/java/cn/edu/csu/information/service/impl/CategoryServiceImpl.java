package cn.edu.csu.information.service.impl;

import cn.edu.csu.information.constants.AdminConstants;
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

    @Override
    public List<InfoCategory> getCategoryExcludeNew() {
        return infoCategoryRepository.findByIdNot(AdminConstants.CATEGORY_OF_NEW);
    }

    @Override
    public InfoCategory save(InfoCategory infoCategory) {
        return infoCategoryRepository.save(infoCategory);
    }

    @Override
    public InfoCategory findCategoryById(Integer integer) {
        return infoCategoryRepository.getOne(integer);
    }
}
