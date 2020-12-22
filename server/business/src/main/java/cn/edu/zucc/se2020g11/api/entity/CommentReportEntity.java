package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class CommentReportEntity {
    private Integer commentReportId;

    private Integer commentId;

    private String username;

    private String commentReportBody;

    private Date time;

    private Integer status;

    public Integer getCommentReportId() {
        return commentReportId;
    }

    public void setCommentReportId(Integer commentReportId) {
        this.commentReportId = commentReportId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getCommentReportBody() {
        return commentReportBody;
    }

    public void setCommentReportBody(String commentReportBody) {
        this.commentReportBody = commentReportBody == null ? null : commentReportBody.trim();
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