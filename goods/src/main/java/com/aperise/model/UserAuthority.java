package com.aperise.model;

import com.aperise.bean.AclResource;

import java.util.List;

public class UserAuthority {
    private Integer id;

    private String name;

    private String nickname;

    private List<Access> accesses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Access> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<Access> accesses) {
        this.accesses = accesses;
    }

    public static class Access {
        private Long id;
        private String resource;
        private String display;
        private boolean isGranted;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public boolean isGranted() {
            return isGranted;
        }

        public void setGranted(boolean granted) {
            isGranted = granted;
        }

        public static Access from(AclResource resource, boolean isGranted) {
            Access access = new Access();
            access.id = resource.getId();
            access.display = resource.getDisplay();
            access.resource = resource.getResource();
            access.isGranted = isGranted;
            return access;
        }
    }
}
