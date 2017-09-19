package com.aperise.model;

import com.aperise.bean.User;

import java.util.List;

public class Access {
    private Long id;
    private String resource;
    private String display;
    private List<User> grantedUsers;
    private List<User> ungrantedUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public List<User> getGrantedUsers() {
        return grantedUsers;
    }

    public void setGrantedUsers(List<User> grantedUsers) {
        this.grantedUsers = grantedUsers;
    }

    public List<User> getUngrantedUsers() {
        return ungrantedUsers;
    }

    public void setUngrantedUsers(List<User> ungrantedUsers) {
        this.ungrantedUsers = ungrantedUsers;
    }
}
