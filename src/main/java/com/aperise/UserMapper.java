package com.aperise;

import com.aperise.bean.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectUser(int id);

}
