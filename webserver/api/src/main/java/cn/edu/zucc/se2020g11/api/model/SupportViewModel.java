package cn.edu.zucc.se2020g11.api.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 收到的赞模型
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
public class SupportViewModel
{
    @NotNull(message = "supportId不能为空")
    private Integer supportId;

    @NotNull(message = "compositionId不能为空")
    private Integer compositionId;

    @NotNull(message = "targetUsername不能为空")
    private String targetUsername;

    @NotNull(message = "username不能为空")
    private String username;

    @NotNull(message = "nickname不能为空")
    private String nickname;

    @NotNull(message = "avatarUrl不能为空")
    private String avatarUrl;

    @NotNull(message = "title不能为空")
    private String title;

    @NotNull(message = "time不能为空")
    private Date time;
}
