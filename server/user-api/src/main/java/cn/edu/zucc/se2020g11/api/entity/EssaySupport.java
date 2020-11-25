package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class EssaySupport {
    private Integer eSupportId;

    private String userName;

    private Integer essayId;

    private Date dateTime;

    public Integer geteSupportId() {
        return eSupportId;
    }

    public void seteSupportId(Integer eSupportId) {
        this.eSupportId = eSupportId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getEssayId() {
        return essayId;
    }

    public void setEssayId(Integer essayId) {
        this.essayId = essayId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}