package com.oi.financialoilservices.controller;

import com.oi.financialoilservices.dto.InputOilTransactionDto;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Oil transaction service controller")
@Slf4j
@Controller
public class StatisticsServiceController {

    @Autowired
    private StatisticsService statisticsService;

    @PostMapping(value = "/revenueYield")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Calculate Revenue yield.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = OilTransaction.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public String calculateRevenueYield(@RequestBody InputOilTransactionDto inputOilTransactionDto) {
        return null;
    }

    @PostMapping(value = "/priceEarningsRatio")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Calculate Price-Earnings Ratio.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = OilTransaction.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public String calculatePriceEarningsRatio(@RequestBody InputOilTransactionDto inputOilTransactionDto) {
        return null;
    }

    @GetMapping(value = "/volumeWeightedOilPrice30Minutes")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Calculate Volume Weighted Oil Price based on transaction in the past 30 minutes.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = OilTransaction.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public String calculateVolumeWeightedOilPrice30Minutes() {
        return null;
    }

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
