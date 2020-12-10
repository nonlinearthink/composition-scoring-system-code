package cn.edu.zucc.se2020g11.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

/**
 * 成功返回模型
 *
 * @author nonlinearthink
 */
@Getter
@Setter
public class ApiResult<T> {

    private int code;

    private String msg;

    private T data;
}
