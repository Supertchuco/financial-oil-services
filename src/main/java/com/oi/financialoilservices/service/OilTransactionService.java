package com.oi.financialoilservices.service;

import com.oi.financialoilservices.dto.InputOilTransactionDto;
import com.oi.financialoilservices.dto.ResponseOilTransactionDto;
import com.oi.financialoilservices.entity.Oil;
import com.oi.financialoilservices.entity.OilTransaction;
import com.oi.financialoilservices.enumerator.Operations;
import com.oi.financialoilservices.exception.GetOilTransactionRegistryException;
import com.oi.financialoilservices.exception.InvalidOilTransactionOperationException;
import com.oi.financialoilservices.exception.OilRegistryNotFoundException;
import com.oi.financialoilservices.exception.SaveOilTransactionRegistryException;
import com.oi.financialoilservices.repository.OilRepository;
import com.oi.financialoilservices.repository.OilTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.EnumUtils.isValidEnumIgnoreCase;

@Slf4j
@Service
public class OilTransactionService {

    @Autowired
    private OilRepository oilRepository;

    @Autowired
    private OilTransactionRepository oilTransactionRepository;

    @Autowired
    private StatisticsService statisticsService;

    private OilTransaction persistOilTransactionOnDatabase(final InputOilTransactionDto inputOilTransactionDto) {
        log.info("Persist Oil transaction registry on database");
        try {
            return oilTransactionRepository.save(new OilTransaction(inputOilTransactionDto.getVolume(), inputOilTransactionDto.getPrice(),
                    inputOilTransactionDto.getOperation().toUpperCase(Locale.US), initializeOilObject(inputOilTransactionDto.getOilId())));
        } catch (OilRegistryNotFoundException oilRegistryNotFoundException) {
            throw oilRegistryNotFoundException;
        } catch (Exception exception) {
            log.error("Error to persist ne oil registry", exception);
            throw new SaveOilTransactionRegistryException();
        }
    }

    public ResponseOilTransactionDto oilTransactionOperation(final InputOilTransactionDto inputOilTransactionDto) {
        log.info("Start oil transaction operation");

        if (!isValidEnumIgnoreCase(Operations.class, inputOilTransactionDto.getOperation())) {
            log.error("Invalid Operation {}", inputOilTransactionDto.getOperation());
            throw new InvalidOilTransactionOperationException();
        }

        final OilTransaction oilTransaction = persistOilTransactionOnDatabase(inputOilTransactionDto);

        final BigDecimal revenueYield;
        final int revenue;

        if (isNull(oilTransaction.getOil().getFixedRevenue())) {
            revenue = oilTransaction.getOil().getVariableRevenue();
            revenueYield = statisticsService.calculateRevenueYield(revenue, oilTransaction.getOil().getOilBarrelValue(),
                    oilTransaction.getPrice());
        } else {
            revenue = oilTransaction.getOil().getFixedRevenue();
            revenueYield = statisticsService.calculateRevenueYield(revenue, oilTransaction.getPrice());
        }

        return new ResponseOilTransactionDto(oilTransaction.getTransactionId(), oilTransaction.getVolume(),
                oilTransaction.getPrice(), oilTransaction.getOperation(), oilTransaction.getOil(), oilTransaction.getTransactionDateTime(),
                revenueYield, statisticsService.calculatePriceEarningsRatio(oilTransaction.getPrice(), revenue),
                statisticsService.calculateVolumeWeightedOilPriceProcess(oilTransaction.getOil().getOilType().getOilType()));
    }

    public OilTransaction getOilTransactionOnDatabase(final long oilTransactionId) {
        log.info("Get Oil transaction(s) registry on database");
        try {
            return oilTransactionRepository.findByTransactionId(oilTransactionId);
        } catch (Exception exception) {
            log.error("Error to get oil transaction(s) registry", exception);
            throw new GetOilTransactionRegistryException();
        }
    }

    public List<OilTransaction> getOilTransactionOnDatabase() {
        log.info("Get Oil transaction(s) registry on database");
        try {
            return oilTransactionRepository.findAll();

        } catch (Exception exception) {
            log.error("Error to get oil transaction(s) registry", exception);
            throw new GetOilTransactionRegistryException();
        }
    }

    private Oil initializeOilObject(final String oilId) {
        log.info("Initialize Oil object");
        final Oil oil = oilRepository.findByOilId(oilId);
        if (isNull(oil)) {
            log.error("Oil registry not found in database by id: {}", oilId);
            throw new OilRegistryNotFoundException();
        }
        return oil;
    }
}
