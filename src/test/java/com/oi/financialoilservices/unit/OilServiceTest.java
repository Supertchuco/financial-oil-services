package com.oi.financialoilservices.unit;

import com.oi.financialoilservices.entity.OilTransaction;
import com.oi.financialoilservices.service.OilService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"PMD.TooManyMethods", "PMD.UnusedPrivateField"})
@SpringBootTest
public class OilServiceTest {

    @Autowired
    private OilService oilService;

    @Test
    public void shouldCalculateVolumeWeightedOilPriceWithSuccess() {
        List<OilTransaction> transactions = Arrays.asList(new OilTransaction(1L, BigDecimal.valueOf(10.20), null, null),
                new OilTransaction(2L, BigDecimal.valueOf(10.21), null, null), new OilTransaction(3L,
                        BigDecimal.valueOf(10.22), null, null));
        assertEquals(BigDecimal.valueOf(10.21).setScale(2, RoundingMode.HALF_EVEN), oilService.calculateVolumeWeightedOilPrice(transactions));
    }

    @Test
    public void shouldCalculateRevenueYieldStandardWithSuccess() {
        assertEquals(BigDecimal.valueOf(1.13).setScale(2, RoundingMode.HALF_EVEN), oilService.calculateRevenueYield(22,
                BigDecimal.valueOf(19.50)));
    }

    @Test
    public void shouldCalculateRevenueYieldPremiumWithSuccess() {
        assertEquals(BigDecimal.valueOf(19.94).setScale(2, RoundingMode.HALF_EVEN), oilService.calculateRevenueYield(7,
                BigDecimal.valueOf(50.00), BigDecimal.valueOf(17.55)));
    }

    @Test
    public void shouldCalculatePriceEarningsRatioWithSuccess() {
        assertEquals(BigDecimal.valueOf(2.47).setScale(2, RoundingMode.HALF_EVEN), oilService.calculatePriceEarningsRatio(BigDecimal.valueOf(12.35),
                5));
    }

    @Test
    public void shouldCalculateGeometricMeanWithSuccess() {
        assertEquals(BigDecimal.valueOf(7.34).setScale(2, RoundingMode.HALF_EVEN),
                oilService.calculateGeometricMean( Arrays.asList(BigDecimal.valueOf(9.20),  BigDecimal.valueOf(4.21), BigDecimal.valueOf(10.22))));
    }
}
