package com.daishu;

import com.daishu.utlis.SolrUtlis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DaishuSolrUtlisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run= SpringApplication.run(DaishuSolrUtlisApplication.class, args);
        SolrUtlis i = (SolrUtlis) run.getBean("solrUtlis");
        i.improtItemData();

    }

}
