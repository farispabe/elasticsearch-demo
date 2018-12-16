package com.elasticsearch.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.elasticsearch.example.demo.service.RegencyService;
import com.elasticsearch.example.demo.vo.RegencyVO;

@RestController
@RequestMapping("api/regency")
public class RegencyController {

    @Autowired
    RegencyService regencyService;

    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer add(@RequestBody RegencyVO vo){
        return regencyService.add(vo);
    }
}
