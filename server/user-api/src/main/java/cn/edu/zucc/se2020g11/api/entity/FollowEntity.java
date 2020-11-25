package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class FollowEntity extends FollowEntityKey {
    private Date dateTime;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}