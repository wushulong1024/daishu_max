package com.daishu.utlis;

import com.alibaba.fastjson.JSON;
import com.daishu.mapper.TbItemMapper;
import com.daishu.pojo.TbItem;
import com.daishu.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/19  11:49
 */
@Component
public class SolrUtlis {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private SolrTemplate solrTemplate;

    public void improtItemData() {
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");

        List<TbItem> itemList = tbItemMapper.selectByExample(example);

        System.out.println("商品列表");
        for (TbItem tbItem : itemList) {

            System.out.println(tbItem.getId() + " " + tbItem.getTitle() + " " + tbItem.getPrice());
            Map    specMap  = JSON.parseObject(tbItem.getSpec(),Map.class);
            tbItem.setSpecMap(specMap);

        }

        solrTemplate.saveBeans("collection1", itemList);
        solrTemplate.commit("collection1");
        System.out.println("结束");
    }


}
