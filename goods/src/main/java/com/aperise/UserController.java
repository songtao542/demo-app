package com.aperise;

import java.util.List;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.aperise.bean.User;
import com.aperise.bean.UserExample;
import com.aperise.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/user/info")
    public User userinfo(@RequestParam(value = "id", defaultValue = "1") int id) {
        System.out.println("id=" + id);
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @RequestMapping("/user/list")
    public List<User> userList(@RequestParam(value = "offset", defaultValue = "0") int offset) throws Exception {
        System.out.println("offset=" + offset);
        UserExample example = new UserExample();
        List<User> users = userMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, 20));
        return users;
    }

}
