package com.daishu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.daishu.mapper.TbBrandMapper;
import com.daishu.pojo.TbBrand;
import com.daishu.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/19  10:00
 */

@Service(version = "1.0.0",timeout = 10000)
@Component
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper brandMapper;

    @Override
    public List<TbBrand> findAll() {
        return brandMapper.selectByExample(null);
    }
}
