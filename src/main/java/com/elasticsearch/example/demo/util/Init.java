package com.elasticsearch.example.demo.util;

import com.elasticsearch.example.demo.model.Driver;
import com.elasticsearch.example.demo.model.Regency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Init {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @PostConstruct
    public void init(){
        elasticsearchTemplate.putMapping(Driver.class);
        elasticsearchTemplate.putMapping(Regency.class);
    }
}
