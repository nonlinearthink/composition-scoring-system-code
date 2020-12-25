package cn.edu.zucc.se2020g11.api.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 作文统计模型
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
public class CompositionCountModel
{
    @NotNull(message = "compositionId不能为空")
    private Integer compositionId;

    @NotNull(message = "nickname不能为空")
    private String nickname;

    @NotNull(message = "avatarUrl不能为空")
    private String avatarUrl;

    @NotNull(message = "compositionId不能为空")
    private String title;

    @NotNull(message = "compositionId不能为空")
    private String compositionBody;

    @NotNull(message = "description不能为空")
    private String description;

    @NotNull(message = "releaseTime不能为空")
    private Date releaseTime;

    @NotNull(message = "favoriteCount不能为空")
    private Integer favoriteCount;

    @NotNull(message = "supportCount不能为空")
    private Integer supportCount;

    @NotNull(message = "commentCount不能为空")
    private Integer commentCount;

    @NotNull(message = "historyCount不能为空")
    private Integer historyCount;
}
