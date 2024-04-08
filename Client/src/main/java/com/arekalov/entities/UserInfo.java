package com.arekalov.entities;

import java.io.Serializable;
import java.util.Objects;

public class UserInfo implements Serializable {
    private String login;
    private String password;
    private AuthMode authMode;

    public UserInfo(String login, String password, AuthMode authMode) {
        this.login = login;
        this.password = password;
        this.authMode = authMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(login, userInfo.login) && Objects.equals(password, userInfo.password) && authMode == userInfo.authMode;
    }

    public AuthMode getAuthMode() {
        return authMode;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthMode(AuthMode authMode) {
        this.authMode = authMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, authMode);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", authMode=" + authMode +
                '}';
    }
}
