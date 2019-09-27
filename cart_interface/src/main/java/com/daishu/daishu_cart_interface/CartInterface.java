package com.daishu.daishu_cart_interface;

import com.daishu.entity_cart.Cart;

import java.util.List;

public interface CartInterface {
    //添加商品到购物车
    List<Cart> addGoodsToCartList(List<Cart> list,Long id,Integer num);
    //从redis中提取购物车
    List<Cart> findCartListFromRedis(String username);
    //将购物车列表存入redis
    void saveCartListToRedis(String username,List<Cart> cartList);
    //合并购物
    List<Cart> mergeCartList(List<Cart> cartList1,List<Cart> cartList2);
    //从redis购物车中查找商品
    List<Cart> findGoodById(Long[] ids,String username);
}
