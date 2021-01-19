package cn.edu.zucc.se2020g11.api.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 用户名卡片模型
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
public class UsernameCardModel
{
    @NotNull(message = "username不能为空")
    private String username;

    @NotNull(message = "nickname不能为空")
    private String nickname;

    @NotNull(message = "signature不能为空")
    private String signature;

    @NotNull(message = "avatarUrl不能为空")
    private String avatarUrl;

    @NotNull(message = "compositionCount不能为空")
    private Integer compositionCount;

    @NotNull(message = "followCount不能为空")
    private Integer followCount;

    @NotNull(message = "isFollow不能为空")
    private Boolean isFollow;
}
