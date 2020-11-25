package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class DynamicCommentEntity {
    private Integer dcommentId2;

    private String userName;

    private Integer nextDcommentId2;

    private Integer dynamicId;

    private String commentBody;

    private Date dateTime;

    public Integer getDcommentId2() {
        return dcommentId2;
    }

    public void setDcommentId2(Integer dcommentId2) {
        this.dcommentId2 = dcommentId2;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getNextDcommentId2() {
        return nextDcommentId2;
    }

    public void setNextDcommentId2(Integer nextDcommentId2) {
        this.nextDcommentId2 = nextDcommentId2;
    }

    public Integer getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
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