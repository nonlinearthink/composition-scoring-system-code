package cn.edu.zucc.se2020g11.apiserver.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author nonlinearthink
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ResultStatusEnum {
    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String message;

    /**
     * 请求成功
     */
    SUCESS(200,"请求成功"),

    PASSWORD_NOT_MATCHING(400,"密码错误");

    ;
}
