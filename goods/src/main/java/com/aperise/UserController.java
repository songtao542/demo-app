package com.aperise;

import com.aperise.Result.Status;
import com.aperise.bean.User;
import com.aperise.bean.UserCriteria;
import com.aperise.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/user/add")
    public Result addUser(String email, String phone, String password, String name, String nickname, Long birthday, String sex, Float height, Float weight) {
        logger.debug("/user/add name=" + name + " email=" + email + " password=" + password);
        if (StringUtils.isEmpty(name)) {
            return Result.ERROR(Status.PARAMETER_MISSING, "name can't be empty!");
        }
        if (StringUtils.isEmpty(email)) {
            return Result.ERROR(Status.PARAMETER_MISSING, "email can't be empty!");
        }
        if (StringUtils.isEmpty(password)) {
            return Result.ERROR(Status.PARAMETER_MISSING, "password can't be empty!");
        }
        if (!"M".equals(sex) && "W".equals(sex)) {
            return Result.ERROR(Status.PARAMETER_ERROR, "sex must be M or W");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setNickname(nickname);
        if (birthday != null) {
            user.setBirthday(new Date(birthday));
        }
        user.setSex(sex);
        user.setHeight(height);
        user.setWeight(weight);
        userMapper.insertSelective(user);
        return Result.OK(user);
    }

    @RequestMapping("/user/update")
    public Result updateUser(int id, String email, String phone, String password, String name, String nickname, Long birthday, String sex, Float height, Float weight) {
        logger.debug("/user/update name=" + name + " email=" + email + " password=" + password);
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setNickname(nickname);
        if (birthday != null) {
            user.setBirthday(new Date(birthday));
        }
        user.setSex(sex);
        user.setHeight(height);
        user.setWeight(weight);
        userMapper.updateByPrimaryKeySelective(user);
        return Result.OK(user);
    }

    @RequestMapping("/user/info")
    public Result userInfo(Integer id) {
        if (null == id) {
            return Result.ERROR(Status.PARAMETER_MISSING, "id can't be empty!");
        }
        User user = userMapper.selectByPrimaryKey(id);
        return Result.OK(user);
    }

    @RequestMapping("/user/name")
    public Result userName(@RequestParam(value = "id", defaultValue = "1") Integer id) {
        String name = userMapper.selectUserNameById(id);
        logger.debug("name=" + name);
        return Result.OK(name);
    }

    @RequestMapping("/user/list")
    public Result userList(@RequestParam(value = "offset", defaultValue = "0") int offset) throws Exception {
        System.out.println("offset=" + offset);
        UserCriteria example = new UserCriteria();
        List<User> users = userMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, 20));
        return Result.OK(users);
    }

}
