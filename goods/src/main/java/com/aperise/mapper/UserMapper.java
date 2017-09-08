package com.aperise.mapper;

import com.aperise.bean.User;

public interface UserMapper extends com.aperise.mapper.gen.GenUserMapper {
    String selectUserNameById(int id);
    User selectUserById(int id);
}