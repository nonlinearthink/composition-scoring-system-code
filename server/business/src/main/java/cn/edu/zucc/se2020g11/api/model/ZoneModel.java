package cn.edu.zucc.se2020g11.api.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 个人空间模型
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
public class ZoneModel
{
    @NotNull(message = "username不能为空")
    private String username;

    @NotNull(message = "nickname不能为空")
    private String nickname;

    @NotNull(message = "signature不能为空")
    private String signature;

    @NotNull(message = "avatarUrl不能为空")
    private String avatarUrl;
}
