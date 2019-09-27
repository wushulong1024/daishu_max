package com.daishu.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.daishu.pay.service.WeixinPayService;
import com.daishu.pojo.TbSeckillOrder;
import com.daishu.service.SeckillOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/24  16:03
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference
    private WeixinPayService weixinPayService;

    @Reference
    private SeckillOrderService seckillOrderService;


    @RequestMapping("/createNative")
    public Map createNative(){
        System.out.println("进入支付wwwwwwwwwwwwwwwwwwwwww");
       TbSeckillOrder seckillOrder= seckillOrderService.searchOrderFromRedisUserId("tiger");
        if(seckillOrder!=null){
            System.out.println("秒杀支付成功");
            return  weixinPayService.createNative(seckillOrder.getId()+"",(long)(seckillOrder.getMoney().doubleValue()*100)+"");
        }else{
            return  new HashMap();
        }

    }

}
