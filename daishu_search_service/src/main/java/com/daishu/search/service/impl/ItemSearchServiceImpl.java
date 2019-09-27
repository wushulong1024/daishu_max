package com.daishu.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.daishu.pojo.TbItem;
import com.daishu.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.*;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/19  17:01
 */

@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map search(Map searchMap) {
        Map map = new HashMap();

        //空格处理
        String keywords = (String) searchMap.get("keywords");
        searchMap.put("keywords", keywords.replace(" ", ""));//去掉空格
        //查询列表
        map.putAll(searchList(searchMap));

        //分组查询商品分类列表
        List<String> categroyList = searchCategroyList(searchMap);
        map.put("categoryList", categroyList);

        //查询品牌和规格参数
        if (categroyList.size() > 0) {

            map.putAll(searchBrandAndSpecList(categroyList.get(0)));
        }
        return map;
    }


    //查询列表
    private Map searchList(Map searchMap) {
        Map map = new HashMap();
        //高亮显示HighlightOptions
        HighlightQuery query = new SimpleHighlightQuery();

        HighlightOptions highlightOptions = new HighlightOptions().addField("item_title");//高亮域
        highlightOptions.setSimplePrefix("<em style='color:red'>");//前缀
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);

        //关键字查询
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        //按照商品分类查询
        if (!"".equals(searchMap.get("category"))) {

            FilterQuery filterQuery = new SimpleFilterQuery();
            Criteria filterCriteria = new Criteria("item_category").is(searchMap.get("category"));
            filterQuery.addCriteria(filterCriteria);
            query.addFilterQuery(filterQuery);
        }

        //按价格过滤
        if (!"".equals(searchMap.get("price"))) {
            String[] price = ((String) searchMap.get("price")).split("-");
            if (!price[0].equals("0")) {//如果最低价格不等于0
                FilterQuery filterQuery = new SimpleFilterQuery();
                Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(price[0]);
                filterQuery.addCriteria(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
            if (!price[1].equals("*")) {//如果最搞价格不等于*
                FilterQuery filterQuery = new SimpleFilterQuery();
                Criteria filterCriteria = new Criteria("item_price").lessThanEqual(price[1]);
                filterQuery.addCriteria(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }

        //分页
        Integer pageNo = (Integer) searchMap.get("pageNo");//获取页码
        if (pageNo == null) {
            pageNo = 1;
        }

        Integer pageSize = (Integer) searchMap.get("pageSize");//获取页大小
        if (pageSize == null) {
            pageSize = 20;
        }

        query.setOffset((long) ((pageNo - 1) * pageSize));//起始索引
        query.setRows(pageSize);//每页记录数

        //按价格排序
        String sortValue = (String) searchMap.get("sort");//升序ASC 降序DESC
        String sortFieId = (String) searchMap.get("sortFieId");//排序字段

        if (sortValue != null && !sortValue.equals("")) {
            if(sortValue.equals("ASC")){
                Sort sort = new Sort(Sort.Direction.ASC, "item_"+sortFieId);
                query.addSort(sort);
            }

            if(sortValue.equals("DESC")){
                Sort sort = new Sort(Sort.Direction.DESC, "item_"+sortFieId);
                query.addSort(sort);
            }

        }


        //*************** 获取高亮结果集*******************
        //返回高亮页对象
        HighlightPage<TbItem> highlightPage = solrTemplate.queryForHighlightPage("collection1", query, TbItem.class);
        //高亮入口集合
        List<HighlightEntry<TbItem>> entryList = highlightPage.getHighlighted();
        for (HighlightEntry<TbItem> entry : entryList) {

            //获取高亮列表
            List<HighlightEntry.Highlight> highlights = entry.getHighlights();
            if (highlights.size() > 0 && highlights.get(0).getSnipplets().size() > 0) {

                TbItem item = entry.getEntity();
                item.setTitle(highlights.get(0).getSnipplets().get(0));
            }
        }
        map.put("rows", highlightPage.getContent());
        map.put("totalPages", highlightPage.getTotalPages());//总页数
        map.put("total", highlightPage.getTotalElements());//总记录数
        return map;

    }


    /**
     * 分组查询页面
     *
     * @return
     */
    private List<String> searchCategroyList(Map searchMap) {
        System.out.println("测试");
        List<String> list = new ArrayList();

        Field field = new SimpleField("item_category");

        SimpleQuery query = new SimpleFacetAndHighlightQuery(new SimpleStringCriteria("*:*"));


        //关键字查询相当于where
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        //设置分组选项
        GroupOptions groupOptions = new GroupOptions().addGroupByField(field);
        query.setGroupOptions(groupOptions);
        groupOptions.setLimit(0);
        groupOptions.setOffset(100);
        //获取分组页
        GroupPage<TbItem> page = solrTemplate.queryForGroupPage("collection1", query, TbItem.class);
        //获取分组结果对象
        GroupResult<TbItem> groupResult = page.getGroupResult(field);
        //获取分组入口页
        Page<GroupEntry<TbItem>> groupEntries = groupResult.getGroupEntries();
        //获取分组入口集合
        List<GroupEntry<TbItem>> entryList = groupEntries.getContent();
        for (GroupEntry<TbItem> entry : entryList) {
            list.add(entry.getGroupValue());//将分组结果添加到返回值中
            System.out.println(entry);
        }
        return list;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询品牌和规格列表
     *
     * @param
     * @return
     */
    private Map searchBrandAndSpecList(String category) {
        Map map = new HashMap();
        //根据商品分类名称得到模板Id
        Long templateId = (Long) redisTemplate.boundHashOps("itemCat").get(category);
        if (templateId != null) {
            //根据模板Id获取品牌列表
            List brandList = (List) redisTemplate.boundHashOps("brandList").get(templateId);
            map.put("brandList", brandList);

            //根据模板Id获得规格列表
            List specdList = (List) redisTemplate.boundHashOps("specdList").get(templateId);

            map.put("specdList", specdList);
        }
        return map;
    }


}
