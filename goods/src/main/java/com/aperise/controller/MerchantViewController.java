package com.aperise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MerchantViewController {

    @RequestMapping("/view/merchant/add")
    public String addMerchant() {
        return "add_merchant";
    }
}
