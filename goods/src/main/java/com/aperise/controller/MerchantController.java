package com.aperise.controller;

import com.aperise.Result;
import com.aperise.bean.Category;
import com.aperise.bean.Merchant;
import com.aperise.mapper.MerchantMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class MerchantController {

    @Autowired
    MerchantMapper merchantMapper;

    @Transactional
    @RequestMapping("/merchant/add")
    public Result addMerchant(String name, String displayName) {

        if (StringUtils.isEmpty(name)) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "name can't be empty!");
        }
        if (StringUtils.isEmpty(displayName)) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "displayName can't be empty!");
        }

        Merchant merchant = new Merchant();
        merchant.setName(name);
        merchant.setDisplayName(displayName);

        merchant.setGmtCreate(new Date());
        merchant.setGmtModify(new Date());
        merchant.setIsDeleted("n");

        merchantMapper.insertSelective(merchant);

        return Result.OK(merchant);
    }
}
