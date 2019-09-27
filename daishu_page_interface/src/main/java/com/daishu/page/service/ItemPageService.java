package com.daishu.page.service;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/22  19:06
 */
public interface ItemPageService {

    /**
     * 生成商品详细页
     * @param goodsId
     * @return
     */
    public boolean genItemHtml(Long goodsId);


}
