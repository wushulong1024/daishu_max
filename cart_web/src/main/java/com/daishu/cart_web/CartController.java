package com.daishu.cart_web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.daishu.daishu_cart_interface.CartInterface;
import com.daishu.entity_cart.Cart;
import com.daishu.entity_cart.Result;
import com.daishu.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Reference(version = "0.0.1-SNAPSHOT")
    private CartInterface CartInterface;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @RequestMapping("/findCartList")

    public List<Cart> findCartList() {
        //获取登入人账号信息
//        String username="zhangsan";
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        //获取cookie中购物车信息
        String cookieValue = CookieUtil.getCookieValue(request, "cartList","utf-8");
        if (cookieValue==null||cookieValue.equals("")){
            cookieValue="[]";
        }
        List<Cart> cartList_cookie= JSON.parseArray(cookieValue,Cart.class);
        if (username.equals("anonymousUser")){//未登录
            System.out.println("从cookie中获取");
            return cartList_cookie;
        }else {
            List<Cart> cartList_redis =CartInterface.findCartListFromRedis(username);//从redis中提取
            if(cartList_cookie.size()>0){//如果本地存在购物车
                //合并购物车
                cartList_redis=CartInterface.mergeCartList(cartList_cookie,cartList_redis);
                //清除本地cookie的数据
                CookieUtil.deleteCookie(request, response, "cartList");
                //将合并后的数据存入redis
                CartInterface.saveCartListToRedis(username, cartList_redis);
            }
            cartList_redis =CartInterface.findCartListFromRedis(username);
            return cartList_redis;
        }

    }
    @RequestMapping("/addGoodsToCartList")
    public Result addGoodsToCartList(Long itemId,Integer num){
        response.setHeader("Access-Control-Allow-Origin","http://192.168.90.127:9090");
        response.addHeader("Access-Control-Allow-Credentials", "true");
//        String username="zhangsan";
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            //提取购物车
            List<Cart> cartList = findCartList();
            //调用服务方法操作购物车
            cartList= CartInterface.addGoodsToCartList(cartList, itemId, num);
            if (username.equals("anonymousUser")){
                //将新的购物车存入cookie
                String jsonString = JSON.toJSONString(cartList);
                CookieUtil.setCookie(request,response,"cartList",jsonString,3600*24,"utf-8");
            }else {
                CartInterface.saveCartListToRedis(username,cartList);
            }
            return new Result(true,"存入购物车成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"存入购物车失败");
        }
    }



}
