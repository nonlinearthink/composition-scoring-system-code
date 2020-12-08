package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class CompositionEntity {
    private Integer compositionId;

    private String username;

    private Date releaseTime;

    private String compositionBody;

    private Boolean isDraft;

    private String title;

    private String description;

    private Integer visibility;

    private Integer score;

    private Boolean valid;

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

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getCompositionBody() {
        return compositionBody;
    }

    public void setCompositionBody(String compositionBody) {
        this.compositionBody = compositionBody == null ? null : compositionBody.trim();
    }

    public Boolean getIsDraft() {
        return isDraft;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}