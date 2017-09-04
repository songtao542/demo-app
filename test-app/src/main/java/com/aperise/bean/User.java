package com.aperise.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    int id;
    String name;
    String nickname;
    String sex;
    float weight;
    long gmtCreate;
    long gmtModify;
    String isDeleted;

    List<Game> games;

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String name, String nickname, String sex, float weight, long gmtCreate, long gmtModify, String isDeleted) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.sex = sex;
        this.weight = weight;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
        this.isDeleted = isDeleted;
    }

//    @Override
//    public String toString() {
//        StringBuilder b = new StringBuilder();
//        b.append("id=" + id).append("\n")
//                .append("name=" + name).append("\n")
//                .append("nickname" + nickname).append("\n")
//                .append("sex=" + sex).append("\n")
//                .append("weight" + weight).append("\n")
//                .append("gmtCreate=" + gmtCreate).append("\n")
//                .append("gmtModify=" + gmtModify).append("\n")
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

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(long gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String is_deleted) {
        this.isDeleted = is_deleted;
    }
}
