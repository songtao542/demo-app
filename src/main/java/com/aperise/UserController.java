package com.aperise;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

import com.aperise.bean.User;
import com.aperise.dao.UserDao;
import com.google.gson.Gson;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    UserDao userDao;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "id", defaultValue = "1") int id) {
        return new Greeting(counter.incrementAndGet(), "" + id);
    }

    @RequestMapping("/user")
    public User user(@RequestParam(value = "id", defaultValue = "1") int id) {
        System.out.println("id=" + id);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("com.aperise.mapper.user.selectUser", id);
        return user;
    }

    @RequestMapping("/user1")
    public User user1(@RequestParam(value = "id", defaultValue = "1") int id) {
        System.out.println("id=" + id);
        User user = sqlSessionTemplate.selectOne("com.aperise.mapper.user.selectUser", id);
        System.out.println("sqlSessionFactory2==" + sqlSessionFactory);
        System.out.println("sqlSessionTemplate==" + sqlSessionTemplate);
        return user;
    }

    @RequestMapping("/user2")
    public User user2(@RequestParam(value = "id", defaultValue = "1") int id) {
        System.out.println("id=" + id);
        User user = userDao.selectOne(id);
        return user;
    }
}
