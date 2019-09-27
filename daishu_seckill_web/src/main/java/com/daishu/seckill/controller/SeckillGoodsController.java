package com.daishu.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.daishu.pojo.TbSeckillGoods;
import com.daishu.service.SeckillGoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/20  16:30
 */
@RestController
@RequestMapping("/seckillGoods")
public class SeckillGoodsController {

    @Reference
    private SeckillGoodsService seckillGoodsService;

    @RequestMapping("/findList")
    public List<TbSeckillGoods> findList() {
        List<TbSeckillGoods> list = seckillGoodsService.findList();
        System.out.println("测试");
        System.out.println(list);
        return list;
    }

    @RequestMapping("/findOneFromRedis")
    public TbSeckillGoods findOneFromRedis(Long id) {
        System.out.println("秒杀查询");
        return seckillGoodsService.findOneFromRedis(id);
    }
}
