package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class FavoriteEntity {
    private Integer favotiteId;

    private Integer compositionId;

    private String username;

    private Date time;

    public Integer getFavotiteId() {
        return favotiteId;
    }

    public void setFavotiteId(Integer favotiteId) {
        this.favotiteId = favotiteId;
    }

    public Integer getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(Integer compositionId) {
        this.compositionId = compositionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}