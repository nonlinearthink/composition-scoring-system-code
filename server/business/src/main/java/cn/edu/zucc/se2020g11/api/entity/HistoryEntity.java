package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class HistoryEntity {
    private Integer historyId;

    private String username;

    private Integer compositionId;

    private Date time;

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}