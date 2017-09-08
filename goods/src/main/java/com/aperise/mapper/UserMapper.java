package com.aperise.mapper;

public interface UserMapper extends com.aperise.mapper.gen.GenUserMapper {
    String selectUserNameById(int id);
}