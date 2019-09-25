package com.daishu.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/lonin")
public class SellerLongin {

    @RequestMapping("/name")
    public Map name(){
        String name=SecurityContextHolder.getContext().getAuthentication().getName();
        Map map=new HashMap<>();
        map.put("loginname",name);
        return map;
    }
}
