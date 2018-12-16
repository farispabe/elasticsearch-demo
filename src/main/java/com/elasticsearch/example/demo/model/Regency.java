package com.elasticsearch.example.demo.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Document(indexName = "regency", type = "regency")
public class Regency {

    @Id
    @GeneratedValue
    private Integer id;

    @Field(type = FieldType.Keyword)
    private String name;

    private Double southWestLat;

    private Double southWestLng;

    private Double northEastLat;

    private Double northEastLng;
}
