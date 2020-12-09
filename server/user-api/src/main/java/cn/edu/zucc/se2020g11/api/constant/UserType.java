package cn.edu.zucc.se2020g11.api.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户类型枚举类
 *
 * @author nonlinearthink
 */
public enum UserType {
    /**
     * 管理员
     */
    ADMIN(0),
    /**
     * 普通用户
     */
    USER(1);

    @Setter
    @Getter
    private int type;

    UserType(int type) {
        this.type = type;
    }
}
