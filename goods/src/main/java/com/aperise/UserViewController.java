package com.aperise;

import com.aperise.mapper.UserMapper;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserViewController {

    @Autowired
    UserMapper userMapper;
    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/")
    public String index() {
        logger.debug("UserViewController xxxxxxxxxxxxxxxxxxx/index");
        return "sign_in";
//        return "redirect:http://localhost/front-end/index.html";
    }

    @RequestMapping("/login")
    public String login() {
        logger.debug("UserViewController *********************** /login");
        return "sign_in";
    }

    @RequestMapping("/signin")
    public String signin(Model model) {
        logger.debug("UserViewController -----------------------/signin model="+model.asMap());
        return "sign_in";
    }

    @RequestMapping("/signup")
    public String signUp() {
        logger.debug("UserViewController /signup");
        return "sign_up";
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
