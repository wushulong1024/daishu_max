package com.daishu.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.daishu.mapper.TbGoodsDescMapper;
import com.daishu.mapper.TbGoodsMapper;
import com.daishu.mapper.TbItemCatMapper;
import com.daishu.mapper.TbItemMapper;
import com.daishu.page.service.ItemPageService;
import com.daishu.pojo.TbGoods;
import com.daishu.pojo.TbGoodsDesc;
import com.daishu.pojo.TbItem;
import com.daishu.pojo.TbItemExample;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/23  15:53
 */

@Service(version = "0.0.2")
@Component
public class ItemPageServiceImpl implements ItemPageService {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public boolean genItemHtml(Long goodsId) {

        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        try {
            Template template = configuration.getTemplate("item.ftl");

            //创建数据模型
            Map dataModel = new HashMap<>();
            TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goods", tbGoods);
            TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goodsDesc", tbGoodsDesc);

            //读取商品分类
            String itemCat1 = tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id()).getName();
            String itemCat2 = tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id()).getName();
            String itemCat3 = tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName();
            dataModel.put("itemCat1", itemCat1);
            dataModel.put("itemCat2", itemCat2);
            dataModel.put("itemCat3", itemCat3);

            //读取SKU列表数据
            TbItemExample example=new TbItemExample();
            TbItemExample.Criteria criteria= example.createCriteria();
            criteria.andGoodsIdEqualTo(goodsId);//
            criteria.andStatusEqualTo("1");
            example.setOrderByClause("is_default desc");//按是否默认字段是否降序排序，目的是返回的第一条结果为默认的SKU
            List<TbItem> itemList= tbItemMapper.selectByExample(example);
            dataModel.put("itemList",itemList);

            //生成到服务器本地硬盘
            Writer out = new FileWriter("D:\\item\\" + goodsId + ".html");
            template.process(dataModel, out);
            out.close();
            System.out.println("成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
