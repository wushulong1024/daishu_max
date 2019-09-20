package com.daishu.entity_cart;

import com.daishu.pojo.TbOrderItem;


import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {
    //商家id
    private String sellerId;
    //商家名称
    private  String sellerName;
    //购物车
    private List<TbOrderItem> orderItemlist;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public List<TbOrderItem> getOrderItemlist() {
        return orderItemlist;
    }

    public void setOrderItemlist(List<TbOrderItem> orderItemlist) {
        this.orderItemlist = orderItemlist;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "sellerId='" + sellerId + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", orderItemlist=" + orderItemlist +
                '}';
    }
}
