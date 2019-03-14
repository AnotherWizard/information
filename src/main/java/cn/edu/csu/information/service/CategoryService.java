package cn.edu.csu.information.service;


import cn.edu.csu.information.dataObject.InfoCategory;

import java.util.List;

public interface CategoryService {
       List<InfoCategory> findAll();

       /**
        * 返回除最新以外的分类
        * @return
        */
       List<InfoCategory> getCategoryExcludeNew();
}
