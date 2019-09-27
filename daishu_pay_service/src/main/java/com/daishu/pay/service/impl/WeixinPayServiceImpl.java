package com.daishu.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.daishu.pay.service.WeixinPayService;
import com.daishu.utils.HttpClient;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/24  16:24
 */
@Service
@Component
public class WeixinPayServiceImpl implements WeixinPayService {


    @Override
    public Map createNative(String out_trade_no, String total_fee) {
        Map param = new HashMap();
        param.put("appid", "wx8397f8696b538317");
        param.put("mch_id", "1473426802");
        param.put("noce_str", WXPayUtil.generateNonceStr());
        param.put("body", "袋鼠商城");
        param.put("out_trade_no", out_trade_no);
        param.put("total_fee", total_fee);
        param.put("spbill_create_ip", "192.168.40.127");
        param.put("notify_url", "http://www.itcast.cn");
        param.put("trade_type", "NATIVE");

        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, "8A627A4578ACE384017C997F12D68B23");
            System.out.println("请求的参数：" + xmlParam);
            //发送请求

            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();

            //获取结果
            String xmlResult = httpClient.getContent();
            Map<String, String> mapResult = WXPayUtil.xmlToMap(xmlResult);
            System.out.println("微信返回结果："+mapResult);
            Map map = new HashMap();
            map.put("code_url", mapResult.get("code_url"));//生成支付二维码的链接
            map.put("out_trade_no", out_trade_no);
            map.put("total_fee", total_fee);

            return map;

        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }


    }
}
