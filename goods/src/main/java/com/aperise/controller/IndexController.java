package com.aperise.controller;

import com.aperise.bean.AclResource;
import com.aperise.bean.AclResourceGroup;
import com.aperise.mapper.AclResourceMapper;
import com.aperise.model.Menu;
import com.aperise.model.MenuImpl;
import com.aperise.model.MenuItem;
import com.aperise.model.MenuItemImpl;
import com.aperise.services.AccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    protected static Logger logger = LoggerFactory.getLogger(UserViewController.class);

    @Autowired
    AclResourceMapper aclResourceMapper;

    @Autowired
    AccessService accessService;

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        logger.debug("UserViewController xxxxxxxxxxxxxxxxxxx/index");
        List<AclResource> resources = aclResourceMapper.selectByType("url");

        Map<String, Menu> tmp = new HashMap<>();
        List<Menu> menus = new ArrayList<>();
        for (AclResource resource : resources) {
            if (logger.isDebugEnabled())
                logger.debug("index resource:" + resource);
            if (accessService.checkGranted(resource)) {
                if (resource.getGroupId() == null) {
                    Menu menu = new MenuImpl(resource.getId(), resource.getDisplay(), resource.getResource());
                    menus.add(menu);
                } else {
                    Menu menu = tmp.get(resource.getGroupName());
                    if (menu == null) {
                        menu = new MenuImpl(resource.getGroupId(), resource.getGroupName(), null);
                        tmp.put(resource.getGroupName(), menu);
                        menus.add(menu);
                    }
                    MenuItem item = new MenuItemImpl(resource.getId(), resource.getDisplay(), resource.getResource());
                    menu.add(item);
                }
            }
        }
        tmp.clear();
        model.addAttribute("menus", menus);
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
