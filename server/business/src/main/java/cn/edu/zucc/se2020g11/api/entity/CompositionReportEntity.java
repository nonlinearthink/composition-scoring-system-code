package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class CompositionReportEntity {
    private Integer compositionReportId;

    private String username;

    private Integer compositionId;

    private String compositionReportBody;

    private Date time;

    private Integer status;

    public Integer getCompositionReportId() {
        return compositionReportId;
    }

    public void setCompositionReportId(Integer compositionReportId) {
        this.compositionReportId = compositionReportId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(Integer compositionId) {
        this.compositionId = compositionId;
    }

    public String getCompositionReportBody() {
        return compositionReportBody;
    }

    public void setCompositionReportBody(String compositionReportBody) {
        this.compositionReportBody = compositionReportBody == null ? null : compositionReportBody.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}