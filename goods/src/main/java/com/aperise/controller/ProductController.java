package com.aperise.controller;

import com.aperise.Result;
import com.aperise.bean.Category;
import com.aperise.bean.Product;
import com.aperise.bean.User;
import com.aperise.mapper.CategoryMapper;
import com.aperise.mapper.ProductMapper;
import com.aperise.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ProductController {
    protected static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    MutableAclService aclService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;


    @Transactional
    @Secured("ROLE_ADMIN")
    @RequestMapping("/product/category/add")
    public Result addCategory(String name, String displayName) {
        if (StringUtils.isEmpty(name)) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "name can't be empty!");
        }
        if (StringUtils.isEmpty(displayName)) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "displayName can't be empty!");
        }

        Category category = new Category();
        category.setName(name);
        category.setDisplayName(displayName);

        category.setGmtCreate(new Date());
        category.setGmtModify(new Date());
        category.setIsDeleted("n");

        categoryMapper.insertSelective(category);

        return Result.OK(category);
    }

    @Transactional
    @RequestMapping("/product/add")
    public Result addProduct(Integer categoryId, Integer merchantId, String name, String displayName, String description) {
        if (null == categoryId) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "categoryId can't be empty!");
        }
        if (null == merchantId) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "merchantId can't be empty!");
        }
        if (StringUtils.isEmpty(name)) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "name can't be empty!");
        }
        if (StringUtils.isEmpty(displayName)) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "displayName can't be empty!");
        }
        if (StringUtils.isEmpty(description)) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "description can't be empty!");
        }

        Product product = new Product();
        product.setCategoryId(categoryId);
        product.setMerchantId(merchantId);
        product.setName(name);
        product.setDisplayName(displayName);
        product.setDescription(description);

        product.setGmtCreate(new Date());
        product.setGmtModify(new Date());
        product.setIsDeleted("n");

        productMapper.insertSelective(product);

        Authentication oriAuth = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("/product/add auth=" + oriAuth);

        ObjectIdentity oid = new ObjectIdentityImpl(Product.class, product.getId());
        MutableAcl acl = aclService.createAcl(oid);
        acl.insertAce(0, BasePermission.ADMINISTRATION,
                new PrincipalSid(oriAuth), true);
        acl.insertAce(1, BasePermission.DELETE,
                new GrantedAuthoritySid("ROLE_ADMIN"), true);
        acl.insertAce(2, BasePermission.READ,
                new GrantedAuthoritySid("ROLE_USER"), true);
        aclService.updateAcl(acl);
        return Result.OK(product);
    }

    @Secured({"ACL_DELETE_PRODUCT"})
    @Transactional
    @RequestMapping("/product/delete")
    public Result deleteProduce(Integer id) {
        if (null == id) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "id can't be empty!");
        }

        logger.debug("----------------delete the product id=" + id + "----------------");
        Product product = productMapper.selectByPrimaryKey(id);
        if (product == null) {
            return Result.ERROR(Result.Status.DATA_NOT_EXIST, "product is not exist");
        } else {
            logger.debug("----------------delete the product----------------");
            logger.debug("product=" + product);
            logger.debug("----------------delete the product----------------");
        }
        return Result.OK("delete success");
    }


    @Secured({"ROLE_ADMIN", "ACL_QUERY_PRODUCT"})
    @RequestMapping("/product/query")
    public Result queryProduct(Integer id) {
        if (null == id) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "id can't be empty!");
        }
        Product product = productMapper.selectByPrimaryKey(id);
        if (product == null) {
            return Result.ERROR(Result.Status.DATA_NOT_EXIST, "product is not exist");
        } else {
            return Result.OK(product);
        }
    }

}
