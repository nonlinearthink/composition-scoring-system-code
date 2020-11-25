package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class DynamicEntity {
    private Integer dynamicId;

    private String userName;

    private String dynamicBody;

    private Date releaseTime;

    private Boolean isShare;

    private String source;

    public Integer getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getDynamicBody() {
        return dynamicBody;
    }

    public void setDynamicBody(String dynamicBody) {
        this.dynamicBody = dynamicBody == null ? null : dynamicBody.trim();
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Boolean getIsShare() {
        return isShare;
    }

    public void setIsShare(Boolean isShare) {
        this.isShare = isShare;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }
}