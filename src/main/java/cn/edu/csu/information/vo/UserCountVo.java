package cn.edu.csu.information.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserCountVo {
    private Integer  totalCount;
    private Integer monCount;
    private Integer dayCount;
    private List<String> activeTime;
    private List<Integer> activeCount;
}
