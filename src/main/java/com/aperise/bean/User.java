package com.aperise.bean;

public class User {
    int id;
    String name;
    String nickname;
    String sex;
    float weight;
    long gmtCreate;
    long gmtModify;
    String isDeleted;

    public User(int id) {
        this.id = id;
    }

    public User(int id, String name, String nickname, String sex, float weight, long gmt_create, long gmt_modify, String is_deleted) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.sex = sex;
        this.weight = weight;
        this.gmtCreate = gmt_create;
        this.gmtModify = gmt_modify;
        this.isDeleted = is_deleted;
    }

//    @Override
//    public String toString() {
//        StringBuilder b = new StringBuilder();
//        b.append("id=" + id).append("\n")
//                .append("name=" + name).append("\n")
//                .append("nickname" + nickname).append("\n")
//                .append("sex=" + sex).append("\n")
//                .append("weight" + weight).append("\n")
//                .append("gmt_create=" + gmt_create).append("\n")
//                .append("gmt_modify=" + gmt_modify).append("\n")
//                .append("is_deleted=" + is_deleted);
//        return b.toString();
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmt_create) {
        this.gmtCreate = gmt_create;
    }

    public long getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(long gmt_modify) {
        this.gmtModify = gmt_modify;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String is_deleted) {
        this.isDeleted = is_deleted;
    }
}
