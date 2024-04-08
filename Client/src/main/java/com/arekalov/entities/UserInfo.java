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
