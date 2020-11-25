package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class CompositionHistoryEntity {
    private Integer cHistoryId;

    private String userName;

    private Integer compositionId;

    private Date dateTime;

    public Integer getcHistoryId() {
        return cHistoryId;
    }

    public void setcHistoryId(Integer cHistoryId) {
        this.cHistoryId = cHistoryId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(Integer compositionId) {
        this.compositionId = compositionId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}