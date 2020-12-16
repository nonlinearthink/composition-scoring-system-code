package cn.edu.zucc.se2020g11.api.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class FollowEntity {
    private Integer followId;

    @NotNull(message = "username不能为空")
    private String username;

    @NotNull(message = "targetUsername不能为空")
    private String targetUsername;

    private Date time;

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername == null ? null : targetUsername.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}