package com.oi.financialoilservices.controller;

import com.oi.financialoilservices.entity.Oil;
import com.oi.financialoilservices.entity.OilTransaction;
import com.oi.financialoilservices.service.OilService;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/oil", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "User controller")
@Slf4j
@Controller
public class OilController {

    @Autowired
    private OilService oilService;

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Input oil registry on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Oil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })

    public Oil inputOil(@Valid @RequestBody Oil oil) {
        return oilService.persistOilRegistry(oil);
    }

    @GetMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get oil registry by id on database or if id is null get all oil registries on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Oil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public List<Oil> getOil(@RequestBody String oilId) {
        return oilService.getOilRegistryOnDatabase(oilId);
    }

    @PostMapping(value = "/transaction")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Input oil transaction registry on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = OilTransaction.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public OilTransaction inputOilTransaction(@Valid @RequestBody OilTransaction oilTransaction) {
        return oilService.persistOilTransactionOnDatabase(oilTransaction);
    }

}
