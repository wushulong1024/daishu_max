package com.daishu.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.daishu.seckill.entity.Result;
import com.daishu.service.SeckillOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/21  14:22
 */
@RestController
@RequestMapping("/seckillOrder")
public class SeckillOrderController {

    @Reference
    private SeckillOrderService seckillOrderService;

    @RequestMapping("/submitOrder")
    public Result submitOrder(Long seckillId) {
        System.out.println("执行下单方法");
        //提取当前用户
        try {
          /*  String username = "Tiger";
            if ("Tiger".equals(username)) {
                return new Result(false, "当前用户未登陆");
            }*/

            seckillOrderService.submitOrede(seckillId, "tiger");//用户暂时写死


            return new Result(true, "提交订单成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "提交订单失败");
        }


    }
}
