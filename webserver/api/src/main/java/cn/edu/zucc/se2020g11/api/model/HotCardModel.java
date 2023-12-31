package cn.edu.zucc.se2020g11.api.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 热榜卡片模型
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
public class HotCardModel
{
    @NotNull(message = "compositionId不能为空")
    private Integer compositionId;

    @NotNull(message = "avatarUrl不能为空")
    private String avatarUrl;

    @NotNull(message = "nickname不能为空")
    private String nickname;

    @NotNull(message = "title不能为空")
    private String title;

    @NotNull(message = "compositionBody不能为空")
    private String compositionBody;

    @NotNull(message = "releaseTime不能为空")
    private Date releaseTime;

    @NotNull(message = "hotCount不能为空")
    private Double hotCount;
}
