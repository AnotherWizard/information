package cn.edu.csu.information.controller;

import cn.edu.csu.information.dataObject.InfoNews;
import cn.edu.csu.information.service.NewsService;
import cn.edu.csu.information.vo.ResultVo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuchengsheng
 */
@RestController
@RequestMapping("/open")
@Api(description = "获取点击量最高的十条新闻")
public class OpenController {

    @Resource
    private NewsService newsService;

    @GetMapping("/news")
    public ResultVo news() {
        ResultVo<List<InfoNews>> resultVo = new ResultVo<>();
        resultVo.setData(newsService.findNewsClickTopTen());
        resultVo.setCode(0);
        resultVo.setMsg("success");
        return resultVo;
    }

}
