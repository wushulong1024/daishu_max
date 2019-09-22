package com.daishu.sellergoods.service;

import com.daishu.pojo.TbBrand;
import page.PageResult;

import java.util.List;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/19  9:50
 */
public interface BrandService {

    /**
     * 查询所有商品接口
     * @return
     */
    List<TbBrand> findAll();

    /**
     * 品牌分页
     * pageNum  当前页数
     * pageSize  每页记录数
     */
    public PageResult findPage(int pageNum, int pageSize);
}
