package cn.edu.zucc.se2020g11.api.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 违规模型
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
public class ReportModel
{
    @NotNull(message = "username不能为空")
    private String username;

    @NotNull(message = "compositionReportCount不能为空")
    private String compositionReportCount;

    @NotNull(message = "commentReportCount不能为空")
    private String commentReportCount;

    @NotNull(message = "count不能为空")
    private Integer count;

    @NotNull(message = "frozen不能为空")
    private Boolean frozen;
}
