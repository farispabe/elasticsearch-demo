package com.elasticsearch.example.demo.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "driver")
@Data
@Document(indexName = "driver", type = "driver")
public class Driver implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Field(type = FieldType.Keyword)
    private String fullName;

    @Field(type = FieldType.Keyword)
    private String nickName;

    @Field(type = FieldType.Keyword)
    private Gender gender;

    @Field(type = FieldType.Keyword)
    private String vehicle;

    private Integer price;

    @Transient
    @GeoPointField
    private GeoPoint location;
}
