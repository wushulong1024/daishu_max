package com.daishu.pay.service;

import java.util.Map;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/24  16:22
 */
public interface WeixinPayService {

    public Map createNative(String out_trade,String total_fee);
}
