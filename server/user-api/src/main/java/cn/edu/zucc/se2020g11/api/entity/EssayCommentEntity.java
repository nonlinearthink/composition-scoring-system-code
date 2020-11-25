package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class EssayCommentEntity {
    private Integer eCommentId;

    private Integer essayId;

    private String userName;

    private String commentBody;

    private Date dateTime;

    public Integer geteCommentId() {
        return eCommentId;
    }

    public void seteCommentId(Integer eCommentId) {
        this.eCommentId = eCommentId;
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

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody == null ? null : commentBody.trim();
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}