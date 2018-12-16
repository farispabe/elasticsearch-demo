package com.elasticsearch.example.demo.elasticrepository;

import com.elasticsearch.example.demo.model.Driver;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DriverElasticRepository extends ElasticsearchRepository<Driver, Integer> {
}
