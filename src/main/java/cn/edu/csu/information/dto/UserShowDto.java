package cn.edu.csu.information.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserShowDto {

    private Integer id;

    private String nickName;

    private String mobile;

    private String avatarUrl;

    private String gender = "MAN";

    private String signature = "";

    private Integer followersCount;

    private Integer newsCount;

}

