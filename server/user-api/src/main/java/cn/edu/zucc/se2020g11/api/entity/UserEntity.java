package cn.edu.zucc.se2020g11.api.entity;

import java.util.Date;

public class UserEntity {
    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private String signature;

    private String avatarUrl;

    private Boolean isMale;

    private Integer vipDays;

    private Boolean frozen;

    private Date defrostingTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public Boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(Boolean isMale) {
        this.isMale = isMale;
    }

    public Integer getVipDays() {
        return vipDays;
    }

    public void setVipDays(Integer vipDays) {
        this.vipDays = vipDays;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public Date getDefrostingTime() {
        return defrostingTime;
    }

    public void setDefrostingTime(Date defrostingTime) {
        this.defrostingTime = defrostingTime;
    }
}