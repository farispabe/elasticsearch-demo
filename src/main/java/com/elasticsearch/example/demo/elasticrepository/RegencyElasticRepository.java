package com.elasticsearch.example.demo.elasticrepository;

import com.elasticsearch.example.demo.model.Regency;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RegencyElasticRepository extends ElasticsearchRepository<Regency, Integer> {
}
