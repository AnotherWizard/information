package cn.edu.csu.information.controller;

import cn.edu.csu.information.dao.InfoCategoryRepository;
import cn.edu.csu.information.dataObject.InfoCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
//@RequestMapping("/")
public class BaseController {

    @Resource
    private InfoCategoryRepository infoCategoryRepository;

    @RequestMapping("/base")
    public String index(Model model){
        List<InfoCategory> infoCategories = infoCategoryRepository.findAll();
        model.addAttribute("categories",infoCategories);
//        for(InfoCategory attribute : infoCategories) {
//            System.out.println(attribute);
//        }
        return "news/index";
    }
}
