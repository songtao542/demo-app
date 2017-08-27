package com.aperise.bean;

public class User {
    int id;
    String name;
    String nickname;
    String sex;
    float weight;
    long gmt_create;
    long gmt_modify;
    String is_deleted;

    public User(int id, String name, String nickname, String sex, float weight, long gmt_create, long gmt_modify, String is_deleted) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.sex = sex;
        this.weight = weight;
        this.gmt_create = gmt_create;
        this.gmt_modify = gmt_modify;
        this.is_deleted = is_deleted;
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

    public long getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(long gmt_create) {
        this.gmt_create = gmt_create;
    }

    public long getGmt_modify() {
        return gmt_modify;
    }

    public void setGmt_modify(long gmt_modify) {
        this.gmt_modify = gmt_modify;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }
}
