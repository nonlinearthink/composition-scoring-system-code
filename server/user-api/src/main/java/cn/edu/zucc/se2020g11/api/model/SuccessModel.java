package cn.edu.zucc.se2020g11.api.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 成功返回模型
 *
 * @author nonlinearthink
 */
@Getter
@Setter
public class SuccessModel {

    private boolean success;

    private Object data;
}
