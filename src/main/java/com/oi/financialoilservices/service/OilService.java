package com.oi.financialoilservices.service;

import com.oi.financialoilservices.dto.InputOilDto;
import com.oi.financialoilservices.dto.InputOilTransactionDto;
import com.oi.financialoilservices.entity.Oil;
import com.oi.financialoilservices.entity.OilTransaction;
import com.oi.financialoilservices.entity.OilType;
import com.oi.financialoilservices.exception.*;
import com.oi.financialoilservices.repository.OilRepository;
import com.oi.financialoilservices.repository.OilTransactionRepository;
import com.oi.financialoilservices.repository.OilTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.StatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class OilService {

    @Autowired
    private OilRepository oilRepository;

    @Autowired
    private OilTypeRepository oilTypeRepository;

    @Autowired
    private OilTransactionRepository oilTransactionRepository;

    public BigDecimal calculateRevenueYield(final int fixedRevenue, final BigDecimal price) {
        try {
            log.info("Calculate Revenue Yield Standard");
            return BigDecimal.valueOf(fixedRevenue).divide(
                    price, 2, RoundingMode.HALF_EVEN);
        } catch (Exception exception) {
            log.error("Error to calculate Revenue Yield Standard", exception);
            throw new CalculateRevenueYieldStandardException();
        }
    }

    public BigDecimal calculateRevenueYield(final int variableRevenue, final BigDecimal oilBarrelValue, final BigDecimal price) {
        try {
            log.info("Calculate Revenue Yield Premium");
            return (BigDecimal.valueOf(variableRevenue).multiply(oilBarrelValue).setScale(2, RoundingMode.HALF_EVEN))
                    .divide(price, 2, RoundingMode.HALF_EVEN);
        } catch (Exception exception) {
            log.error("Error to calculate Revenue Yield Premium", exception);
            throw new CalculateRevenueYieldStandardException();
        }
    }

    public BigDecimal calculatePriceEarningsRatio(final BigDecimal price, final int revenue) {
        try {
            log.info("Calculate Price-Earnings Ratio");
            return price.divide(BigDecimal.valueOf(revenue));
        } catch (Exception exception) {
            log.error("Error to calculate Price-Earnings Ratio", exception);
            throw new CalculatePriceEarningsRatioException();
        }
    }

    public BigDecimal calculateGeometricMean(final List<BigDecimal> values) {
        try {
            log.info("Calculate Volume Weighted Oil Price");
            double[] arrayValues = values.stream().mapToDouble(BigDecimal::doubleValue).toArray();
            return BigDecimal.valueOf(StatUtils.geometricMean(arrayValues)).setScale(2, RoundingMode.HALF_EVEN);
        } catch (Exception exception) {
            log.error("Error to calculate geometric mean of prices for all the types of oil", exception);
            throw new CalculateGeometricMeanException();
        }
    }

    public BigDecimal calculateVolumeWeightedOilPrice(final List<OilTransaction> transactions) {
        long totalQuantity = 0;
        BigDecimal totalQuantityXPrice = BigDecimal.ZERO;
        try {
            log.info("Calculate Volume Weighted Oil Price");
            for (OilTransaction transaction : transactions) {
                totalQuantity += transaction.getVolume();
                totalQuantityXPrice = totalQuantityXPrice.add(BigDecimal.valueOf(transaction.getVolume()).multiply(transaction.getPrice()).setScale(2, RoundingMode.HALF_EVEN));
            }
            return totalQuantityXPrice.divide(BigDecimal.valueOf(totalQuantity), 2, RoundingMode.HALF_EVEN).setScale(2, RoundingMode.HALF_EVEN);
        } catch (Exception exception) {
            log.error("Error to calculate Volume Weighted Oil Price", exception);
            throw new CalculateVolumeWeightedOilPriceException();
        }
    }

    public Oil persistOilRegistry(final InputOilDto inputOilDto) {
        log.info("Persist Oil registry on database");
        inputOilDto.validate(inputOilDto);
        try {
            final OilType oilType = initializeOilTypeObject(inputOilDto.getOilTypeId());
            final Oil oil = (isNull(inputOilDto.getVariableRevenue())) ? new Oil(inputOilDto.getId(), oilType, inputOilDto.getFixedRevenue(), inputOilDto.getOilBarrelValue()) :
                    new Oil(inputOilDto.getId(), oilType, inputOilDto.getFixedRevenue(), inputOilDto.getVariableRevenue(), inputOilDto.getOilBarrelValue());
            return oilRepository.save(oil);
        } catch (OilTypeRegistryNotFoundException oilTypeRegistryNotFoundException) {
            throw oilTypeRegistryNotFoundException;
        } catch (Exception exception) {
            log.error("Error to persist oil registry on database", exception);
            throw new SaveOilResistryException();
        }
    }

    public Oil getOilRegistryOnDatabase(final String oilId) {
        log.info("Get Oil registry on database");
        try {
            return oilRepository.findByOilId(oilId);
        } catch (Exception exception) {
            log.error("Error to get oil registry on database", exception);
            throw new GetOilException();
        }
    }

    public List<Oil> getOilRegistryOnDatabase() {
        log.info("Get all Oil registries on database");
        try {
            return oilRepository.findAll();
        } catch (Exception exception) {
            log.error("Error to get all oil registries on database", exception);
            throw new GetOilException();
        }
    }

    public OilTransaction persistOilTransactionOnDatabase(final InputOilTransactionDto inputOilTransactionDto) {
        log.info("Persist Oil transaction registry on database");
        inputOilTransactionDto.validate(inputOilTransactionDto);
        final Oil oil = oilRepository.findByOilId(inputOilTransactionDto.getOilId());

        if (isNull(oil)) {
            // nao existe
        }

        try {
            return oilTransactionRepository.save(new OilTransaction(inputOilTransactionDto.getVolume(), inputOilTransactionDto.getPrice(),
                    inputOilTransactionDto.getOperation(), initializeOilObject(inputOilTransactionDto.getOilId())));
        } catch (OilRegistryNotFoundException oilRegistryNotFoundException) {
            throw oilRegistryNotFoundException;
        } catch (Exception exception) {
            log.error("Error to persist ne oil registry", exception);
            throw new SaveOilTransactionRegistryException();
        }
    }

    public List<OilTransaction> getOilTransactionOnDatabase(final long oilTransactionId) {
        log.info("Get Oil transaction(s) registry on database");
        try {
            return oilTransactionRepository.findAllWithTransactionDateTimeUntil30Minutes();
        } catch (Exception exception) {
            log.error("Error to get oil transaction(s) registry", exception);
            throw new GetOilTransactionRegistryException();
        }
    }

    public List<OilTransaction> getOilTransactionOnDatabaseByLast30Minutes() {
        log.info("Get Oil transaction(s) registry on database");
        try {
            Timestamp initialTimestamp = new Timestamp(new Date().getTime());
            Timestamp finalTimestamp = new Timestamp(new Date(new Date().getTime() - (5 * 60000)).getTime());

            return oilTransactionRepository.findByTransactionDateTimeBetween(initialTimestamp, finalTimestamp);
        } catch (Exception exception) {
            log.error("Error to get oil transaction(s) registry", exception);
            throw new GetOilTransactionOnDatabaseByLast30MinutesException();
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

    private OilType initializeOilTypeObject(final String oilTypeId) {
        log.info("Initialize Oil object");
        final OilType oilType = oilTypeRepository.findByOilType(oilTypeId);
        if (isNull(oilType)) {
            log.error("Oil type registry not found in database by id: {}", oilTypeId);
            throw new OilTypeRegistryNotFoundException();
        }
        return oilType;
    }
}
