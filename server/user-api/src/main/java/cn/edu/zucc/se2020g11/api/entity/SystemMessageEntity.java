package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class SystemMessageEntity {
    private Integer sMessageId;

    private String sMessageBody;

    private Date dateTime;

    public Integer getsMessageId() {
        return sMessageId;
    }

    public void setsMessageId(Integer sMessageId) {
        this.sMessageId = sMessageId;
    }

    public String getsMessageBody() {
        return sMessageBody;
    }

    public void setsMessageBody(String sMessageBody) {
        this.sMessageBody = sMessageBody == null ? null : sMessageBody.trim();
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}