package com.marcs.app.auth.client.domain;

import java.util.Date;

import com.marcs.app.user.client.domain.User;

/**
 * Authentication token to be used within the app.
 *
 * @author Sam Butler
 * @since July 31, 2021
 */
public class AuthToken {

    private String token;

    private Date createDate;

    private Date expireDate;

    private User user;

    public AuthToken(String t, Date creation, Date expire, User u) {
        token = t;
        expireDate = expire;
        createDate = creation;
        user = u;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
