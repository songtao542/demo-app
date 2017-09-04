package com.aperise;

import com.aperise.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("view")
public class UserViewController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/user")
    public String user(@RequestParam(value="id", required=false, defaultValue="1") int id, Model model) {
        model.addAttribute("user", userMapper.selectByPrimaryKey(id));
        return "user";
    }

    @RequestMapping("/greeting")
    public String greeting1(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

}
