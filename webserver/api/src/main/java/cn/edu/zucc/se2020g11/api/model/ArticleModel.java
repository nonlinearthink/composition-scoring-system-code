package cn.edu.zucc.se2020g11.api.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 推送文章模型
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
public class ArticleModel
{
    @NotNull(message = "articleId不能为空")
    private Integer articleId;

    @NotNull(message = "articleTitle不能为空")
    private String articleTitle;

    @NotNull(message = "articleBody不能为空")
    private String articleBody;

    @NotNull(message = "time不能为空")
    private Date time;

    @NotNull(message = "avatarUrl不能为空")
    private String avatarUrl;
}