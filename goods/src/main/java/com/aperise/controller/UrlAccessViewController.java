package com.aperise.controller;

import com.aperise.bean.*;
import com.aperise.mapper.AclResourceMapper;
import com.aperise.mapper.UserMapper;
import com.aperise.model.Access;
import com.aperise.services.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UrlAccessViewController {

    @Autowired
    AclResourceMapper aclResourceMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AclService aclService;

    @Autowired
    AccessService accessService;


    @Secured("ROLE_ADMIN")
    @RequestMapping("/view/access/manage")
    public String accessManage(Model model) {
        List<Access> accesses = accessService.getAccesses();
        model.addAttribute("size", accesses.size());
        model.addAttribute("accesses", accesses);
        return "access_manage";
    }

    @RequestMapping("/view/resource/add")
    public String addResource(Model model) {
        List<AclResourceGroup> groups = aclResourceMapper.selectGroup();
        model.addAttribute("groups", groups);
        return "add_resource";
    }

    @RequestMapping("/view/resource/delete")
    public String deleteResource(Model model) {
        List<AclResourceGroup> groups = aclResourceMapper.selectGroup();
        model.addAttribute("groups", groups);
        return "add_resource";
    }
}
