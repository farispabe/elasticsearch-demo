package com.elasticsearch.example.demo.controller;

import com.elasticsearch.example.demo.vo.GetRequestDriverVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.elasticsearch.example.demo.service.DriverService;
import com.elasticsearch.example.demo.vo.DriverVO;

import java.util.List;

@RestController
@RequestMapping("api/driver")
public class DriverController {

    @Autowired
    DriverService driverService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer add(@RequestBody DriverVO vo){
        return driverService.add(vo);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<DriverVO> get(@RequestBody GetRequestDriverVO vo){
        return driverService.get(vo.getDriverIds(), vo.getWords(), vo.getRegencyId(), vo.getMaximumPrice(), vo.getMinimumPrice(), vo.getCurrentLatitude(),
                vo.getCurrentLongitude(), vo.getRadius(), vo.getPage(), vo.getLimit(), vo.getSortBy(), vo.getDirection());
    }
}
