package cn.edu.csu.information.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserCountVo {
    private Integer  totalCount;
    private Integer monCount;
    private Integer dayCount;
    private Date activeTime;
    private String activeCount;
}
