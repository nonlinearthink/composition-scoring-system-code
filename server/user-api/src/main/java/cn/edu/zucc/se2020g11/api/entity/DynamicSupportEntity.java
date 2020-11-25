package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class DynamicSupportEntity {
    private Integer dsupportId2;

    private String userName;

    private Integer dynamicId;

    private Date dateTime;

    public Integer getDsupportId2() {
        return dsupportId2;
    }

    public void setDsupportId2(Integer dsupportId2) {
        this.dsupportId2 = dsupportId2;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}