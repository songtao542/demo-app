package com.aperise.dao;

import com.aperise.bean.User;
import com.aperise.mapper.UserMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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

}