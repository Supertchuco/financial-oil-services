package com.oi.financialoilservices.service;

import com.oi.financialoilservices.dto.ResponseGeometricMeanDto;
import com.oi.financialoilservices.entity.OilTransaction;
import com.oi.financialoilservices.exception.CalculateGeometricMeanException;
import com.oi.financialoilservices.exception.CalculatePriceEarningsRatioException;
import com.oi.financialoilservices.exception.CalculateRevenueYieldStandardException;
import com.oi.financialoilservices.exception.CalculateVolumeWeightedOilPriceException;
import com.oi.financialoilservices.repository.OilRepository;
import com.oi.financialoilservices.repository.OilTransactionRepository;
import com.oi.financialoilservices.repository.OilTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.StatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.time.LocalDateTime.now;

@Slf4j
@Service
public class StatisticsService {

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
            return price.divide(BigDecimal.valueOf(revenue), 2, RoundingMode.HALF_EVEN);
        } catch (Exception exception) {
            log.error("Error to calculate Price-Earnings Ratio", exception);
            throw new CalculatePriceEarningsRatioException();
        }
    }

    public ResponseGeometricMeanDto calculateGeometricMeanProcess() {
        final List<BigDecimal> values = oilTransactionRepository.findAllPricesInAllOilTransactions();
        ResponseGeometricMeanDto geometricMeanDto = new ResponseGeometricMeanDto((CollectionUtils.isEmpty(values))
                ? BigDecimal.ZERO : calculateGeometricMean(values));
        return geometricMeanDto;
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

    public BigDecimal calculateVolumeWeightedOilPriceProcess(final String oilType) {
        List<OilTransaction> transactions = oilTransactionRepository.findByOilOilTypeOilTypeAndTransactionDateTimeBetween(oilType,
                now().minusMinutes(30), now());
        return (CollectionUtils.isEmpty(transactions)) ? BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_EVEN)
                : calculateVolumeWeightedOilPrice(transactions);
    }

    private BigDecimal calculateVolumeWeightedOilPrice(final List<OilTransaction> transactions) {
        long totalQuantity = 0;
        BigDecimal totalQuantityXPrice = BigDecimal.ZERO;
        try {
            log.info("Calculate Volume Weighted Oil Price");
            for (OilTransaction transaction : transactions) {
                totalQuantity += transaction.getVolume();
                totalQuantityXPrice = totalQuantityXPrice.add(BigDecimal.valueOf(transaction.getVolume())
                        .multiply(transaction.getPrice()).setScale(2, RoundingMode.HALF_EVEN));
            }
            return totalQuantityXPrice.divide(BigDecimal.valueOf(totalQuantity), 2,
                    RoundingMode.HALF_EVEN).setScale(2, RoundingMode.HALF_EVEN);
        } catch (Exception exception) {
            log.error("Error to calculate Volume Weighted Oil Price", exception);
            throw new CalculateVolumeWeightedOilPriceException();
        }
    }

}
