package cn.edu.csu.information.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data

public class NewsBasicDto {
    private Date createTime;

//    @JsonProperty
    private Integer id;

    private String title;

    private String source;

    private String digest;

    private Integer clicks;

    private String indexImageUrl;

}
