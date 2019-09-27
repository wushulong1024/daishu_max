package com.daishu.service;

import com.daishu.pojo.TbSeckillOrder;


/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/21  13:45
 */
public interface SeckillOrderService {


    public  void submitOrede(Long seckillId, String userId);

    /**
     * 缓存中提取订单
     * @param userId
     * @return
     */
    public TbSeckillOrder searchOrderFromRedisUserId(String userId);
}
