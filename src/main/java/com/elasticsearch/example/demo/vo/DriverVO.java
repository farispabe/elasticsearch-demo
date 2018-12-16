package com.elasticsearch.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DriverVO {

    private Integer id;

    @JsonProperty(value = "full_name")
    private String fullName;

    @JsonProperty(value = "nick_name")
    private String nickName;

    private String gender;

    private String vehicle;

    private Integer price;

    private Double latitude;

    private Double longitude;

    private Double distance;
}
