package com.daishu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.daishu.pojo.TbBrand;
import com.daishu.sellergoods.service.BrandService;
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

    @Reference(version = "1.0.0")
    private BrandService brandService;

    @RequestMapping("/tes")
    public List<TbBrand> findAll() {
        System.out.println("啊啊");
        System.out.println("呀呀呀");
        System.out.println("在不好弄死你");
        return brandService.findAll();
    }
}
