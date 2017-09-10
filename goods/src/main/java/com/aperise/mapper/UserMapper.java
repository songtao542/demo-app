package com.aperise.mapper;

import com.aperise.bean.User;

public interface UserMapper extends com.aperise.mapper.gen.GenUserMapper {
    String selectNameById(int id);
    User selectByName(String name);
}