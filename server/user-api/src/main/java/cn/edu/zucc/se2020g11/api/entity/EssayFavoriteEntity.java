package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class EssayFavoriteEntity {
    private Integer eFavoriteId;

    private Integer essayId;

    private String userName;

    private Date dateTime;

    public Integer geteFavoriteId() {
        return eFavoriteId;
    }

    public void seteFavoriteId(Integer eFavoriteId) {
        this.eFavoriteId = eFavoriteId;
    }

    public Integer getEssayId() {
        return essayId;
    }

    public void setEssayId(Integer essayId) {
        this.essayId = essayId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}