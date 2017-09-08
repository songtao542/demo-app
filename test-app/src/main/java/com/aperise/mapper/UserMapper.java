package com.aperise.mapper;

import com.aperise.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.HashMap;

public interface UserMapper {
    //@Select("SELECT * FROM user WHERE id = #{id}")
    public User selectUser(int id);

    public List<User> selectAllUser(@Param("offset") int offset, @Param("size") int size);

    public String selectUserNameById(int id);

    public List<HashMap<String, Object>> selectAllUserWithGame(@Param("id") int id, @Param("offset") int offset, @Param("size") int size);

    public User selectUserWithGames(@Param("id") int id, @Param("offset") int offset, @Param("size") int size);

    @Transactional(readOnly = true)
    public List<User> selectAllUserWithGames(@Param("offset") int offset, @Param("size") int size);

}
