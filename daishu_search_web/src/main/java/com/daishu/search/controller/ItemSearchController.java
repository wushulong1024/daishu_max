package com.daishu.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.daishu.page.service.ItemPageService;
import com.daishu.search.service.ItemSearchService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/19  17:29
 */

@RestController
@RequestMapping("/itemsearch")
public class ItemSearchController {

    @Reference
    private ItemSearchService itemSearchService;


    @RequestMapping("/search")
    public Map seach(@RequestBody Map searchMap){

        return itemSearchService.search(searchMap);
    }

    @Reference(version = "0.0.2")
    private ItemPageService itemPageService;
    @RequestMapping("/genHtml")
    public  void genHtml(Long goodsId){
        itemPageService.genItemHtml(goodsId);

    }


    @GetMapping("item/{id}.html")
    public  String toItemPage(@PathVariable("id") long id, Model model){
        System.out.println(id);
        itemPageService.genItemHtml(id);
        return  "redirect:http://localhost:8084/item/"+id +".html";

    }

}
