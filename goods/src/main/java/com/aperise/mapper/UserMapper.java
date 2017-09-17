package com.aperise.mapper;

import com.aperise.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserMapper extends com.aperise.mapper.gen.GenUserMapper {
    String selectNameById(int id);

    User selectByName(@Param("name") String name);
}