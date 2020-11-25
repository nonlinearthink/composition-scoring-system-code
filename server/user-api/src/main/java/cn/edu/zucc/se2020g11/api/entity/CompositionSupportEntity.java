package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class CompositionSupportEntity {
    private Integer dsupportId3;

    private String userName;

    private Integer compositionId;

    private Date dateTime;

    public Integer getDsupportId3() {
        return dsupportId3;
    }

    public void setDsupportId3(Integer dsupportId3) {
        this.dsupportId3 = dsupportId3;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(Integer compositionId) {
        this.compositionId = compositionId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}