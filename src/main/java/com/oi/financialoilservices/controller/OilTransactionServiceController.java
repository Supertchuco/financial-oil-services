package com.oi.financialoilservices.controller;

import com.oi.financialoilservices.dto.InputOilTransactionDto;
import com.oi.financialoilservices.entity.OilTransaction;
import com.oi.financialoilservices.service.OilTransactionService;
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

import java.util.List;

@RestController
@RequestMapping(path = "/oilTransaction", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Oil transaction service controller")
@Slf4j
@Controller
public class OilTransactionServiceController {

    @Autowired
    private OilTransactionService oilTransactionService;

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Input oil transaction registry on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = OilTransaction.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public OilTransaction inputOilTransaction(@RequestBody InputOilTransactionDto inputOilTransactionDto) {
        return oilTransactionService.persistOilTransactionOnDatabase(inputOilTransactionDto);
    }

    @GetMapping(value = "/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve oil transaction by transaction id.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = OilTransaction.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public OilTransaction getOilTransactionByTransactionId(@PathVariable long transactionId) {
        return oilTransactionService.getOilTransactionOnDatabase(transactionId);
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve all oil transactions on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = OilTransaction.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public List<OilTransaction> getAllOilTransactions() {
        return oilTransactionService.getOilTransactionOnDatabase();
    }

}
