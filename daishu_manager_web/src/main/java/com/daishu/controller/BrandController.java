package com.daishu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.daishu.pojo.TbBrand;
import com.daishu.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import page.PageResult;

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

    /**
     * 查询全部
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }

    /**
     * 分页
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int size){
        System.out.println(page+"----"+size);
        return brandService.findPage(page,size);
    }
}
