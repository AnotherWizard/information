package cn.edu.csu.information.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentBeLikedDto {
    private Date createTime;

    private String createTimeStr = "";

    private Date updateTime = new Date();

    private Integer id;

    private Integer userId;

    private Integer newsId;

    private Integer parentId;

    private Integer likeCount;

    private String content;

    private boolean liked;
}
