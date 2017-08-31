package com.aperise.dao;

import com.aperise.bean.User;
import com.aperise.mapper.UserMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.HashMap;

@Repository
public class UserDao {
//public class UserDao extends SqlSessionDaoSupport {

//    @Autowired
//    @Override
//    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
//        super.setSqlSessionTemplate(sqlSessionTemplate);
//    }
//
//    public User getUser(int id) {
//        return getSqlSession().selectOne("com.aperise.mapper.user.selectUser", id);
//    }

    @Autowired
    UserMapper userMapper;

    public User getUser(int id) {
        return userMapper.selectUser(id);
    }

    public List<User> getAllUser(int offset, int size) {
        return userMapper.selectAllUser(offset, size);
    }

    public String getUserNameById(int id) {
        return userMapper.selectUserNameById(id);
    }

    public List<HashMap<String, Object>> getAllUserWithGame(int id, int offset, int size) {
        return userMapper.selectAllUserWithGame(id, offset, size);
    }

    public User getUserWithGames(int id, int offset, int size) {
        return userMapper.selectUserWithGames(id, offset, size);
    }

    public List<User> getAllUserWithGames(int offset, int size) {
        return userMapper.selectAllUserWithGames(offset, size);
    }

}
