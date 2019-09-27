package com.daishu.cart_service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.daishu.daishu_cart_interface.CartInterface;
import com.daishu.entity_cart.Cart;
import com.daishu.mapper.TbItemMapper;
import com.daishu.pojo.TbItem;
import com.daishu.pojo.TbOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service(version = "0.0.1-SNAPSHOT",timeout = 6000)
@EnableDubbo
public class CartInterfacrImpl implements CartInterface {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Override
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long id, Integer num) {
        //1.根据数据库ID查询商品明细数据库的对象
        TbItem item = itemMapper.selectByPrimaryKey(id);
        if (item==null){
            throw new RuntimeException("该商品不存在");
        }
        if (!item.getStatus().equals("1")){
            throw  new RuntimeException("该商品状态不合法");
        }
        //2、根据数据库的对象得到商家的ID
        String sellerId = item.getSellerId();
        //3、根据商家的ID在购物车列表中查询购物车对象
        Cart cart = searchCartBySellerId(cartList, sellerId);
        //4、如果购物小列表中不存在该商家的购物车
        if(cart==null){
            //4.1创建一个新的购物车对象
            cart=new Cart();
            cart.setSellerId(sellerId);
            cart.setSellerName(item.getSeller());
            //创建订单明细
            List<TbOrderItem> orderItemlist = new ArrayList<TbOrderItem>();
            TbOrderItem orderItem = createOrderItem(item, num);
            //添加到订单明细
            orderItemlist.add(orderItem);
            //将订单明细添加到购物车对象中
            cart.setOrderItemlist(orderItemlist);
            //4.2将新的购物车添加到购物车列表中
            cartList.add(cart);
        }else {//5.如果购物车列表中存在该购物车
            //判断该商品是否在该购物车的明细列表中存在,如果存在返回商品信息，不存在返回null
            TbOrderItem orderItem = searchOrderItemByItemId(cart.getOrderItemlist(), item.getId());
            //5.1如果不存在，创建新的购物车明细对象，并添加到该购物车的明细列表中
            if (orderItem == null) {
                orderItem = createOrderItem(item, num);
                cart.getOrderItemlist().add(orderItem);
            } else {
                //5.2如果存在，在原有的数量上添加数量，并且更新金额
                orderItem.setNum(orderItem.getNum()+num);
                orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue()*orderItem.getNum().doubleValue()));
                //当明细的数量小于等于0，移除此明细
                if (orderItem.getNum()<=0){
                    cart.getOrderItemlist().remove(orderItem);
                }
                if (cart.getOrderItemlist().size()==0) {
                    cartList.remove(cart);
                }
            }
        }
        return cartList;
    }
    /*
        从redis中通过用户名查找购物车
     */
    @Override
    public List<Cart> findCartListFromRedis(String username) {
        List<Cart> cartlist = (List<Cart>) redisTemplate.boundHashOps("cartList").get(username);
        if (cartlist==null){
            cartlist= new ArrayList<>();
        }
        return cartlist;
    }

    /**
     * 保存购物车到redis
     * @param username
     * @param cartList
     */
    @Override
    public void saveCartListToRedis(String username, List<Cart> cartList) {
        redisTemplate.boundHashOps("cartList").put(username,cartList);
        System.out.println(username+"购物车保存成功");
    }

    /**
     * 合并购物车
     * @param cartList1
     * @param cartList2
     * @return
     */
    @Override
    public List<Cart> mergeCartList(List<Cart> cartList1, List<Cart> cartList2) {
        System.out.println("正在合并");
        for (Cart cart:cartList1) {
            for (TbOrderItem orderItem:cart.getOrderItemlist()) {
                 cartList1 = addGoodsToCartList(cartList2, orderItem.getItemId(), orderItem.getNum());
            }
        }
        return cartList1;
    }
    /**
     * 查找cart对象
     */
    @Override
    public List<Cart> findGoodById(Long[] ids,String username) {
        System.out.println(ids);
        List<Cart> orderList=new ArrayList<>();
        List<Cart> cartList = findCartListFromRedis(username);
        for (int i = 0; i <ids.length; i++) {
            Cart cart = findCartById(cartList, ids[i]);
            if (cart!=null){
                orderList.add(cart);
            }
        }
        return orderList;
    }


    public Cart findCartById(List<Cart> cartList,Long id){
        for (Cart cart:cartList) {
            for (TbOrderItem orderItem:cart.getOrderItemlist()){
                if (orderItem.getItemId().equals(id))
                    return cart;
            }

        }
        return null;
    }
    /**
     * 在购物车中根据商家ID查找对应的购物车明细对象
     * @param list
     * @param sellerId
     * @return
     */
    private Cart searchCartBySellerId(List<Cart> list,String sellerId){
        for (Cart cartlist: list) {
            if (cartlist.getSellerId().equals(sellerId)){
                return cartlist;
            }
        }
        return null;
    }
    /**
     * 创建订单明细
     */
    private TbOrderItem createOrderItem(TbItem item,Integer num){
        TbOrderItem orderItem=new TbOrderItem();
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setItemId(item.getId());
        orderItem.setNum(num);
        orderItem.setPicPath(item.getImage());
        orderItem.setPrice(item.getPrice());
        orderItem.setTitle(item.getTitle());
        orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue()*item.getNum().doubleValue()));
        return orderItem;
    }
    /**
     * 根据skuID在购物车明细列表中查询购物车明细对象
     * @param orderItemList
     * @param itemId
     * @return
     */
    public TbOrderItem searchOrderItemByItemId(List<TbOrderItem> orderItemList,Long itemId){
        for(TbOrderItem orderItem:orderItemList){
            if(orderItem.getItemId().longValue()==itemId.longValue()){
                return orderItem;
            }
        }
        return null;
    }

}
