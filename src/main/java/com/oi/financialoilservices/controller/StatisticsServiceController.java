package com.oi.financialoilservices.controller;

import com.oi.financialoilservices.entity.OilTransaction;
import com.oi.financialoilservices.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Oil transaction service controller")
@Slf4j
@Controller
public class StatisticsServiceController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping(value = "/geometricMean")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Calculate the Inventory Index using the geometric mean of prices for all the types of oil.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = OilTransaction.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public String calculateGeometricMean() {
        return null;
    }
}
