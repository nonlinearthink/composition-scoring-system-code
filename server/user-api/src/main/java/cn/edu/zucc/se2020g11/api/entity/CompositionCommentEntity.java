package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class CompositionCommentEntity {
    private Integer ccommentId3;

    private Integer compositionId;

    private Integer nextCcommentId3;

    private String userName;

    private String commentBody;

    private Date dateTime;

    public Integer getCcommentId3() {
        return ccommentId3;
    }

    public void setCcommentId3(Integer ccommentId3) {
        this.ccommentId3 = ccommentId3;
    }

    public Integer getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(Integer compositionId) {
        this.compositionId = compositionId;
    }

    public Integer getNextCcommentId3() {
        return nextCcommentId3;
    }

    public void setNextCcommentId3(Integer nextCcommentId3) {
        this.nextCcommentId3 = nextCcommentId3;
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