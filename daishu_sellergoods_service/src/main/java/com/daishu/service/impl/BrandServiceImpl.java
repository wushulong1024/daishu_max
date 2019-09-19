package com.daishu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.daishu.mapper.TbBrandMapper;
import com.daishu.pojo.TbBrand;
import com.daishu.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import page.PageResult;

import java.util.List;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/19  10:00
 */

@Service
@Component
public class BrandServiceImpl implements BrandService{

    @Autowired
    private TbBrandMapper brandMapper;

    /**
     * 查询所有商品
     * @return
     */
    @Override
    public List<TbBrand> findAll() {
        return brandMapper.selectByExample(null);
    }

    /**
     * 商品分页
     * @param pageNum  当前页数
     * @param pageSize  每页记录数
     * @return
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {

        return null;
    }
}
