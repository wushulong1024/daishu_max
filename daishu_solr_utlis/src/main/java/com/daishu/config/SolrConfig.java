package com.daishu.config;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/18  18:06
 */

@Configuration
public class SolrConfig {

    @Bean
    @ConditionalOnMissingBean(SolrTemplate.class)
    public  SolrTemplate solrTemplate(SolrClient solrClient){
        return  new SolrTemplate(solrClient);
    }
}


