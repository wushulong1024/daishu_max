package com.daishu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.daishu.pojo.TbBrand;
import com.daishu.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/19  10:04
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }
}
