package com.aperise;

import java.util.List;

import com.aperise.bean.User;
import com.aperise.bean.UserExample;
import com.aperise.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/user/info")
    public Result userinfo(@RequestParam(value = "id", defaultValue = "1") int id) {
        System.out.println("id=" + id);
        User user = userMapper.selectByPrimaryKey(id);
        return Result.OK(user);
    }

    @RequestMapping("/user/list")
    public Result userList(@RequestParam(value = "offset", defaultValue = "0") int offset) throws Exception {
        System.out.println("offset=" + offset);
        UserExample example = new UserExample();
        List<User> users = userMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, 20));
        return Result.OK(users);
    }

}
