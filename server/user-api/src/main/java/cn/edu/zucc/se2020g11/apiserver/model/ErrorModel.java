package cn.edu.zucc.se2020g11.apiserver.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author nonlinearthink
 */
@Getter
@Setter
public class ErrorModel {

    private int code;

    private String message;
}
