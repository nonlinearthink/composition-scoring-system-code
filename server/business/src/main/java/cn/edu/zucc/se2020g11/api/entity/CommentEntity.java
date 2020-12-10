package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class CommentEntity {
    private Integer commentId;

    private Integer compositionId;

    private String username;

    private String commentBody;

    private Date time;

    private Boolean valid;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
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

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody == null ? null : commentBody.trim();
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