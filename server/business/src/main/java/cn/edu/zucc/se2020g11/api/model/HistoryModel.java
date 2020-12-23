package cn.edu.zucc.se2020g11.api.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 历史记录模型
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
public class HistoryModel
{
    @NotNull(message = "nickname不能为空")
    private String nickname;

    @NotNull(message = "title不能为空")
    private String title;

    @NotNull(message = "compositionBody不能为空")
    private String compositionBody;

    @NotNull(message = "time不能为空")
    private Date time;
}
