package com.aperise.mapper;

public interface UserMapper extends com.aperise.mapper.gen.UserMapper {
    String selectUserNameById(int id);
}