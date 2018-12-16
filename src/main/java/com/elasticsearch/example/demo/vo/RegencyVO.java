package com.elasticsearch.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegencyVO {

    private Integer id;

    private String name;

    @JsonProperty(value = "south_west_lat")
    private Double southWestLat;

    @JsonProperty(value = "south_west_lng")
    private Double southWestLng;

    @JsonProperty(value = "north_east_lat")
    private Double northEastLat;

    @JsonProperty(value = "north_east_lng")
    private Double northEastLng;
}
