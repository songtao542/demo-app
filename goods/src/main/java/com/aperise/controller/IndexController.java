package com.aperise.controller;

import com.aperise.bean.AclResource;
import com.aperise.mapper.AclResourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.acl.Acl;
import java.util.List;

@Controller
public class IndexController {
    protected static Logger logger = LoggerFactory.getLogger(UserViewController.class);

    @Autowired
    AclResourceMapper aclResourceMapper;

    @RequestMapping({"/", "/index"})
    public String index() {
        logger.debug("UserViewController xxxxxxxxxxxxxxxxxxx/index");
        List<AclResource> resources = aclResourceMapper.selectByType("url");

        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        logger.debug("UserViewController *********************** /login");
        return "sign_in";
    }

    @RequestMapping("/signin")
    public String signin(HttpServletRequest request) {
        logger.debug("UserViewController -----------------------/request:" + request.getMethod() + "  -- " + request.getRequestURI() + "  -- " + request.getQueryString());
        return "sign_in";
    }

    @RequestMapping("/signup")
    public String signup() {
        logger.debug("UserViewController /signup");
        return "sign_up";
    }
}
