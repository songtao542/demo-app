package com.aperise.mapper;

import com.aperise.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

public interface UserMapper {
//      @Select("SELECT * FROM user WHERE id = #{id}")
    public User selectUser(int id);

    public User selectAllUser();

}
