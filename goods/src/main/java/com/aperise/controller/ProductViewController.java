package com.aperise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductViewController {

    @RequestMapping("/view/product/category/add")
    public String addCategory() {
        return "add_category";
    }

    @RequestMapping("/view/product/add")
    public String addProduct() {
        return "add_product";
    }
}
