package com.aperise.mapper;

import com.aperise.bean.Game;
import com.aperise.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.HashMap;

public interface GameMapper {
    //@Select("SELECT * FROM user WHERE id = #{id}")
    public Game selectGame(int id);

    public List<Game> selectAllGame(@Param("offset") int offset, @Param("size") int size);

    public String selectGameNameById(int id);

    public Game selectGameWithPlayers(@Param("id") int id, @Param("offset") int offset, @Param("size") int size);

    public List<Game> selectAllGameWithPlayers(@Param("offset") int offset, @Param("size") int size);

}
