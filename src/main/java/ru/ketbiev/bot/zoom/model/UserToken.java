package ru.ketbiev.bot.zoom.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class UserToken implements Serializable {

    private String accessToken;
    private String refreshToken;
    private Date createDate;

    public UserToken(String accessToken, String refreshToken, Date createDate) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.createDate = createDate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Time createDate) {
        this.createDate = createDate;
    }
}
