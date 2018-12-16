package com.elasticsearch.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetRequestDriverVO {

    @JsonProperty(value = "driver_ids")
    private List<Integer> driverIds;

    private String words;

    @JsonProperty(value = "regency_id")
    private Integer regencyId;

    @JsonProperty(value = "maximum_price")
    private Integer maximumPrice;

    @JsonProperty(value = "minimum_price")
    private Integer minimumPrice;

    @JsonProperty(value = "current_latitude")
    private Double currentLatitude;

    @JsonProperty(value = "current_longitude")
    private Double currentLongitude;

    @JsonProperty(value = "radius")
    private Double radius;

    private Integer page;

    private Integer limit;

    @JsonProperty(value = "sort_by")
    private String sortBy;

    @JsonProperty(value = "direction")
    private String direction;
}
