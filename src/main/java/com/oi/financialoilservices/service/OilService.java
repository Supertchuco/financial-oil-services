package com.oi.financialoilservices.service;

import com.oi.financialoilservices.entity.OilTransaction;
import com.oi.financialoilservices.exception.CalculateGeometricMeanException;
import com.oi.financialoilservices.exception.CalculatePriceEarningsRatioException;
import com.oi.financialoilservices.exception.CalculateRevenueYieldStandardException;
import com.oi.financialoilservices.exception.CalculateVolumeWeightedOilPriceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.StatUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class OilService {

    public BigDecimal calculateRevenueYield(final int fixedRevenue, final BigDecimal price) {
        try {
            log.info("Calculate Revenue Yield Standard");
            return new BigDecimal(fixedRevenue).divide(price);
        } catch (Exception exception) {
            log.error("Error to calculate Revenue Yield Standard", exception);
            throw new CalculateRevenueYieldStandardException();
        }
    }

    public BigDecimal calculateRevenueYield(final int variableRevenue, final BigDecimal oilBarrelValue, final BigDecimal price) {
        try {
            log.info("Calculate Revenue Yield Premium");
            return (new BigDecimal(variableRevenue).multiply(oilBarrelValue)).divide(price);
        } catch (Exception exception) {
            log.error("Error to calculate Revenue Yield Premium", exception);
            throw new CalculateRevenueYieldStandardException();
        }
    }

    public BigDecimal calculatePriceEarningsRatio(final BigDecimal price, final int revenue) {
        try {
            log.info("Calculate Price-Earnings Ratio");
            return price.divide(new BigDecimal(revenue));
        } catch (Exception exception) {
            log.error("Error to calculate Price-Earnings Ratio", exception);
            throw new CalculatePriceEarningsRatioException();
        }
    }

    public long calculateGeometricMean() {

        return 0;
    }



/*    public BigDecimal geometricMean(double[] data)
    {
        double sum = data[0];

        for (int i = 1; i < data.length; i++) {
            sum *= data[i];
        }
        return BigDecimal.valueOf(Math.pow(sum, 1.0 / data.length));
    }*/

    public BigDecimal geometricMean(List<BigDecimal> values) {
        try {
            log.info("Calculate Volume Weighted Oil Price");
            double[] arrayValues = values.stream().mapToDouble(BigDecimal::doubleValue).toArray();
            return new BigDecimal(StatUtils.geometricMean(arrayValues));
        } catch (Exception exception) {
            log.error("Error to calculate geometric mean of prices for all the types of oil", exception);
            throw new CalculateGeometricMeanException();
        }
    }

    public BigDecimal calculateVolumeWeightedOilPrice(final List<OilTransaction> transactions, final BigDecimal price) {

        // long totalQuantity =  transactons.stream().mapToLong(o -> o.getVolume()).sum();

        long totalQuantity = 0;
        BigDecimal totalQuantityXPrice = null;
        try {
            log.info("Calculate Volume Weighted Oil Price");
            for (OilTransaction transaction : transactions) {
                totalQuantity += transaction.getVolume();
                totalQuantityXPrice.add(new BigDecimal(transaction.getVolume()).multiply(price));
            }
            return totalQuantityXPrice.divide(new BigDecimal(totalQuantity));
        } catch (Exception exception) {
            log.error("Error to calculate Volume Weighted Oil Price", exception);
            throw new CalculateVolumeWeightedOilPriceException();
        }
    }
}
