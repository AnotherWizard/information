package cn.edu.csu.information.enums;

import lombok.Getter;


/**
 * @author liu
 */
@Getter
public enum ResultEnum {

    /**
     * 成功
     */
    OK(0,"成功"),
    /**
     * 参数错误
     */
    PARAMERR(201,"参数错误"),

    /**
     * 参数错误
     */
    REASONERR(202,"请输入拒绝原因")

    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
