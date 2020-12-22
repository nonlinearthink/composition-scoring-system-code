package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class SystemMessageEntity {
    private Integer sMessageId;

    private String adminName;

    private String sMessageBody;

    private Date time;

    public Integer getsMessageId() {
        return sMessageId;
    }

    public void setsMessageId(Integer sMessageId) {
        this.sMessageId = sMessageId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getsMessageBody() {
        return sMessageBody;
    }

    public void setsMessageBody(String sMessageBody) {
        this.sMessageBody = sMessageBody == null ? null : sMessageBody.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}