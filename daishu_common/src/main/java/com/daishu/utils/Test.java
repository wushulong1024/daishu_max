package com.daishu.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/24  16:29
 */
@Component
@ConfigurationProperties
@PropertySource("classpath:/weixinpay.properties")
public class Test {
}
