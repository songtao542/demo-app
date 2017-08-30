package com.aperise;

import com.aperise.bean.User;
import com.aperise.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class UserViewController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    UserDao userDao;

    @RequestMapping("/userview")
    public String greeting1(@RequestParam(value="id", required=false, defaultValue="1") int id, Model model) {
        model.addAttribute("user", userDao.getUser(id));
        return "user";
    }

    @RequestMapping("/user/greeting")
    public String greeting1(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

}
