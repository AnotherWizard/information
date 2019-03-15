package cn.edu.csu.information.dto;

import cn.edu.csu.information.dataObject.InfoComment;
import cn.edu.csu.information.dataObject.InfoUser;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDetailDto {
    private Date createTime;

    private String createTimeStr = "";

    private Date updateTime = new Date();

    private Integer id;

    private Integer userId;

    private InfoUser user;

    private Integer newsId;

    private Integer parentId;

    private InfoComment parent;

    private Integer likeCount;

    private String content;

    private Boolean liked;
}
