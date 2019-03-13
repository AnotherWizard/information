package cn.edu.csu.information.service;

import cn.edu.csu.information.dataObject.InfoNews;

import java.util.List;

/**
 * @author liuchengsheng
 */
public interface InforNewsService {

    /**
     * 根据新闻的状态查找新闻
     * @param status
     * @return
     */
    List<InfoNews> findNewsByStatus(Integer status);
}
