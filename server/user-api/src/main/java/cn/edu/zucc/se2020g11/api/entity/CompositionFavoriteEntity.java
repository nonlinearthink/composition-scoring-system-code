package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class CompositionFavoriteEntity {
    private Integer cFavotiteId;

    private Integer compositionId;

    private String userName;

    private Date dateTime;

    public Integer getcFavotiteId() {
        return cFavotiteId;
    }

    public void setcFavotiteId(Integer cFavotiteId) {
        this.cFavotiteId = cFavotiteId;
    }

    public Integer getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(Integer compositionId) {
        this.compositionId = compositionId;
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