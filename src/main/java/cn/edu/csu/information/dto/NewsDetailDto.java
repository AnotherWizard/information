package cn.edu.csu.information.dto;

import cn.edu.csu.information.dataObject.InfoCategory;
import cn.edu.csu.information.dataObject.InfoNews;
import lombok.Data;

/**
 * @author liuchengsheng
 */
@Data
public class NewsDetailDto {

    private InfoNews infoNews;
    private InfoCategory infoCategory;
}
