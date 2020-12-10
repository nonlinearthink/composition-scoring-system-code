package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class HelpEntity {
    private Integer helpId;

    private String adminName;

    private String helpTitle;

    private String helpBody;

    private Date time;

    public Integer getHelpId() {
        return helpId;
    }

    public void setHelpId(Integer helpId) {
        this.helpId = helpId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getHelpTitle() {
        return helpTitle;
    }

    public void setHelpTitle(String helpTitle) {
        this.helpTitle = helpTitle == null ? null : helpTitle.trim();
    }

    public String getHelpBody() {
        return helpBody;
    }

    public void setHelpBody(String helpBody) {
        this.helpBody = helpBody == null ? null : helpBody.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}