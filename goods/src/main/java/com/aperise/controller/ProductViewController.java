package com.aperise.controller;

import com.aperise.bean.Category;
import com.aperise.bean.CategoryCriteria;
import com.aperise.bean.Merchant;
import com.aperise.bean.MerchantCriteria;
import com.aperise.mapper.CategoryMapper;
import com.aperise.mapper.MerchantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProductViewController {
    protected static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    MerchantMapper merchantMapper;

    @RequestMapping("/view/product/category/add")
    public String addCategory() {
        return "add_category";
    }

    @RequestMapping("/view/product/add")
    public String addProduct(Model model) {
        CategoryCriteria categoryCriteria = new CategoryCriteria();
        List<Category> categories = categoryMapper.selectByCriteria(categoryCriteria);

        MerchantCriteria merchantCriteria = new MerchantCriteria();
        List<Merchant> merchants = merchantMapper.selectByCriteria(merchantCriteria);
        if (logger.isDebugEnabled()) {
            logger.debug("categories=" + categories);
            logger.debug("merchants=" + merchants);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("merchants", merchants);

        return "add_product";
    }
}
