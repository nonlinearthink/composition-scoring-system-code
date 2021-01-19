package cn.edu.zucc.se2020g11.api.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 错误返回模型
 *
 * @author nonlinearthink
 */
@Getter
@Setter
public class ErrorModel {

    private boolean success;

    private int code;

    private String message;
}
