package com.elasticsearch.example.demo.service;

import com.elasticsearch.example.demo.elasticrepository.RegencyElasticRepository;
import com.elasticsearch.example.demo.model.Regency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;
import com.elasticsearch.example.demo.vo.RegencyVO;

@Service
public class RegencyService {

    @Autowired
    RegencyElasticRepository regencyElasticRepository;

    public Integer add(RegencyVO vo){
        Regency regency = new Regency();
        regency.setName(vo.getName());
        regency.setId(vo.getId());
        regency.setNorthEastLat(vo.getNorthEastLat());
        regency.setNorthEastLng(vo.getNorthEastLng());
        regency.setSouthWestLat(vo.getSouthWestLat());
        regency.setSouthWestLng(vo.getSouthWestLng());
        regency = regencyElasticRepository.save(regency);
        return regency.getId();
    }
}
