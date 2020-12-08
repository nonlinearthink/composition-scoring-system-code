package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class ReCommentEntity {
    private Integer reCommentId;

    private String username;

    private Integer commentId;

    private String reCommentBody;

    private Date time;

    private Boolean valid;

    public Integer getReCommentId() {
        return reCommentId;
    }

    public void setReCommentId(Integer reCommentId) {
        this.reCommentId = reCommentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getReCommentBody() {
        return reCommentBody;
    }

    public void setReCommentBody(String reCommentBody) {
        this.reCommentBody = reCommentBody == null ? null : reCommentBody.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}