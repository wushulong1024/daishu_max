package com.daishu.seckill.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.daishu.pojo.TbSeckillGoods;
import com.daishu.pojo.TbSeckillGoodsExample;
import com.daishu.seckill.mapper.TbSeckillGoodsMapper;
import com.daishu.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Component
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<TbSeckillGoods> findList() {
        List<TbSeckillGoods> tbSeckillGoods = redisTemplate.boundHashOps("seckillGoods").values();

        if (tbSeckillGoods == null || tbSeckillGoods.size() == 0) {
            TbSeckillGoodsExample example = new TbSeckillGoodsExample();
            TbSeckillGoodsExample.Criteria criteria = example.createCriteria();
            criteria.andStatusEqualTo("1");
            criteria.andStockCountGreaterThan(0);
            criteria.andStartTimeLessThanOrEqualTo(new Date());
            criteria.andEndTimeGreaterThanOrEqualTo(new Date());
            tbSeckillGoods = seckillGoodsMapper.selectByExample(example);
            for (TbSeckillGoods tbSeckillGood : tbSeckillGoods) {
                redisTemplate.boundHashOps("seckillGoods").put(tbSeckillGood.getId(), tbSeckillGood);

            }
            System.out.println("从数据库读取数据装入缓存");
        } else {
            System.out.println("缓存读取");
        }

        return tbSeckillGoods;
    }

    @Override
    public TbSeckillGoods findOneFromRedis(Long id) {
        return (TbSeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(id);
    }

    @Override
    public TbSeckillGoods findOne(Long id) {
        return null;
    }


}
