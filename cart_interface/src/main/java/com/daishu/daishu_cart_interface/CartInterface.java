package com.daishu.daishu_cart_interface;

import com.daishu.entity_cart.Cart;

import java.util.List;

public interface CartInterface {
    List<Cart> addGoodsToCartList(List<Cart> list,Long id,Integer num);
}
