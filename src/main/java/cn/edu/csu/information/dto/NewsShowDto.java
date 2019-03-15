package cn.edu.csu.information.dto;

import cn.edu.csu.information.dataObject.InfoCategory;
import cn.edu.csu.information.dataObject.InfoUser;
import lombok.Data;

import java.util.Date;

@Data
public class NewsShowDto {

    private Integer id;

    private String title;

    private String source;

    private String digest;

    private String content;

    private Integer commentsCount;

    private Date createTime;

    private String createTimeStr = "";

    private Integer clicks;

    private InfoCategory category;

    private String indexImageUrl;

    private UserShowDto author;
}

