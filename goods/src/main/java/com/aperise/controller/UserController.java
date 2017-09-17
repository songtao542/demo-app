package com.aperise.controller;

import com.aperise.Result;
import com.aperise.Result.Status;
import com.aperise.bean.AclResource;
import com.aperise.bean.User;
import com.aperise.bean.UserCriteria;
import com.aperise.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.tomcat.util.http.parser.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.DispatcherServlet;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    MutableAclService aclService;

    @Autowired
    UserMapper userMapper;

    @Transactional
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

        logger.debug("/user/add insert id=" + user.getId());

        Authentication oriAuth = SecurityContextHolder.getContext().getAuthentication();

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(String.valueOf(user.getId()), user.getPassword(), authorities);

        try {
            SecurityContextHolder.getContext().setAuthentication(auth);

            ObjectIdentity oid = new ObjectIdentityImpl(User.class, user.getId());
            MutableAcl acl = aclService.createAcl(oid);
            PrincipalSid owner = new PrincipalSid(String.valueOf(user.getId()));
//            acl.setOwner(owner);
            acl.insertAce(0, BasePermission.ADMINISTRATION,
                    owner, true);
            acl.insertAce(1, BasePermission.DELETE,
                    new GrantedAuthoritySid("ROLE_ADMIN"), true);
            acl.insertAce(2, BasePermission.READ,
                    new GrantedAuthoritySid("ROLE_USER"), true);
            aclService.updateAcl(acl);
        } finally {
            SecurityContextHolder.getContext().setAuthentication(oriAuth);
        }
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

    @Secured("ACL_USER_DELETE")
    @RequestMapping("/user/delete")
    public Result deleteUser(Integer id) {
        logger.debug("/user/delete id=" + id);
        if (null == id) {
            return Result.ERROR(Status.PARAMETER_MISSING, "id can't be empty!");
        }
        userMapper.deleteByPrimaryKey(id);
        return Result.OK("delete success");
    }

    @Secured("ACL_READ_RESOURCE")
    @RequestMapping("/user/info")
    public Result userInfo(Integer id) {
        if (null == id) {
            return Result.ERROR(Status.PARAMETER_MISSING, "id can't be empty!");
        }
        User user = userMapper.selectByPrimaryKey(id);
        logger.debug("RequestContextHolder.getRequestAttributes()=" + RequestContextHolder.getRequestAttributes());
        DispatcherServlet a;
        return Result.OK(user);
    }

    @RequestMapping("/user/name")
    public Result userName(@RequestParam(value = "id", defaultValue = "1") Integer id) {
        String name = userMapper.selectNameById(id);
        logger.debug("name=" + name);
        return Result.OK(name);
    }

    @RequestMapping("/user/list")
    public Result userList(@RequestParam(value = "offset", defaultValue = "0") int offset) throws Exception {
        System.out.println("offset=" + offset);
        UserCriteria criteria = new UserCriteria();
        List<User> users = userMapper.selectByCriteriaWithRowbounds(criteria, new RowBounds(offset, 20));
        return Result.OK(users);
    }

}
