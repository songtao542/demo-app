package com.aperise.controller;

import com.aperise.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserViewController {
    protected static Logger logger = LoggerFactory.getLogger(UserViewController.class);

    @Autowired
    UserMapper userMapper;


    @ModelAttribute
    public void setModelAttr(Model model) {
        model.addAttribute("attr", "test attr");
    }

    @ModelAttribute
    public String setModelAttr1() {
        return "test attr1";
    }

    @ModelAttribute("attr2")
    public String setModelAttr2() {
        return "test attr2";
    }

    @RequestMapping("/view/user/add_success")
    public String addUserSuccess() {
        return "sign_up_success";
    }

    @RequestMapping("/view/user")
    public String user(@RequestParam(value = "id", required = false, defaultValue = "1") int id, Model model) {
        model.addAttribute("user", userMapper.selectByPrimaryKey(id));
        return "user";
    }

    @RequestMapping("/greeting")
    public String greeting1(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

}
