package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class CompositionEntity {
    private Integer compositionId;

    private String userName;

    private String title;

    private String compositionBody;

    private Date releaseTime;

    private String compositionType;

    private Integer shareCount;

    private Integer score;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCompositionBody() {
        return compositionBody;
    }

    public void setCompositionBody(String compositionBody) {
        this.compositionBody = compositionBody == null ? null : compositionBody.trim();
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getCompositionType() {
        return compositionType;
    }

    public void setCompositionType(String compositionType) {
        this.compositionType = compositionType == null ? null : compositionType.trim();
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}