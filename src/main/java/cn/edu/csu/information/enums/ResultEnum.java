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

    NODATA(4002,"未查询到新闻数据"),

    /**
     * 参数错误
     */
    REASONERR(202,"请输入拒绝原因"),

    /**
     * 手机号码格式错误
     */
    MOBILEERR(203,"手机号码格式错误"),
    TIMEOUTERR(204,"验证码以过期"),
    CODEERR(205,"验证码错误"),
    NO_USER(206,"用户不存在"),
    PWDERR(207,"用户名或密码错误"),
    SESSIONERR(4101,"用户未登录")
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
