package com.elasticsearch.example.demo.repository;

import com.elasticsearch.example.demo.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Integer>{
}
