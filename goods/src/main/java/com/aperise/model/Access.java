package com.aperise.model;

import com.aperise.bean.User;

import java.util.List;

public class Access {
    private Long id;
    private String resource;
    private String display;
    private List<Authority> authorities;

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

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public static class Authority {
        private Integer id;

        private String name;

        private String nickname;

        private boolean isGranted;

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

        public boolean isGranted() {
            return isGranted;
        }

        public boolean getIsGranted() {
            return isGranted;
        }

        public void setGranted(boolean granted) {
            isGranted = granted;
        }


        public static Authority from(User user, boolean isGranted) {
            Authority authority = new Authority();
            authority.id = user.getId();
            authority.name = user.getName();
            authority.nickname = user.getNickname();
            authority.isGranted = isGranted;
            return authority;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("id:" + id + ",");
            sb.append("name:" + name + ",");
            sb.append("nickname:" + nickname + ",");
            sb.append("isGranted:" + isGranted);
            sb.append("}");
            return sb.toString();
        }
    }

}

