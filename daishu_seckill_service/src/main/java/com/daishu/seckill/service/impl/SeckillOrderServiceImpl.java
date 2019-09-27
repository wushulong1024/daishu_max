package com.daishu.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.daishu.config.IdWorker;
import com.daishu.pojo.TbSeckillGoods;
import com.daishu.pojo.TbSeckillOrder;
import com.daishu.seckill.mapper.TbSeckillGoodsMapper;
import com.daishu.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/21  13:56
 */
@Service
@Component
public class  SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbSeckillGoodsMapper tbSeckillGoodsMapper;

    @Autowired
    private IdWorker idWorker;
    @Override
    public void submitOrede(Long seckillId, String userId) {

        //查询缓存中的商品

        TbSeckillGoods seckillGoods= (TbSeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(seckillId);
        if(seckillGoods==null){
            throw  new RuntimeException("商品不存在");
        }
        if(seckillGoods.getStockCount()<=0){
            throw  new RuntimeException("商品已被抢光");
        }

        //减少库存
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
        //减完之后存入库存
        redisTemplate.boundHashOps("seckillGoods").put(seckillId,seckillGoods);
        if(seckillGoods.getStockCount()==0){
            tbSeckillGoodsMapper.updateByPrimaryKey(seckillGoods);//更新数据库
            redisTemplate.boundHashOps("seckillGoods").delete(seckillId);
            System.out.println("将商品同步到数据库");
        }

        //存储秒杀订单(不像数据库存，只像缓存中存)
        TbSeckillOrder seckillOrder=new TbSeckillOrder();
        seckillOrder.setId(idWorker.nextId());
        seckillOrder.setSeckillId(seckillId);
        seckillOrder.setMoney(seckillGoods.getCostPrice());
        seckillOrder.setUserId(userId);
        seckillOrder.setSellerId(seckillGoods.getSellerId());
        seckillOrder.setCreateTime(new Date());
        seckillOrder.setStatus("0");

        redisTemplate.boundHashOps("seckillOrder").put(userId,seckillOrder);
        System.out.println("保存订单成功(redis)");



    }

    @Override
    public TbSeckillOrder searchOrderFromRedisUserId(String userId) {

        return (TbSeckillOrder) redisTemplate.boundHashOps("seckillOrder").get(userId);
    }
}
