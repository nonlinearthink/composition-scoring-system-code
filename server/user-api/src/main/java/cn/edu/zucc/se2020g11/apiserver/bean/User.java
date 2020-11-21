package cn.edu.zucc.se2020g11.apiserver.bean;

public class User {
    private String userName;

    private String password;

    private String nickName;

    private String signature;

    private String avatarUrl;

    private String phone;

    private String email;

    private Boolean isMale;

    private Integer vipDays;

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getSignature()
    {
        return signature;
    }

    public void setSignature(String signature)
    {
        this.signature = signature;
    }

    public String getAvatarUrl()
    {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Boolean getMale()
    {
        return isMale;
    }

    public void setMale(Boolean male)
    {
        isMale = male;
    }

    public Integer getVipDays()
    {
        return vipDays;
    }

    public void setVipDays(Integer vipDays)
    {
        this.vipDays = vipDays;
    }
}