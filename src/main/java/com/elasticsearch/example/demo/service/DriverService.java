package com.elasticsearch.example.demo.service;

import com.elasticsearch.example.demo.elasticrepository.DriverElasticRepository;
import com.elasticsearch.example.demo.elasticrepository.RegencyElasticRepository;
import com.elasticsearch.example.demo.model.Driver;
import com.elasticsearch.example.demo.model.Gender;
import com.elasticsearch.example.demo.model.Regency;
import com.elasticsearch.example.demo.util.DistanceUtil;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import com.elasticsearch.example.demo.repository.DriverRepository;
import com.elasticsearch.example.demo.vo.DriverVO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    DriverElasticRepository driverElasticRepository;

    @Autowired
    RegencyElasticRepository regencyElasticRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    public Integer add(DriverVO vo){
        Driver driver = new Driver();
        driver.setFullName(vo.getFullName());
        driver.setNickName(vo.getNickName());
        driver.setGender(Gender.valueOf(vo.getGender()));
        driver.setVehicle(vo.getVehicle());
        driver.setPrice(vo.getPrice());
        driver.setLocation(new GeoPoint(vo.getLatitude(), vo.getLongitude()));
        driver = driverRepository.save(driver);
        driverElasticRepository.save(driver);
        return driver.getId();
    }

    public List<DriverVO> get(List<Integer> ids, String words, Integer regencyId, Integer maximumPrice, Integer minimumPrice,
                              Double currentLatitude, Double currentLongitude, Double radius, Integer page, Integer limit,
                              String sortBy, String direction){
        final int scoreThreshold = 10;
        page = page == null ? 0 : page;
        limit = limit == null ? 10 : limit;
        maximumPrice = maximumPrice == null ? Integer.MAX_VALUE : maximumPrice;
        minimumPrice = minimumPrice == null ? 0 : minimumPrice;
        sortBy = StringUtils.isEmpty(sortBy) ? "fullName" : sortBy;
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction != null && direction.equalsIgnoreCase("dec")) sortDirection = Sort.Direction.DESC;

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
                .filter(new RangeQueryBuilder("price")
                        .gte(minimumPrice)
                        .lte(maximumPrice));

        if (ids != null && ids.size() > 0){
            float score = ids.size()*scoreThreshold;
            for (Integer id : ids){
                boolQueryBuilder = boolQueryBuilder
                        .should(new TermQueryBuilder("id", String.valueOf(id)).boost(score));
                score = score - scoreThreshold;
            }
        }
        if (!StringUtils.isEmpty(words)){
            boolQueryBuilder = boolQueryBuilder
                    .filter(new MultiMatchQueryBuilder(words)
                            .field("fullName")
                            .field("nickName")
                            .field("gender")
                            .field("vehicle")
                            .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                            .fuzziness(Fuzziness.TWO));
        }
        if (regencyId != null){
            Regency regency = regencyElasticRepository.findById(regencyId).get();
            boolQueryBuilder = boolQueryBuilder
                    .filter(new GeoBoundingBoxQueryBuilder("location")
                            .setCorners(regency.getNorthEastLat(), regency.getSouthWestLng(), regency.getSouthWestLat(), regency.getNorthEastLng()));
        }
        if (currentLatitude != null && currentLongitude != null && radius != null){
            boolQueryBuilder = boolQueryBuilder
                    .filter(QueryBuilders.geoDistanceQuery("location")
                            .point(currentLatitude, currentLongitude)
                            .distance(radius, DistanceUnit.KILOMETERS));
        }

        SearchQuery searchQuery;
        if (ids != null && ids.size() > 0){
            searchQuery = new NativeSearchQueryBuilder()
                    .withMinScore(scoreThreshold)
                    .withQuery(boolQueryBuilder)
                    .build();
        } else if (currentLatitude != null && currentLongitude != null && radius != null){
            searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(boolQueryBuilder)
                    .withPageable(new PageRequest(page, limit))
                    .withSort(SortBuilders
                            .geoDistanceSort("location", new org.elasticsearch.common.geo.GeoPoint(currentLatitude, currentLongitude))
                            .order(SortOrder.ASC))
                    .build();
        } else {
            searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(boolQueryBuilder)
                    .withPageable(new PageRequest(page, limit, new Sort(sortDirection, sortBy)))
                    .build();
        }

        elasticsearchTemplate.createIndex(Driver.class);
        Page<Driver> resultPage = elasticsearchTemplate.queryForPage(searchQuery, Driver.class);
        List<Driver> resultList = resultPage.getContent();
        List<DriverVO> vos = new ArrayList<>();
        for (Driver result : resultList){
            DriverVO vo = new DriverVO();
            vo.setId(result.getId());
            vo.setFullName(result.getFullName());
            vo.setNickName(result.getNickName());
            vo.setGender(result.getGender().getInternalValue());
            vo.setVehicle(result.getVehicle());
            vo.setPrice(result.getPrice());
            if (currentLatitude != null && currentLongitude != null && radius != null)vo.setDistance(DistanceUtil.harvesineDistance(currentLatitude, currentLongitude, result.getLocation().getLat(), result.getLocation().getLon()));
            vos.add(vo);
        }
        return vos;
    }
}
