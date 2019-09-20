package com.daishu.cart_web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.daishu.daishu_cart_interface.CartInterface;
import com.daishu.entity_cart.Cart;
import com.daishu.entity_cart.Result;
import com.daishu.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("111111111111111");
        String cookieValue = CookieUtil.getCookieValue(request, "cartList","utf-8");
        if (cookieValue==null||cookieValue.equals("")){
            cookieValue="[]";
        }
        List<Cart> cartList= JSON.parseArray(cookieValue,Cart.class);

        return cartList;
    }
    @RequestMapping("/addGoodsToCartList")
    public Result addGoodsToCartList(Long itemId,Integer num){
        try {
            //提取购物车
            List<Cart> cartList = findCartList();
            //调用服务方法操作购物车
            cartList= CartInterface.addGoodsToCartList(cartList, itemId, num);
            //将新的购物车存入cookie
            String jsonString = JSON.toJSONString(cartList);
            CookieUtil.setCookie(request,response,"cartList",jsonString,3600*24,"utf-8");
            return new Result(true,"存入购物车成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"存入购物车失败");
        }
    }

}
