package com.aperise.dao;

import com.aperise.bean.Game;
import com.aperise.bean.User;
import com.aperise.mapper.GameMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameDao {

    @Autowired
    GameMapper gameMapper;

    public Game getGame(int id) {
        return gameMapper.selectGame(id);
    }

    public List<Game> getAllGame(@Param("offset") int offset, @Param("size") int size) {
        return gameMapper.selectAllGame(offset, size);
    }

    public String getNameById(int id) {
        return gameMapper.selectGameNameById(id);
    }

    public Game getGameWithPlayers(@Param("id") int id, @Param("offset") int offset, @Param("size") int size) {
        return gameMapper.selectGameWithPlayers(id, offset, size);
    }

    public List<Game> getAllGameWithPlayers(@Param("offset") int offset, @Param("size") int size) {
        return gameMapper.selectAllGameWithPlayers(offset, size);
    }

}
